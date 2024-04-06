package dreamsoftware.smartgridoptimizer.agents.impl;

import com.j256.simplejmx.common.JmxAttributeFieldInfo;
import com.j256.simplejmx.common.JmxAttributeMethodInfo;
import com.j256.simplejmx.common.JmxOperationInfo;
import com.j256.simplejmx.common.JmxResourceInfo;
import dreamsoftware.smartgridoptimizer.agents.NotifierAgent;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.concepts.Surplus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.*;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IControllerVisitor;
import jade.content.ContentElement;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.proto.SubscriptionInitiator;
import jade.proto.SubscriptionResponder.Subscription;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ControllerAgent extends NotifierAgent implements IControllerVisitor {
	
	private final Logger logger = LoggerFactory.getLogger(ControllerAgent.class);
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	public final static String AGENT_NAME = "CONTROLLER_AGENT";
	public final static String AGENT_DESCRIPTION = "Main agent responsible for coordinating all other agents";
	
	private final static Integer CHECK_POWER_INTERVAL = 1000;
	
	private final Map<AID, Double> batteryLevels = new HashMap<>();
	
	private DFAgentDescription[] loadEnergyAgents = {};
	private DFAgentDescription[] generatedEnergyAgents = {};
	private DFAgentDescription[] batteryAgents = {};

    private Double ppower = 0.0; //power generated
    private Double pload = 0.0; //load consumption
    
    
    // Lookup Report Load Energy Agents
    private void lookupLoadEnergyAgents() {
    	
    	// lookup power load agents
    	DFAgentDescription reportLoadEnergyDescription = new DFAgentDescription();
		reportLoadEnergyDescription.addOntologies(ontology.getName());
		
		ServiceDescription reportLoadEnergyService = new ServiceDescription();
		reportLoadEnergyService.setType(ENERGY);
		reportLoadEnergyService.setName(REPORT_LOAD_ENERGY);
		
		reportLoadEnergyDescription.addServices(reportLoadEnergyService);
		
		addBehaviour(new SubscriptionInitiator(this, DFService.createSubscriptionMessage(this, getDefaultDF(), reportLoadEnergyDescription, null)) {
			
			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			protected void handleInform(ACLMessage inform) {
				super.handleInform(inform);
				logger.debug("Agent "+getLocalName()+": Notification received from DF");
				try {
					DFAgentDescription[] results = DFService.decodeNotification(inform.getContent());
					logger.debug("LoadEnergyAgents: " + results.length);
					if (results.length > 0) { 
						subscribeToServices(results);
						loadEnergyAgents = (DFAgentDescription[]) ArrayUtils.addAll(loadEnergyAgents, results);
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			} 
		});
    }
    
    // Lookup Generated Energy Agents
	private void lookupGeneratedEnergyAgents() {
		// lookup generate energy agents
		DFAgentDescription reportGeneratedEnergyDescription = new DFAgentDescription();
		reportGeneratedEnergyDescription.addOntologies(ontology.getName());

		ServiceDescription reportGeneratedEnergyService = new ServiceDescription();
		reportGeneratedEnergyService.setType(ENERGY);
		reportGeneratedEnergyService.setName(REPORT_GENERATED_ENERGY);

		reportGeneratedEnergyDescription.addServices(reportGeneratedEnergyService);

		addBehaviour(new SubscriptionInitiator(this, DFService.createSubscriptionMessage(this, getDefaultDF(), reportGeneratedEnergyDescription, null)) {

			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			protected void handleInform(ACLMessage inform) {
				super.handleInform(inform);
				logger.debug("Agent " + getLocalName() + ": Notification received from DF");
				try {
					DFAgentDescription[] results = DFService.decodeNotification(inform.getContent());
					logger.debug("GeneratedEnergyAgents: " + results.length);
					if (results.length > 0) {
						subscribeToServices(results);
						generatedEnergyAgents = (DFAgentDescription[]) ArrayUtils.addAll(generatedEnergyAgents, results);
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	// Lookup Battery Agents
	private void lookupBatteryAgents() {
		// lookup battery agents
		DFAgentDescription batteriesAgentsDescription = new DFAgentDescription();
		batteriesAgentsDescription.addOntologies(ontology.getName());

		ServiceDescription reportBatteryLevelService = new ServiceDescription();
		reportBatteryLevelService.setType(ENERGY);
		reportBatteryLevelService.setName(REPORT_BATTERY_LEVEL);

		batteriesAgentsDescription.addServices(reportBatteryLevelService);
		
		addBehaviour(new SubscriptionInitiator(this, DFService.createSubscriptionMessage(this, getDefaultDF(), batteriesAgentsDescription, null)) {

			@Serial
			private static final long serialVersionUID = 1L;
		
			@Override
			protected void handleInform(ACLMessage inform) {
				super.handleInform(inform);
				logger.debug("Agent " + getLocalName() + ": Notification received from DF");
				try {
					DFAgentDescription[] results = DFService.decodeNotification(inform.getContent());
					logger.debug("BatteryAgents: " + results.length);
					if (results.length > 0) {
						batteryAgents = (DFAgentDescription[]) ArrayUtils.addAll(batteryAgents, results);
						for(DFAgentDescription result: results)
							batteryLevels.put(result.getName(), 0.0);
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void subscribeToServices(DFAgentDescription[] agents) {
		for (DFAgentDescription agentDes : agents) {
			// subscribe to each agent.
			ACLMessage messageSub = new ACLMessage(ACLMessage.SUBSCRIBE);
			messageSub.setProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
			messageSub.addReceiver(agentDes.getName());
			this.addBehaviour(new SubscribeToService(this, messageSub));
		}
	}
	
	@Override
	protected void setup() {
		super.setup();
		lookupLoadEnergyAgents();
		lookupGeneratedEnergyAgents();
		lookupBatteryAgents();

		this.addBehaviour(new CheckPower(this, CHECK_POWER_INTERVAL));
	}

	public void visit(GenerateEnergy generateEnergy) {
		logger.debug("GenerateEnergy received ...");
		if(generateEnergy.getPowerGenerated() != null)
			ppower += generateEnergy.getPowerGenerated().getPowerGeneratedValue();
	}

	public void visit(PerformConsumption performConsumption) {
		logger.debug("PerformConsumption received ...");
		if(performConsumption.getLoadConsumption() != null)
			pload += performConsumption.getLoadConsumption().getLoadConsumptionValue();
	}
	
	public void visit(NotifierBatteryLevel notifierBatteryLevel) {
		logger.debug("Battery Level value " + notifierBatteryLevel.getBatteryLevel().getBatteryLevelValue());
		batteryLevels.put(notifierBatteryLevel.getAid(), notifierBatteryLevel.getBatteryLevel().getBatteryLevelValue());
	}
	
	@Override
	protected void onRegisterServices(DFAgentDescription description) {
		ServiceDescription service = new ServiceDescription();
        service.setType(ENERGY);
        service.setName(REPORT_ITERATION_STATUS);
        description.addServices(service);
	}
	
	@Override
	protected void onCreateMBean(JmxResourceInfo resourceInfo, List<JmxAttributeFieldInfo> attributeFieldInfoList,
								 List<JmxAttributeMethodInfo> attributeMethodInfoList, List<JmxOperationInfo> operationInfoList) {
		resourceInfo.setJmxDomainName(AGENT_NAME);
		resourceInfo.setJmxBeanName(this.getAID().getLocalName());
		resourceInfo.setJmxDescription(AGENT_DESCRIPTION);
	}

	private class SubscribeToService extends SubscriptionInitiator {

		@Serial
		private static final long serialVersionUID = 1L;
		
		public SubscribeToService(Agent a, ACLMessage msg) {
			super(a, msg);
		}

		@Override
		protected void handleAgree(ACLMessage agree) {
			super.handleAgree(agree);
			logger.debug(agree.getSender().getName() + " accept request subscription ...");
		}

		@Override
		protected void handleRefuse(ACLMessage refuse) {
			super.handleRefuse(refuse);
			logger.debug(refuse.getSender().getName() + " refuse request subscription ...");
		}

		@Override
		protected void handleInform(ACLMessage inform) {
			super.handleInform(inform);
			try {
				ContentElement ce = getContentManager().extractContent(inform);
				@SuppressWarnings("unchecked")
				IVisitable<IControllerVisitor> visitableMsg = (IVisitable<IControllerVisitor>)ce;
				visitableMsg.accept(ControllerAgent.this);
			} catch (CodecException | OntologyException e) {
				e.printStackTrace();
			}
		}
	}

	private class CheckPower extends TickerBehaviour {

		@Serial
		private static final long serialVersionUID = 1L;

		public CheckPower(Agent a, long period) {
			super(a, period);
		}

		private Double getTotalBattery() {
			Double totalbattery = 0.0;
			for (Entry<AID, Double> pair : batteryLevels.entrySet()) {
				totalbattery += pair.getValue();
			}
			return totalbattery;
		}

		@Override
		protected void onTick() {
			double sp = ppower - pload;
			logger.debug("surplus:" + sp);
			try {
				double toBuy = 0.0;
				double pToSell = 0.0;
				double batteryLevel = 0.0;
				double adjustLoad = 0.0;
				Double totalBattery = getTotalBattery();
				Double maxBat = BatteryAgent.CELL_MAX_BAT_LEVEL * batteryLevels.size();
				// get battery agents AID
				List<AID> batteryAgentsAID = new ArrayList<>();
				for (DFAgentDescription batteryAgent : batteryAgents)
					batteryAgentsAID.add(batteryAgent.getName());
				if (sp > 0) {
					// if we have surplus try to store it in the battery and
					// sell the rest
					double batteryRoom = maxBat - totalBattery;
					logger.debug("batteryRoom -> " + batteryRoom);
					ACLMessage storeInBatMessage;
					if (sp <= batteryRoom) {
						// store in bat sp
						logger.debug("storing in bat:" + sp);
						batteryLevel = totalBattery + sp;
						storeInBatMessage = createFipaRequest(
								new StoreInBattery(new Surplus(sp / batteryLevels.size())), batteryAgentsAID);
					} else {
						logger.debug("store the maximum available ...");
						batteryLevel = totalBattery + batteryRoom;
						storeInBatMessage = createFipaRequest(
								new StoreInBattery(new Surplus(batteryRoom / batteryLevels.size())), batteryAgentsAID);
						pToSell = sp - batteryRoom;
						ACLMessage sellPowerMessage = createFipaRequest(new SellingPower(new Surplus(pToSell)),
								new AID(MarketAgent.AGENT_NAME, AID.ISLOCALNAME));
						myAgent.addBehaviour(new SellingPowerRequest(myAgent, sellPowerMessage));
					}
					myAgent.addBehaviour(new StorePowerInBattery(myAgent, storeInBatMessage));

				} else {
					Double demand = Math.abs(sp);
					logger.debug("totalBattery -> " + totalBattery);
					logger.debug("Power demanded: " + demand);
					// Retrieve stored power and buy more power if needed
					ACLMessage retrieveDemandMessage;
					if(demand <= totalBattery){
						batteryLevel = totalBattery - demand;
						retrieveDemandMessage = createFipaRequest(
								new RetrieveDemand(new Surplus(demand / batteryLevels.size())), batteryAgentsAID);
					} else {
						batteryLevel = 0.0;
						retrieveDemandMessage = createFipaRequest(
								new RetrieveDemand(new Surplus(totalBattery / batteryLevels.size())), batteryAgentsAID);
						// power to buy
						toBuy = demand - totalBattery;
						logger.debug("buy power: " + toBuy);
						ACLMessage buyPowerRequest = createFipaRequest(new BuyPower(new Surplus(toBuy)),
								new AID(MarketAgent.AGENT_NAME, AID.ISLOCALNAME));
						myAgent.addBehaviour(new BuyPowerRequest(myAgent, buyPowerRequest));
					}
					myAgent.addBehaviour(new RetrieveDemandRequest(myAgent, retrieveDemandMessage));
					
					// update loads with this power.
					logger.debug("Update Loads ....");
					List<AID> receivers = new ArrayList<>();
					for (DFAgentDescription loadEnergyAgent : loadEnergyAgents) {
						receivers.add(loadEnergyAgent.getName());
					}
					adjustLoad = pload - demand;
					ACLMessage updateLoadRequest = createFipaRequest(
							new UpdateLoad(new LoadConsumption(demand / loadEnergyAgents.length)), receivers);
					myAgent.addBehaviour(new UpdateLoadRequest(myAgent, updateLoadRequest));
				}
				IterationStatus iteStatus = new IterationStatus();
				iteStatus.setPowerGeneratedValue(ppower);
				iteStatus.setLoadConsumptionValue(pload);
				iteStatus.setSurplusValue(sp);
				iteStatus.setPowerBoughtValue(toBuy);
				iteStatus.setPowerSoldValue(pToSell);
				iteStatus.setBatteryLevelValue(batteryLevel);
				iteStatus.setAdjustLoadValue(adjustLoad);

				myAgent.addBehaviour(new NotifyIterationStatus(iteStatus));

			} catch (CodecException | OntologyException e) {
				e.printStackTrace();
			}
			// reset data
			ppower = 0.0;
			pload = 0.0;
		}
	}
	
	/**
	 * Behaviour for Notify the current Iteration Status
	 */
	private class NotifyIterationStatus extends OneShotBehaviour {

		@Serial
		private static final long serialVersionUID = 1L;
		
		private final IterationStatus iterationStatus;
		
		public NotifyIterationStatus(IterationStatus iterationStatus) {
			super();
			this.iterationStatus = iterationStatus;
		}

		@Override
		public void action() {
			try {
				ACLMessage msgInform = new ACLMessage(ACLMessage.INFORM);
				msgInform.setLanguage(codec.getName());
				msgInform.setOntology(ontology.getName());
				getContentManager().fillContent(msgInform, iterationStatus);
				// Send Message to each subscription
				for (Subscription subscription : subscriptions) {
					subscription.notify(msgInform);
				}
			} catch (CodecException | OntologyException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * Request for init buy power process
	 */
	private static class BuyPowerRequest extends AchieveREInitiator {
		
		@Serial
		private static final long serialVersionUID = 1L;

		public BuyPowerRequest(Agent a, ACLMessage msg) {
			super(a, msg);
		}

		@Override
		protected void handleInform(ACLMessage inform) {
			super.handleInform(inform);
		}

		@Override
		protected void handleRefuse(ACLMessage refuse) {
			super.handleRefuse(refuse);
		}
	}
	
	/**
	 * Request for init selling power process
	 */
	private class SellingPowerRequest extends AchieveREInitiator {
		
		@Serial
		private static final long serialVersionUID = 1L;

		public SellingPowerRequest(Agent a, ACLMessage msg) {
			super(a, msg);
		}

		@Override
		protected void handleInform(ACLMessage inform) {
			super.handleInform(inform);
		}

		@Override
		protected void handleRefuse(ACLMessage refuse) {
			super.handleRefuse(refuse);
			logger.debug("Market can`t sell the energy");
		}
	}
	
	/**
	 * Request for Retrieve power demand request
	 */
	private class RetrieveDemandRequest extends AchieveREInitiator {

		@Serial
		private static final long serialVersionUID = 1L;

		public RetrieveDemandRequest(Agent a, ACLMessage msg) {
			super(a, msg);
		}

		@Override
		protected void handleInform(ACLMessage inform) {
			super.handleInform(inform);
			try {
				NotifierBatteryLevel notifierBatteryLevel = (NotifierBatteryLevel)getContentManager().extractContent(inform);
				batteryLevels.put(inform.getSender(), notifierBatteryLevel.getBatteryLevel().getBatteryLevelValue());
			} catch (Exception e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

		@Override
		protected void handleRefuse(ACLMessage refuse) {
			super.handleRefuse(refuse);
			logger.debug("Energy can`t retrieved from battery " + refuse.getSender().getName());
		}
	}
	
	/**
	 * Request for store power in batteries process
	 */
	private class StorePowerInBattery extends AchieveREInitiator {

		@Serial
		private static final long serialVersionUID = 1L;
		
		public StorePowerInBattery(Agent a, ACLMessage msg) {
			super(a, msg);
		}

		@Override
		protected void handleInform(ACLMessage inform) {
			super.handleInform(inform);
			logger.debug("Energy stored in the battery " + inform.getSender().getName());
			try {
				NotifierBatteryLevel notifierBatteryLevel = (NotifierBatteryLevel)getContentManager().extractContent(inform);
				logger.debug("Battery Level value " + notifierBatteryLevel.getBatteryLevel().getBatteryLevelValue());
				batteryLevels.put(inform.getSender(), notifierBatteryLevel.getBatteryLevel().getBatteryLevelValue());
			} catch (Exception e) {
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}

		@Override
		protected void handleRefuse(ACLMessage refuse) {
			super.handleRefuse(refuse);
			logger.debug("Energy can`t stored in the battery " + refuse.getSender().getName());
		}
	}
	
	/**
	 * Request for update load for all product load agents
	 */
	private static class UpdateLoadRequest extends AchieveREInitiator {

		@Serial
		private static final long serialVersionUID = 1L;

		public UpdateLoadRequest(Agent a, ACLMessage msg) {
			super(a, msg);
		}

		@Override
		protected void handleInform(ACLMessage inform) {
			super.handleInform(inform);
		}

		@Override
		protected void handleRefuse(ACLMessage refuse) {
			super.handleRefuse(refuse);
		}
	}
}
