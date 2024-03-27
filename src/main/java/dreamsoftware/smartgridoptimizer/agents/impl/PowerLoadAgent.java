package dreamsoftware.smartgridoptimizer.agents.impl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.j256.simplejmx.common.JmxAttributeFieldInfo;
import com.j256.simplejmx.common.JmxAttributeMethodInfo;
import com.j256.simplejmx.common.JmxOperationInfo;
import com.j256.simplejmx.common.JmxResourceInfo;
import jade.content.ContentElement;
import jade.content.Predicate;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import dreamsoftware.smartgridoptimizer.agents.PublishSubscribeAgent;
import dreamsoftware.smartgridoptimizer.agents.behaviours.HandlerRequest;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.CurrentConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierLoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.PerformConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.UpdateLoad;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IPowerLoadVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerLoadVisitor;

public final class PowerLoadAgent extends PublishSubscribeAgent implements IPowerLoadVisitor {

	@Serial
	private static final long serialVersionUID = 1L;
	
	public final static String AGENT_NAME = "POWER_LOAD_AGENT";
	public final static String AGENT_DESCRIPTION = "Agents that generate loads";

	public static Double LOAD_FACTOR = 39.0;
	private static final Integer NOTIFY_INTERVAL = 1000;
        
    private String localName;
	
	private final Logger logger = LoggerFactory.getLogger(PowerLoadAgent.class);
	
	private Double pload = 0.0;
	
	private final HandlerRequest handlerRequest = new HandlerRequest(this, fipaRequestTemplate) {
		
		@Serial
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean isValidRequest(ContentElement ce) {
			return ce instanceof UpdateLoad || ce instanceof GetStatus;
		}

		@Override
		protected Predicate onGenerateResponse(ContentElement requestContent) {
			IPowerLoadVisitable requestVisitable = (IPowerLoadVisitable)requestContent;
			return requestVisitable.accept(PowerLoadAgent.this);
		}
	};
	
	@Override
	protected void setup() {
		localName = this.getAID().getLocalName();
        logger.debug("Setup " + localName + " agent ...");
		super.setup();
		this.addBehaviour(handlerRequest);
		
		/**
		 * Behaviour for load csv with load data.
		 */
		this.addBehaviour(new OneShotBehaviour() {
			
			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				
				
				InputStream inputStream = localName != null && getClass().getResource("/csv/"+ localName + "_load.csv") != null ? 
						getClass().getResourceAsStream("/csv/"+ localName + "_load.csv") : getClass().getResourceAsStream("/csv/interpolatedload.csv");
						
				BufferedReader br = null;
				
				List<Double> csvLoads = new ArrayList<>();
				
				try {
					br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
					String line = "";
		            while ((line = br.readLine()) != null) {
		            	// check if is a double value.
		            	if(Pattern.matches("([0-9]*)\\.([0-9]*)", line)){
		            		csvLoads.add(Double.parseDouble(line));
		            	}
		            }
		         
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(csvLoads.size() > 0) {
					myAgent.addBehaviour(new PeriodicallyReportConsumptionBehaviour(myAgent, NOTIFY_INTERVAL, csvLoads));
				} else {
					logger.debug("No loads founded ...");
				}
			}
		});
		
	}

	@Override
	protected void onRegisterServices(DFAgentDescription description) {
		ServiceDescription service = new ServiceDescription();
        service.setType(ENERGY);
        service.setName(REPORT_LOAD_ENERGY);
        description.addServices(service);
	}

	public Predicate visit(UpdateLoad updateLoad) {
		pload -= updateLoad.getLoadConsumption().getLoadConsumptionValue();
		return new NotifierLoadConsumption(new LoadConsumption(pload));
	}

	@Override
	protected void onCreateMBean(JmxResourceInfo resourceInfo, List<JmxAttributeFieldInfo> attributeFieldInfoList,
								 List<JmxAttributeMethodInfo> attributeMethodInfoList, List<JmxOperationInfo> operationInfoList) {
	
		resourceInfo.setJmxDomainName(AGENT_NAME);
		resourceInfo.setJmxBeanName(this.getAID().getLocalName());
		resourceInfo.setJmxDescription(AGENT_DESCRIPTION);
		attributeFieldInfoList.add(new JmxAttributeFieldInfo("LOAD_FACTOR", true, true, "Load Factor for all Power load Agents"));
	}
	
	/**
	 * Tick Behaviour to periodically report consumption.
	 */
	class PeriodicallyReportConsumptionBehaviour extends TickerBehaviour {
		
		@Serial
		private static final long serialVersionUID = 1L;
		
		private final List<Double> loadList;
		private Integer ticks = 0;
		
		public PeriodicallyReportConsumptionBehaviour(Agent a, long period, List<Double> loadList) {
			super(a, period);
			this.loadList = loadList;
		}

		@Override
		protected void onTick() {
			if(ticks >= loadList.size()) ticks = 0;
			pload = loadList.get(ticks++);
			logger.info(PowerLoadAgent.this.getName() + " create power load [" + pload + "]");
			PerformConsumption performConsumption = new PerformConsumption(getAID(), new LoadConsumption(pload));
			// notify load to subscribers.
			PowerLoadAgent.this.notifyToSubscribers(performConsumption);
		}
	}

	public Predicate visit(GetStatus getStatus) {
		return new CurrentConsumption(getAID(), new LoadConsumption(pload));
	}
}
