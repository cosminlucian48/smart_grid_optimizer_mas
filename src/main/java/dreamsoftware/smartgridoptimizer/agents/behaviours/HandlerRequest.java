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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class HandlerRequest extends AchieveREResponder {
    
    private Logger logger = LoggerFactory.getLogger(HandlerRequest.class);
	
	private static final long serialVersionUID = 1L;

	public HandlerRequest(Agent a, MessageTemplate mt) {
		super(a, mt);
	}

	@Override
	protected ACLMessage handleRequest(ACLMessage request) throws NotUnderstoodException, RefuseException {

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
	
	
	protected abstract boolean isValidRequest(ContentElement ce);
	
	protected abstract Predicate onGenerateResponse(ContentElement requestContent);
	
}
