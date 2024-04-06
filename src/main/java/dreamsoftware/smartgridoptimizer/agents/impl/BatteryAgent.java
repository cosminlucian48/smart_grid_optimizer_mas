package dreamsoftware.smartgridoptimizer.agents.impl;

import java.io.Serial;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.j256.simplejmx.common.JmxAttributeFieldInfo;
import com.j256.simplejmx.common.JmxAttributeMethodInfo;
import com.j256.simplejmx.common.JmxOperationInfo;
import com.j256.simplejmx.common.JmxResourceInfo;
import jade.content.ContentElement;
import jade.content.Predicate;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import dreamsoftware.smartgridoptimizer.agents.PublishSubscribeAgent;
import dreamsoftware.smartgridoptimizer.agents.behaviours.HandlerRequest;
import dreamsoftware.smartgridoptimizer.ontology.concepts.BatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.predicates.CurrentBatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierBatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.predicates.RetrieveDemand;
import dreamsoftware.smartgridoptimizer.ontology.predicates.StoreInBattery;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IBatteryVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IBatteryVisitor;

/**
 * The BatteryAgent class represents an agent responsible for storing and retrieving energy within the energy management system.
 * It extends the PublishSubscribeAgent class and implements the IBatteryVisitor interface.
 */
public final class BatteryAgent extends PublishSubscribeAgent implements IBatteryVisitor {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private final Logger logger = LoggerFactory.getLogger(BatteryAgent.class);
	
	public static final String AGENT_NAME = "BatteryAgent";
	public final static String AGENT_DESCRIPTION = "Agents that store energy";

	public static Double CELL_MAX_BAT_LEVEL = 20.0;
	private static final Integer NOTIFY_INTERVAL = 1000;
	
	private Double pbat = 0.0;
	
	private final HandlerRequest handlerRequest = new HandlerRequest(this, fipaRequestTemplate) {
		
		@Serial
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean isValidRequest(ContentElement ce) {
			return ce instanceof StoreInBattery || ce instanceof RetrieveDemand || ce instanceof GetStatus;
		}

		@Override
		protected Predicate onGenerateResponse(ContentElement requestContent) {
			IBatteryVisitable requestVisitable = (IBatteryVisitable)requestContent;
			return requestVisitable.accept(BatteryAgent.this);
		}
	};

	/**
	 * Initializes the BatteryAgent by setting up behaviors and registering services.
	 */
	@Override
	protected void setup() {
		super.setup();
		this.addBehaviour(handlerRequest);
		// Tick Behaviour to periodically report your battery level.
		this.addBehaviour(new TickerBehaviour(this, NOTIFY_INTERVAL) {
			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTick() {
				logger.debug("Notify battery level: " + pbat);
				// notify load to subscribers.
				BatteryAgent.this.notifyToSubscribers(new NotifierBatteryLevel(getAID(),new BatteryLevel(pbat)));
			}
		});
	}

	/**
	 * Registers services provided by the BatteryAgent.
	 * @param description the agent's description.
	 */
	@Override
	protected void onRegisterServices(DFAgentDescription description) {
		ServiceDescription service = new ServiceDescription();
        service.setType(ENERGY);
        service.setName(REPORT_BATTERY_LEVEL);
        description.addServices(service);
	}

	/**
	 * Processes a StoreInBattery request.
	 * @param storeInBattery the request.
	 * @return the response predicate.
	 */
	public Predicate visit(StoreInBattery storeInBattery) {
		logger.debug("Storage " + storeInBattery.getSurplus().getSurplusValue() + " in battery " + this.getName());
		pbat += storeInBattery.getSurplus().getSurplusValue();
		logger.debug("Notify battery level: " + pbat);
		return new NotifierBatteryLevel(getAID(), new BatteryLevel(pbat));
	}

	/**
	 * Processes a RetrieveDemand request.
	 * @param retrieveDemand the request.
	 * @return the response predicate.
	 */
	public Predicate visit(RetrieveDemand retrieveDemand) {
		logger.debug("Retrieve " + retrieveDemand.getSurplus().getSurplusValue() + " from battery " + this.getName());
		pbat -= retrieveDemand.getSurplus().getSurplusValue();
		logger.debug("Notify battery level: " + pbat);
		return new NotifierBatteryLevel(getAID(), new BatteryLevel(pbat));
	}

	/**
	 * Processes a GetStatus request.
	 * @param getStatus the request.
	 * @return the response predicate.
	 */
	public Predicate visit(GetStatus getStatus) {
		return new CurrentBatteryLevel(getAID(), new BatteryLevel(pbat));
	}

	/**
	 * Sets up JMX MBean attributes for BatteryAgent.
	 * @param resourceInfo the resource information.
	 * @param attributeFieldInfoList the list of attribute field information.
	 * @param attributeMethodInfoList the list of attribute method information.
	 * @param operationInfoList the list of operation information.
	 */
	@Override
	protected void onCreateMBean(JmxResourceInfo resourceInfo, List<JmxAttributeFieldInfo> attributeFieldInfoList,
			List<JmxAttributeMethodInfo> attributeMethodInfoList, List<JmxOperationInfo> operationInfoList) {
		
		resourceInfo.setJmxDomainName(AGENT_NAME);
		resourceInfo.setJmxBeanName(this.getAID().getLocalName());
		resourceInfo.setJmxDescription(AGENT_DESCRIPTION);
		
		attributeFieldInfoList.add(new JmxAttributeFieldInfo("CELL_MAX_BAT_LEVEL", true, true, "Cell Max Battery Level for all Battery Agents"));
	}
}
