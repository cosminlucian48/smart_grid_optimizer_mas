package dreamsoftware.smartgridoptimizer.agents.impl;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jade.util.leap.Iterator;
import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import jade.proto.SubscriptionInitiator;
import dreamsoftware.smartgridoptimizer.gui.ISmartGridMonitor;
import dreamsoftware.smartgridoptimizer.gui.SmartGridMonitor;
import dreamsoftware.smartgridoptimizer.ontology.SmartGridOntology;
import dreamsoftware.smartgridoptimizer.ontology.ISmartGridVocabulary;
import dreamsoftware.smartgridoptimizer.ontology.actions.ChangePowerConsumptionMeasures;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.CurrentBatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.predicates.CurrentConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.IterationStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierCurrentBudget;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierCurrentPrice;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierTotalPowerBought;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierTotalPowerSold;
import dreamsoftware.smartgridoptimizer.ontology.predicates.PowerCurrentlyGenerated;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

/**
 * The ClientAgent class represents an agent responsible for managing client-side operations in the smart grid system.
 * It extends the GuiAgent class and implements various interfaces related to the smart grid vocabulary and visitor patterns.
 */
public final class ClientAgent extends GuiAgent implements ISmartGridVocabulary, IClientVisitor {

	private final Logger logger = LoggerFactory.getLogger(ClientAgent.class);
	
	@Serial
	private static final long serialVersionUID = 1L;
	public final static String AGENT_NAME = "CLIENT_AGENT";
	
	private final static Long POLLING_INTERVAL = 500L;
	public final static Integer CHANGE_POWER_CONSUMPTION_MEASURES = 1;
	
	private final Codec codec = new SLCodec();
	private final Ontology ontology = SmartGridOntology.getInstance();
    private transient ISmartGridMonitor monitorGUI;
    
    private final List<AID> batteryAgentAids = new ArrayList<>();
    private final List<AID> generateEnergyAgentAids = new ArrayList<>();
    private final List<AID> loadEnergyAgentAids = new ArrayList<>();
    
    
    /***
     * Query Status
     * @throws OntologyException 
     * @throws CodecException 
     */
    private void queryStatus(List<AID> targets) throws CodecException, OntologyException {
    	
    	ACLMessage queryBatteryLevel = new ACLMessage(ACLMessage.QUERY_REF);
    	queryBatteryLevel.setProtocol(FIPANames.InteractionProtocol.FIPA_QUERY);
    	queryBatteryLevel.setLanguage(codec.getName());
    	queryBatteryLevel.setOntology(ontology.getName());
    	getContentManager().fillContent(queryBatteryLevel, new GetStatus());
    	
    	for(AID aid: targets) {
    		queryBatteryLevel.addReceiver(aid);
    	}
    	
    	this.addBehaviour(new AchieveREInitiator(this, queryBatteryLevel) {
    		
			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			protected void handleInform(ACLMessage inform) {
				super.handleInform(inform);
				try {
					ContentElement ce = getContentManager().extractContent(inform);
					IClientVisitable visitableMsg = (IClientVisitable)ce;
					visitableMsg.accept(ClientAgent.this);
				} catch (CodecException | OntologyException e) {
					e.printStackTrace();
				}
			}
    	});
    }
    
    private ACLMessage createDFSubscriptionFor(String serviceName, SearchConstraints constraint) {
    	DFAgentDescription agentDescription = new DFAgentDescription();
		agentDescription.addOntologies(ontology.getName());
		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType(ENERGY);
		serviceDescription.setName(serviceName);
		agentDescription.addServices(serviceDescription);
		return DFService.createSubscriptionMessage(this, getDefaultDF(),agentDescription, constraint);
    }
    
	private void subscribeToService(String serviceName) {
		SearchConstraints onlyOne = new SearchConstraints();
		onlyOne.setMaxResults(-1L);
		addBehaviour(new SubscriptionInitiator(this, createDFSubscriptionFor(serviceName, onlyOne)) {

			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			protected void handleInform(ACLMessage inform) {
				super.handleInform(inform);
				try {
					DFAgentDescription[] agentsDescription = DFService.decodeNotification(inform.getContent());
					if (agentsDescription.length > 0) {
						for(DFAgentDescription agentDesc: agentsDescription) {
				    		ACLMessage messageSub = new ACLMessage(ACLMessage.SUBSCRIBE);
							messageSub.setProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
							messageSub.addReceiver(agentDesc.getName());
							myAgent.addBehaviour(new SubscriptionInitiator(myAgent, messageSub) {
								
								@Serial
								private static final long serialVersionUID = 1L;

								@Override
								protected void handleInform(ACLMessage inform) {
									super.handleInform(inform);
									try {
										ContentElement ce = getContentManager().extractContent(inform);
										IClientVisitable visitableMsg = (IClientVisitable)ce;
										visitableMsg.accept(ClientAgent.this);
									} catch (CodecException | OntologyException e) {
										e.printStackTrace();
									}
								} 
							});
				    	}
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			}
		});
	}
    
	private void subscribeToService(String[] serviceNames) { 
		for(String serviceName: serviceNames) 
			subscribeToService(serviceName);
	}
	
	private void observeAgents() {
		
		// Observe Generate Energy Agents
		addBehaviour(new SubscriptionInitiator(this, createDFSubscriptionFor(REPORT_GENERATED_ENERGY, null)) {

			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			protected void handleInform(ACLMessage inform) {
				super.handleInform(inform);
				try {
					DFAgentDescription[] agentsDescription = DFService.decodeNotification(inform.getContent());
					if (agentsDescription.length > 0) {
						for(DFAgentDescription agentDesc: agentsDescription) {
							Iterator services = agentDesc.getAllServices();
							if(services.hasNext()) {
								loadEnergyAgentAids.add(agentDesc.getName());
								monitorGUI.addGenerateEnergyAgent(agentDesc.getName().getLocalName());
							} else {
								loadEnergyAgentAids.remove(agentDesc.getName());
								monitorGUI.removeGenerateEnergyAgent(agentDesc.getName().getLocalName());
							}
						}
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			}
		});
		
		//Observe Load Energy Agents
		addBehaviour(new SubscriptionInitiator(this, createDFSubscriptionFor(REPORT_LOAD_ENERGY, null)) {

			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			protected void handleInform(ACLMessage inform) {
				super.handleInform(inform);
				try {
					DFAgentDescription[] agentsDescription = DFService.decodeNotification(inform.getContent());
					if (agentsDescription.length > 0) {
						for(DFAgentDescription agentDesc: agentsDescription) {
							Iterator services = agentDesc.getAllServices();
							if(services.hasNext()) {
								generateEnergyAgentAids.add(agentDesc.getName());
								monitorGUI.addConsumptionEnergyAgent(agentDesc.getName().getLocalName());
							} else {
								generateEnergyAgentAids.remove(agentDesc.getName());
								monitorGUI.removeConsumptionEnergyAgent(agentDesc.getName().getLocalName());
							}
						}
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			}
		});
		
		// Observe battery Agents
		addBehaviour(new SubscriptionInitiator(this, createDFSubscriptionFor(REPORT_BATTERY_LEVEL, null)) {

			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			protected void handleInform(ACLMessage inform) {
				super.handleInform(inform);
				try {
					DFAgentDescription[] agentsDescription = DFService.decodeNotification(inform.getContent());
					if (agentsDescription.length > 0) { 
						for (DFAgentDescription agentDesc : agentsDescription) {
		                    Iterator services = agentDesc.getAllServices();
		                    if (services.hasNext()) {
		                    	batteryAgentAids.add(agentDesc.getName());
		                    	monitorGUI.addBatteryAgent(agentDesc.getName().getLocalName());
		                    } else {
		                    	batteryAgentAids.remove(agentDesc.getName());
		                    	monitorGUI.removeBatteryAgent(agentDesc.getName().getLocalName());
		                    }
		                }
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			}
		});
	}
    
	@Override
	protected void setup() {
		super.setup();
		getContentManager().registerLanguage(codec);
		getContentManager().registerOntology(ontology);
		
		monitorGUI = new SmartGridMonitor(this);
		// Init subscriptions to another agents
		subscribeToService(new String[] { REPORT_ITERATION_STATUS, REPORT_TOTAL_BOUGHT, REPORT_TOTAL_SOLD });
		// observe agents
		observeAgents();
		
		// We plan behavior to polling the status of all available agents
		this.addBehaviour(new TickerBehaviour(this, POLLING_INTERVAL) {
			
			@Serial
			private static final long serialVersionUID = 1L;

			@Override
			protected void onTick() {
				try {
					// Query Battery Levels.
					queryStatus(batteryAgentAids);
					// Query Power Generate
					queryStatus(generateEnergyAgentAids);
					// Query Consumed energy
					queryStatus(loadEnergyAgentAids);
				} catch (CodecException | OntologyException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onGuiEvent(GuiEvent guiEvent) {
		int command = guiEvent.getType();
		if (command == CHANGE_POWER_CONSUMPTION_MEASURES) { 
			
			DFAgentDescription dfAgentDescription = new DFAgentDescription();
			dfAgentDescription.addOntologies(ontology.getName());
                        dfAgentDescription.addLanguages(codec.getName());
			
			ServiceDescription service = new ServiceDescription();
			service.setType(ENERGY);
			service.setName(REPORT_LOAD_ENERGY);
			
			dfAgentDescription.addServices(service);
			
			try {
				
				DFAgentDescription[] agentsDesc = DFService.search(this, dfAgentDescription);
				logger.info("Report Load Energy Agents Founded -> " + agentsDesc.length);
				
				if(agentsDesc.length > 0) { 
					
					ACLMessage fipaRequestMessage = new ACLMessage(ACLMessage.REQUEST);
					fipaRequestMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
					fipaRequestMessage.setLanguage(codec.getName());
					fipaRequestMessage.setOntology(ontology.getName());
					List<Double> measures = (List<Double>)guiEvent.getParameter(0);
					ArrayList<LoadConsumption> powerConsumptionMeasures = new ArrayList<LoadConsumption>();
					for(Double measure: measures)
						powerConsumptionMeasures.add(new LoadConsumption(measure));
					getContentManager().fillContent(fipaRequestMessage, new ChangePowerConsumptionMeasures(new jade.util.leap.ArrayList(powerConsumptionMeasures)));
					for(DFAgentDescription agent: agentsDesc) {
						fipaRequestMessage.addReceiver(agent.getName());
					}
					this.addBehaviour(new AchieveREInitiator(this, fipaRequestMessage) {
						@Serial
						private static final long serialVersionUID = 1L;
					});
				}
			} catch (FIPAException | CodecException | OntologyException e) {
				e.printStackTrace();
			}
		}
	}

	public void visit(IterationStatus iterationStatus) {
		monitorGUI.setWindPower(iterationStatus.getPowerGeneratedValue());
		monitorGUI.setLoadValue(iterationStatus.getLoadConsumptionValue());
		monitorGUI.setAdjustLoad(iterationStatus.getAdjustLoadValue());
		monitorGUI.setPowerBought(iterationStatus.getPowerBoughtValue());
		monitorGUI.setPowerSold(iterationStatus.getPowerSoldValue());
		monitorGUI.setSurplus(iterationStatus.getSurplusValue());
		monitorGUI.setTotalBattery(iterationStatus.getBatteryLevelValue());
	}

	public void visit(NotifierTotalPowerBought notifierTotalPowerBought) {
		monitorGUI.setTotalBought(notifierTotalPowerBought.getPowerBought().getPowerBoughtValue());
	}

	public void visit(NotifierTotalPowerSold notifierTotalPowerSold) {
		monitorGUI.setTotalSold(notifierTotalPowerSold.getPowerSold().getPowerSoldValue());
	}

	public void visit(CurrentConsumption currentConsumption) {
		monitorGUI.updateAgentValue(currentConsumption.getAid().getLocalName(), currentConsumption.getLoadConsumption().getLoadConsumptionValue());
	}

	public void visit(CurrentBatteryLevel currentBatteryLevel) {
		monitorGUI.updateAgentValue(currentBatteryLevel.getAid().getLocalName(), currentBatteryLevel.getBatteryLevel().getBatteryLevelValue());
	}

	public void visit(PowerCurrentlyGenerated powerCurrentlyGenerated) {
		monitorGUI.updateAgentValue(powerCurrentlyGenerated.getAid().getLocalName(), powerCurrentlyGenerated.getPowerGenerated().getPowerGeneratedValue());
	}

    public void visit(NotifierCurrentPrice notifierCurrentPrice) {
    	monitorGUI.setCurrentPrice(notifierCurrentPrice.getCurrentPrice().getCurrentPriceValue());
    }

	public void visit(NotifierCurrentBudget notifierCurrentBudget) {
		monitorGUI.setCurrentBudget(notifierCurrentBudget.getCurrentBudget().getCurrentBudgetValue());
	}
}
