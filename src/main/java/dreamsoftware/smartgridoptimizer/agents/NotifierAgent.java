package dreamsoftware.smartgridoptimizer.agents;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionResponder;
import jade.proto.SubscriptionResponder.Subscription;
import jade.proto.SubscriptionResponder.SubscriptionManager;

public abstract class NotifierAgent extends ISmartGridAgent {
	
	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(NotifierAgent.class);

	protected Set<Subscription> subscriptions = new HashSet<Subscription>();
	
	@Override
	protected void setup() {
		super.setup();
	    MessageTemplate template = SubscriptionResponder.createMessageTemplate(ACLMessage.SUBSCRIBE);
	    SubscriptionManager gestor = new SubscriptionManager() {
	    	 
            public boolean register(Subscription suscripcion) {
            	subscriptions.add(suscripcion);
                return true;
            }
 
            public boolean deregister(Subscription suscripcion) {
            	subscriptions.remove(suscripcion);
                return true;
            }
        };
        this.addBehaviour(new ManageSubscriptions(this, template, gestor));
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
