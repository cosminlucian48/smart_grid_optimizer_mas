package dreamsoftware.smartgridoptimizer;

import jade.core.Runtime;
import jade.util.leap.Properties;

import java.lang.management.ManagementFactory;
import javax.management.JMException;
import com.j256.simplejmx.server.JmxServer;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
import dreamsoftware.smartgridoptimizer.agents.impl.ControllerAgent;
import dreamsoftware.smartgridoptimizer.agents.impl.ClientAgent;
import dreamsoftware.smartgridoptimizer.agents.impl.MarketAgent;
import dreamsoftware.smartgridoptimizer.agents.impl.ReportAgent;

/**
 * The MainContainer class is responsible for creating and managing the main container
 * and client container of the MAS (Multi-Agent System) application.
 */
public final class MainContainer {

	/** The JmxServer instance for JMX monitoring and management. */
	public static JmxServer jmxServer = new JmxServer(ManagementFactory.getPlatformMBeanServer());

	/**
	 * The main method initializes the JmxServer, creates the main and client containers, and registers a shutdown hook.
	 * @param args the command line arguments.
	 */
	public static void main(String[] args) {
		try {
			jmxServer.start();		
			createMainContainer();
			createClientContainer();
			java.lang.Runtime.getRuntime().addShutdownHook(new Thread(() -> jmxServer.stop()));
		} catch (ControllerException | JMException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates and starts the client container.
	 * @throws ControllerException if an error occurs during agent container creation.
	 */
	private static void createClientContainer() throws ControllerException {
		Runtime rt = Runtime.instance();
		ProfileImpl pc = new ProfileImpl(false);
		pc.setParameter(ProfileImpl.MAIN_HOST, "localhost");
		AgentContainer container = rt.createAgentContainer(pc);
		container.start();
		AgentController agentController = container.createNewAgent(ClientAgent.AGENT_NAME, ClientAgent.class.getName(), null);
		agentController.start();
	}

	/**
	 * Creates and starts the main container with specified agents.
	 * @throws ControllerException if an error occurs during main container creation.
	 */
	private static void createMainContainer() throws ControllerException{

		Properties containerProps = new jade.util.leap.Properties();
		containerProps.setProperty(Profile.GUI, "true");
		containerProps.setProperty(Profile.AGENTS,
				ControllerAgent.AGENT_NAME + ":dreamsoftware.smartgridoptimizer.agents.impl.ControllerAgent;" +
						"home1:dreamsoftware.smartgridoptimizer.agents.impl.PowerLoadAgent;" +
						"home2:dreamsoftware.smartgridoptimizer.agents.impl.PowerLoadAgent;" +
						"home3:dreamsoftware.smartgridoptimizer.agents.impl.PowerLoadAgent;" +
						"office1:dreamsoftware.smartgridoptimizer.agents.impl.PowerLoadAgent;" +
						"office2:dreamsoftware.smartgridoptimizer.agents.impl.PowerLoadAgent;" +
						"office3:dreamsoftware.smartgridoptimizer.agents.impl.PowerLoadAgent;" +
						"pv:dreamsoftware.smartgridoptimizer.agents.impl.PowerGenerateAgent;" +
						"battery:dreamsoftware.smartgridoptimizer.agents.impl.BatteryAgent;" +
						MarketAgent.AGENT_NAME + ":dreamsoftware.smartgridoptimizer.agents.impl.MarketAgent;" +
						ReportAgent.AGENT_NAME + ":dreamsoftware.smartgridoptimizer.agents.impl.ReportAgent;");
		Profile containerProfile = new ProfileImpl(containerProps);
		Runtime.instance().setCloseVM(false);
		Runtime.instance().createMainContainer(containerProfile);
	}
}
