package dreamsoftware.smartgridoptimizer.agents;

import java.util.ArrayList;
import java.util.List;

import javax.management.JMException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.simplejmx.common.JmxAttributeFieldInfo;
import com.j256.simplejmx.common.JmxAttributeMethodInfo;
import com.j256.simplejmx.common.JmxOperationInfo;
import com.j256.simplejmx.common.JmxResourceInfo;
import jade.content.Predicate;
import jade.content.lang.Codec;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import dreamsoftware.smartgridoptimizer.MainContainer;
import dreamsoftware.smartgridoptimizer.agents.impl.PowerLoadAgent;
import dreamsoftware.smartgridoptimizer.ontology.SmartGridOntology;
import dreamsoftware.smartgridoptimizer.ontology.ISmartGridVocabulary;
import jade.core.AID;

public abstract class ISmartGridAgent extends Agent implements ISmartGridVocabulary {
	
	private Logger logger = LoggerFactory.getLogger(PowerLoadAgent.class);

	private static final long serialVersionUID = 1L;
	
	protected Codec codec = new SLCodec();
	protected Ontology ontology = SmartGridOntology.getInstance();
	protected MessageTemplate fipaRequestTemplate;
	private JmxResourceInfo resourceInfo;
	
	@Override
	protected void setup() {
		super.setup();
		
		getContentManager().registerLanguage(codec);
	    getContentManager().registerOntology(ontology);
	    
	    MessageTemplate protocol = MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
        MessageTemplate performative = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        fipaRequestTemplate = MessageTemplate.and(protocol, performative);
	    
	    DFAgentDescription description = new DFAgentDescription();
		description.setName(getAID());
		description.addOntologies(ontology.getName());
		// register services for this agent on the DF.
		onRegisterServices(description);
		
		try
        {
        	logger.debug("Agent " + getAID() + " registered on DF");
            DFService.register(this, description);
        }
        catch (FIPAException e)
        {
        	logger.debug("Error at register on DF");
            e.printStackTrace();
        }
		
		// register agent on JmxServer
		try {
			resourceInfo = new JmxResourceInfo();
			List<JmxAttributeFieldInfo> attributeFieldInfos = new ArrayList<JmxAttributeFieldInfo>();
			List<JmxAttributeMethodInfo> attributeMethodInfos = new ArrayList<JmxAttributeMethodInfo>();
			List<JmxOperationInfo> operationInfos = new ArrayList<JmxOperationInfo>();
			onCreateMBean(resourceInfo, attributeFieldInfos, attributeMethodInfos, operationInfos);
			MainContainer.jmxServer.register(this, resourceInfo, 
					attributeFieldInfos.toArray(new JmxAttributeFieldInfo[attributeFieldInfos.size()]), 
					attributeMethodInfos.toArray(new JmxAttributeMethodInfo[attributeMethodInfos.size()]), 
					operationInfos.toArray(new JmxOperationInfo[operationInfos.size()]));
		} catch (JMException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void takeDown() {
		super.takeDown();
		try
        {
            DFService.deregister(this);
        }
        catch (FIPAException fe)
        {
            fe.printStackTrace();
        }
	
		MainContainer.jmxServer.unregister(resourceInfo);
	}
	
	
	protected abstract void onRegisterServices(DFAgentDescription description);
	
	protected abstract void onCreateMBean(JmxResourceInfo resourceInfo, List<JmxAttributeFieldInfo> attributeFieldInfos, 
			List<JmxAttributeMethodInfo> attributeMethodInfos, List<JmxOperationInfo> operationInfos);
	

	private ACLMessage createFipaRequest(Predicate content) throws CodecException, OntologyException{
		ACLMessage fipaRequestMessage = new ACLMessage(ACLMessage.REQUEST);
		fipaRequestMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		fipaRequestMessage.setLanguage(codec.getName());
		fipaRequestMessage.setOntology(ontology.getName());
		getContentManager().fillContent(fipaRequestMessage, content);
		return fipaRequestMessage;
	}
	
	/**
	 * Helper for create a FIPA-Request for a agent list.
	 * @param content
	 * @param receivers
	 * @return ACLMessage
	 * @throws CodecException
	 * @throws OntologyException
	 */
	protected ACLMessage createFipaRequest(Predicate content, List<AID> receivers) throws CodecException, OntologyException{
		ACLMessage fipaRequestMessage = createFipaRequest(content);
		for(AID receiver: receivers)
			fipaRequestMessage.addReceiver(receiver);
		return fipaRequestMessage;
	}
	
	/**
	 * Helper for create a FIPA-Request for a Agent.
	 * @param content
	 * @param receiver
	 * @return ACLMessage
	 * @throws CodecException
	 * @throws OntologyException
	 */
	protected ACLMessage createFipaRequest(Predicate content, AID receiver) throws CodecException, OntologyException{
		ACLMessage fipaRequestMessage = createFipaRequest(content);
		fipaRequestMessage.addReceiver(receiver);
		return fipaRequestMessage;
	}
}
