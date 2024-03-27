package dreamsoftware.smartgridoptimizer.agents;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jade.content.ContentElement;
import jade.content.Predicate;
import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionInitiator;
import jade.proto.SubscriptionResponder;
import jade.proto.SubscriptionResponder.Subscription;
import jade.proto.SubscriptionResponder.SubscriptionManager;

public abstract class PublishSubscribeAgent extends ISmartGridAgent {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(PublishSubscribeAgent.class);
	
	/**
	 * Subscriptions made to this agent.
	 */
	protected Set<Subscription> subscribers = new HashSet<Subscription>();
	
	/**
	 * Starts the FIPA-Subscribe dialog to subscribe to the services of the specified agents.
	 * @param agents
	 */
	private void subscribeToService(String serviceName) {

		DFAgentDescription agentDescription = new DFAgentDescription();
		agentDescription.addOntologies(ontology.getName());

		ServiceDescription serviceDescription = new ServiceDescription();
		serviceDescription.setType(ENERGY);
		serviceDescription.setName(serviceName);

		agentDescription.addServices(serviceDescription);

		addBehaviour(new SubscriptionInitiator(this, DFService.createSubscriptionMessage(this, getDefaultDF(),
				agentDescription, null)) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void handleInform(ACLMessage inform) {
				super.handleInform(inform);
				try {

					DFAgentDescription[] agentsDescription = DFService.decodeNotification(inform.getContent());
					
					if (agentsDescription.length > 0) {
						for(DFAgentDescription agentDesc: agentsDescription) {
							
							Iterator<ServiceDescription> services = agentDesc.getAllServices();
							
							if(services.hasNext()) {
								
								ACLMessage messageSub = new ACLMessage(ACLMessage.SUBSCRIBE);
								messageSub.setProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
								messageSub.addReceiver(agentDesc.getName());
								myAgent.addBehaviour(new SubscriptionInitiator(myAgent, messageSub) {
									
									private static final long serialVersionUID = 1L;

									@Override
									protected void handleInform(ACLMessage inform) {
										super.handleInform(inform);
										try {
											ContentElement ce = getContentManager().extractContent(inform);
											onNotify(ce);
										} catch (UngroundedException e) {
											e.printStackTrace();
										} catch (CodecException e) {
											e.printStackTrace();
										} catch (OntologyException e) {
											e.printStackTrace();
										}
									} 
								});
								
							}
				    		
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
	
	
	@Override
	protected void setup() {
		super.setup();
		
		// Get services to subscribe
		String [] services = getServicesToSubscribe();
		if(services != null && services.length > 0){
			subscribeToService(services);
		}
		
	    MessageTemplate template = SubscriptionResponder.createMessageTemplate(ACLMessage.SUBSCRIBE);
	    SubscriptionManager gestor = new SubscriptionManager() {
	    	 
            public boolean register(Subscription suscripcion) {
            	subscribers.add(suscripcion);
                return true;
            }
 
            public boolean deregister(Subscription suscripcion) {
            	subscribers.remove(suscripcion);
                return true;
            }
        };
        
        this.addBehaviour(new ManageSubscriptions(this, template, gestor));
       
	}
	
	@Override
	protected void takeDown() {
		super.takeDown();
		for(Subscription subscriber: subscribers)
			subscriber.close();
	}

	/**
	* Add the description of the services to which you want to subscribe
	*/
	protected String[] getServicesToSubscribe(){ return new String[] {}; };
	
	/**
	 * Notifies the agent the content received by the subscription
	 * @param ce
	 */
	protected void onNotify(ContentElement ce) {};
	
	protected void notifyToSubscribers(Predicate content) {
		try {
			ACLMessage msgInform = new ACLMessage(ACLMessage.INFORM);
			msgInform.setLanguage(codec.getName());
			msgInform.setOntology(ontology.getName());
			getContentManager().fillContent(msgInform, content);
			// Send Message to each Subscriptor
			for (Subscription subscription : subscribers) {
				subscription.notify(msgInform);
			}
		} catch (CodecException e) {
			e.printStackTrace();
		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}
	
	private class ManageSubscriptions extends SubscriptionResponder {
		
		private static final long serialVersionUID = 1L;
		
		private Subscription subscription;
		
		public ManageSubscriptions(Agent a, MessageTemplate mt, SubscriptionManager sm) {
			super(a, mt, sm);
		}

		@Override
		protected ACLMessage handleCancel(ACLMessage cancel) throws FailureException {

			logger.debug("Cancel received from: " + cancel.getSender().getLocalName());
			try {
				this.mySubscriptionManager.deregister(this.subscription);
			} catch (Exception e) {
				logger.debug("Unsubscription failed ...");
			}
			ACLMessage cancela = cancel.createReply();
			cancela.setPerformative(ACLMessage.INFORM);
			return cancela;
		}

		@Override
		protected ACLMessage handleSubscription(ACLMessage subscriptionMessage)
				throws NotUnderstoodException, RefuseException {
			
			logger.debug("Subscription request received from " + subscriptionMessage.getSender().getLocalName());
			
			this.subscription = this.createSubscription(subscriptionMessage);
			
			try {
				this.mySubscriptionManager.register(subscription);
			} catch (Exception e) {
				logger.debug("Subscription record failed.");
			}
			
            ACLMessage agree = subscriptionMessage.createReply();
            agree.setPerformative(ACLMessage.AGREE);
            return agree;
		}
	}

}
