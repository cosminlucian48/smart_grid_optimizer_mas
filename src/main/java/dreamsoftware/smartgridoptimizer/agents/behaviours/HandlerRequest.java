package dreamsoftware.smartgridoptimizer.agents.behaviours;

import jade.content.ContentElement;
import jade.content.Predicate;
import jade.core.Agent;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.AchieveREResponder;

import java.io.Serial;

/**
 * The HandlerRequest class is an abstract class that extends AchieveREResponder and provides a template for handling requests.
 */
public abstract class HandlerRequest extends AchieveREResponder {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a HandlerRequest object with the specified agent and message template.
	 * @param a the agent.
	 * @param mt the message template.
	 */
	public HandlerRequest(Agent a, MessageTemplate mt) {
		super(a, mt);
	}

	/**
	 * Handles the request message received by the agent.
	 * @param request the request message.
	 * @return an agree message if the request is valid.
	 * @throws RefuseException if the request is invalid or cannot be understood.
	 */
	@Override
	protected ACLMessage handleRequest(ACLMessage request) throws RefuseException {
		try {
			ContentElement ce = myAgent.getContentManager().extractContent(request);
			if(!isValidRequest(ce))
				throw new NotUnderstoodException(myAgent.getName() + " can not understand the message");
			ACLMessage agree = request.createReply();
			agree.setPerformative(ACLMessage.AGREE);
			return agree;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RefuseException("Error occurred when " + myAgent.getName() + " process the request");
		}
	}

	/**
	 * Prepares the result notification message to be sent after handling the request.
	 * @param request the request message.
	 * @param response the response message.
	 * @return an inform message with the result of handling the request.
	 * @throws FailureException if an error occurs while preparing the result notification.
	 */
	@Override
	protected ACLMessage prepareResultNotification(ACLMessage request, ACLMessage response)
			throws FailureException {
		try {
			ACLMessage inform = request.createReply();
            inform.setPerformative(ACLMessage.INFORM);
            ContentElement requestContent =  myAgent.getContentManager().extractContent(request);
            Predicate responseContent = onGenerateResponse(requestContent);
            myAgent.getContentManager().fillContent(inform, responseContent);
            return inform;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FailureException("Error occurred while storing power");
		}
	}

	/**
	 * Checks if the request is valid.
	 * @param ce the content element of the request.
	 * @return true if the request is valid, false otherwise.
	 */
	protected abstract boolean isValidRequest(ContentElement ce);

	/**
	 * Generates the response to the request.
	 * @param requestContent the content element of the request.
	 * @return the response predicate.
	 */
	protected abstract Predicate onGenerateResponse(ContentElement requestContent);
}
