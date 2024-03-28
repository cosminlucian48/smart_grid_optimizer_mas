package dreamsoftware.smartgridoptimizer.gui.panel.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SimulationViewerPanel extends JPanel {

	private final Logger logger = LoggerFactory.getLogger(SimulationViewerPanel.class);
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private final JPanel batteryAgentsPanel;
	private final JPanel consumptionAgentsPanelNorth;
	private final JPanel consumptionAgentsPanelEast;
	
	private final JPanel generatePowerAgentsPanel;
	
	private final Map<String, AgentPanel> agentPanels = new HashMap<>();

	public SimulationViewerPanel() {
        setLayout(new BorderLayout());
        consumptionAgentsPanelNorth = new JPanel(new FlowLayout(FlowLayout.LEFT));
        consumptionAgentsPanelNorth.setBackground(Color.gray);
        add(consumptionAgentsPanelNorth, BorderLayout.NORTH);
        consumptionAgentsPanelEast = new JPanel();
        consumptionAgentsPanelEast.setLayout(new BoxLayout(consumptionAgentsPanelEast, BoxLayout.Y_AXIS));
        consumptionAgentsPanelEast.setBackground(Color.gray);
        consumptionAgentsPanelEast.setBorder(new EmptyBorder(5,5,5,5));
        add(consumptionAgentsPanelEast, BorderLayout.EAST);
        batteryAgentsPanel = new JPanel();
        batteryAgentsPanel.setBackground(Color.gray);
        add(batteryAgentsPanel, BorderLayout.SOUTH);
        generatePowerAgentsPanel = new JPanel();
        generatePowerAgentsPanel.setBackground(Color.gray);
        add(generatePowerAgentsPanel, BorderLayout.CENTER);
        
        setPreferredSize( new Dimension(650, 700) );
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
	}

	public void addBatteryAgentComponent(String name, Double value) {
		BatteryAgentPanel batteryComp = new BatteryAgentPanel(name, value);
		agentPanels.put(name, batteryComp);
		batteryAgentsPanel.add(batteryComp);
		batteryAgentsPanel.invalidate();
	}
	
	public void addGeneratePowerAgentComponent(String name, Double value) {
		GeneratePowerAgentPanel generatePowerPanel = new GeneratePowerAgentPanel(name, value);
		agentPanels.put(name, generatePowerPanel);
		generatePowerAgentsPanel.add(generatePowerPanel);
		generatePowerAgentsPanel.invalidate();
	}
	
	public void addConsumePowerAgentComponent(String name, Double value) {
		ConsumePowerAgentPanel consumePowerAgentPanel = new ConsumePowerAgentPanel(name, value);
		agentPanels.put(name, consumePowerAgentPanel);
		if(consumptionAgentsPanelNorth.getComponentCount() <= consumptionAgentsPanelEast.getComponentCount()) {
			consumptionAgentsPanelNorth.add(consumePowerAgentPanel);
			consumptionAgentsPanelNorth.invalidate();
		} else {
			consumptionAgentsPanelEast.add(consumePowerAgentPanel);
			consumptionAgentsPanelEast.invalidate();
		}
	}
	
	public void removeAgent(String name) {
		if(agentPanels.containsKey(name)){
			AgentPanel agentPanel = agentPanels.get(name);
			Container rootPanel = agentPanel.getParent();
			rootPanel.remove(agentPanel);
			agentPanels.remove(name);
			rootPanel.invalidate();
			rootPanel.repaint();
		}
	}
	
	public void setAgentValue(String name, Double value) {
		if(agentPanels.containsKey(name)){ 
			logger.info("Update Agent -> " + name + " with value -> "+ value);
			AgentPanel agentPanel = agentPanels.get(name);
			agentPanel.updateValue(value);
		}
	}
}
