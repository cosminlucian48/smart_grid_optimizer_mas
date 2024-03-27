package dreamsoftware.smartgridoptimizer.agents;

import java.io.Serial;
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

/**
 * The ISmartGridAgent class represents an abstract agent responsible for managing services and JMX registration.
 * It extends the Jade Agent class.
 */
public abstract class ISmartGridAgent extends Agent implements ISmartGridVocabulary {
	
	private final Logger logger = LoggerFactory.getLogger(PowerLoadAgent.class);

	@Serial
	private static final long serialVersionUID = 1L;
	
	protected Codec codec = new SLCodec();
	protected Ontology ontology = SmartGridOntology.getInstance();
	protected MessageTemplate fipaRequestTemplate;
	private JmxResourceInfo resourceInfo;

	/**
	 * Initializes the ISmartGridAgent by setting up content manager, language, ontology, and registering services.
	 */
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
			List<JmxAttributeFieldInfo> attributeFieldInfoList = new ArrayList<>();
			List<JmxAttributeMethodInfo> attributeMethodInfoList = new ArrayList<>();
			List<JmxOperationInfo> operationInfoList = new ArrayList<>();
			onCreateMBean(resourceInfo, attributeFieldInfoList, attributeMethodInfoList, operationInfoList);
			MainContainer.jmxServer.register(this, resourceInfo, 
					attributeFieldInfoList.toArray(new JmxAttributeFieldInfo[attributeFieldInfoList.size()]),
					attributeMethodInfoList.toArray(new JmxAttributeMethodInfo[attributeMethodInfoList.size()]),
					operationInfoList.toArray(new JmxOperationInfo[operationInfoList.size()]));
		} catch (JMException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Cleans up resources when the agent is taken down.
	 */
	@Override
	protected void takeDown() {
		super.takeDown();
		try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
		MainContainer.jmxServer.unregister(resourceInfo);
	}

	/**
	 * Registers services provided by the ISmartGridAgent.
	 * @param description the agent's description.
	 */
	protected abstract void onRegisterServices(DFAgentDescription description);

	/**
	 * Sets up JMX MBean attributes for the ISmartGridAgent.
	 * @param resourceInfo the resource information.
	 * @param attributeFieldInfoList the list of attribute field information.
	 * @param attributeMethodInfoList the list of attribute method information.
	 * @param operationInfoList the list of operation information.
	 */
	protected abstract void onCreateMBean(JmxResourceInfo resourceInfo, List<JmxAttributeFieldInfo> attributeFieldInfoList,
			List<JmxAttributeMethodInfo> attributeMethodInfoList, List<JmxOperationInfo> operationInfoList);

	/**
	 * Creates a FIPA request message.
	 * @param content the content of the message.
	 * @return the FIPA request message.
	 * @throws CodecException if there's a problem with the codec.
	 * @throws OntologyException if there's a problem with the ontology.
	 */
	private ACLMessage createFipaRequest(Predicate content) throws CodecException, OntologyException{
		ACLMessage fipaRequestMessage = new ACLMessage(ACLMessage.REQUEST);
		fipaRequestMessage.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
		fipaRequestMessage.setLanguage(codec.getName());
		fipaRequestMessage.setOntology(ontology.getName());
		getContentManager().fillContent(fipaRequestMessage, content);
		return fipaRequestMessage;
	}

	/**
	 * Helper method to create a FIPA request for a list of agents.
	 * @param content the content of the message.
	 * @param receivers the list of receivers.
	 * @return the FIPA request message.
	 * @throws CodecException if there's a problem with the codec.
	 * @throws OntologyException if there's a problem with the ontology.
	 */
	protected ACLMessage createFipaRequest(Predicate content, List<AID> receivers) throws CodecException, OntologyException{
		ACLMessage fipaRequestMessage = createFipaRequest(content);
		for(AID receiver: receivers)
			fipaRequestMessage.addReceiver(receiver);
		return fipaRequestMessage;
	}

	/**
	 * Helper method to create a FIPA request for a single agent.
	 * @param content the content of the message.
	 * @param receiver the receiver.
	 * @return the FIPA request message.
	 * @throws CodecException if there's a problem with the codec.
	 * @throws OntologyException if there's a problem with the ontology.
	 */
	protected ACLMessage createFipaRequest(Predicate content, AID receiver) throws CodecException, OntologyException{
		ACLMessage fipaRequestMessage = createFipaRequest(content);
		fipaRequestMessage.addReceiver(receiver);
		return fipaRequestMessage;
	}
}
