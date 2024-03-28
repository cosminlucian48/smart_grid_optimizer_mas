package dreamsoftware.smartgridoptimizer.agents;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionResponder;
import jade.proto.SubscriptionResponder.Subscription;
import jade.proto.SubscriptionResponder.SubscriptionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

public abstract class NotifierAgent extends ISmartGridAgent {
	
	@Serial
	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(NotifierAgent.class);

	protected Set<Subscription> subscriptions = new HashSet<>();
	
	@Override
	protected void setup() {
		super.setup();
	    MessageTemplate template = SubscriptionResponder.createMessageTemplate(ACLMessage.SUBSCRIBE);
	    SubscriptionManager manager = new SubscriptionManager() {
            public boolean register(Subscription subscription) {
            	subscriptions.add(subscription);
                return true;
            }
            public boolean deregister(Subscription subscription) {
            	subscriptions.remove(subscription);
                return true;
            }
        };
        this.addBehaviour(new ManageSubscriptions(this, template, manager));
	}
	

	private class ManageSubscriptions extends SubscriptionResponder {
		
		@Serial
		private static final long serialVersionUID = 1L;
		
		private Subscription subscription;
		
		public ManageSubscriptions(Agent a, MessageTemplate mt, SubscriptionManager sm) {
			super(a, mt, sm);
		}

		@Override
		protected ACLMessage handleCancel(ACLMessage cancel) {

			logger.debug("Cancel received from: " + cancel.getSender().getLocalName());
			try {
				this.mySubscriptionManager.deregister(this.subscription);
			} catch (Exception e) {
				logger.debug("deregister failed ...");
			}
			ACLMessage cancelReply = cancel.createReply();
			cancelReply.setPerformative(ACLMessage.INFORM);
			return cancelReply;
		}

		@Override
		protected ACLMessage handleSubscription(ACLMessage subscriptionMessage) {
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
