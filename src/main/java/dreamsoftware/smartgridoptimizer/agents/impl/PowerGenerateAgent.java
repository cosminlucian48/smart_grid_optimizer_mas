package dreamsoftware.smartgridoptimizer.agents.impl;


import com.j256.simplejmx.common.JmxAttributeFieldInfo;
import com.j256.simplejmx.common.JmxAttributeMethodInfo;
import com.j256.simplejmx.common.JmxOperationInfo;
import com.j256.simplejmx.common.JmxResourceInfo;
import dreamsoftware.smartgridoptimizer.agents.PublishSubscribeAgent;
import dreamsoftware.smartgridoptimizer.agents.behaviours.HandlerRequest;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerGenerated;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GenerateEnergy;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.PowerCurrentlyGenerated;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IPowerGenerateVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerGenerateVisitor;
import jade.content.ContentElement;
import jade.content.Predicate;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.List;

public final class PowerGenerateAgent extends PublishSubscribeAgent implements IPowerGenerateVisitor {

	@Serial
	private static final long serialVersionUID = 1L;
	
	public final static String AGENT_NAME = "POWER_GENERATE_AGENT";
	public final static String AGENT_DESCRIPTION = "Agents that generate energy";

	public static Double GEN_FACTOR = 24.0;
	private static final Integer NOTIFY_INTERVAL = 1000;
	
	private final Logger logger = LoggerFactory.getLogger(PowerGenerateAgent.class);
	private Double ppower = 0.0;
	
	private final HandlerRequest handlerRequest = new HandlerRequest(this, fipaRequestTemplate) {
		
		@Serial
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean isValidRequest(ContentElement ce) {
			return ce instanceof GetStatus;
		}

		@Override
		protected Predicate onGenerateResponse(ContentElement requestContent) {
			IPowerGenerateVisitable requestVisitable = (IPowerGenerateVisitable)requestContent;
			return requestVisitable.accept(PowerGenerateAgent.this);
		}
	};

	@Override
	protected void setup() {
		super.setup();
		this.addBehaviour(handlerRequest);

		// Behavior for loading CSV with generation data
		this.addBehaviour(new OneShotBehaviour() {

			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				List<Double> csvGenerationMeasures = loadCSV("/csv/pv.csv");
				if (!csvGenerationMeasures.isEmpty()) {
					myAgent.addBehaviour(new PeriodicallyReportPowerGeneratedBehaviour(myAgent, NOTIFY_INTERVAL, csvGenerationMeasures));
				}
			}
		});
	}

	@Override
	protected void onRegisterServices(DFAgentDescription description) {
		ServiceDescription service = new ServiceDescription();
        service.setType(ENERGY);
        service.setName(REPORT_GENERATED_ENERGY);
        description.addServices(service);
	}

	@Override
	protected void onCreateMBean(JmxResourceInfo resourceInfo, List<JmxAttributeFieldInfo> attributeFieldInfoList,
								 List<JmxAttributeMethodInfo> attributeMethodInfoList, List<JmxOperationInfo> operationInfoList) {
		
		resourceInfo.setJmxDomainName(AGENT_NAME);
		resourceInfo.setJmxBeanName(this.getAID().getLocalName());
		resourceInfo.setJmxDescription(AGENT_DESCRIPTION);
		
		attributeFieldInfoList.add(new JmxAttributeFieldInfo("GEN_FACTOR", true, true, "Generation Factor for all Power Generate Agents"));
	}
	
	/**
	 * Tick Behaviour to periodically report the energy generated
	 *
	 */
	class PeriodicallyReportPowerGeneratedBehaviour extends TickerBehaviour {
		
		@Serial
		private static final long serialVersionUID = 1L;
		
		private final List<Double> powerGeneratedMeasures;
		private Integer tick = 0;
		
		public PeriodicallyReportPowerGeneratedBehaviour(Agent a, long period, List<Double> powerGeneratedMeasures) {
			super(a, period);
			this.powerGeneratedMeasures = powerGeneratedMeasures;
		}
		
		@Override
		protected void onTick() {
			if(tick >= powerGeneratedMeasures.size()) tick = 0;
			ppower = powerGeneratedMeasures.get(tick++);
			logger.debug(PowerGenerateAgent.this.getName() + " generate energy [" + ppower + "]");
			// notify load to subscribers.
			PowerGenerateAgent.this.notifyToSubscribers(new GenerateEnergy(getAID(), new PowerGenerated(ppower)));
		}
	}

	public Predicate visit(GetStatus getStatus) {
		return new PowerCurrentlyGenerated(getAID(), new PowerGenerated(ppower));
	}
}
