package dreamsoftware.smartgridoptimizer.gui.panel.impl;

import java.awt.Color;

public class ConsumePowerAgentPanel extends AgentPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static final Integer COMPONENT_WIDTH = 100;
	private static final Integer COMPONENT_HEIGHT = 130;

	public ConsumePowerAgentPanel(String label, Double value) {
		super(COMPONENT_WIDTH, COMPONENT_HEIGHT, label, value);
	}

	@Override
	protected Color getColor() {
		int r = (int) Math.min(255, Math.ceil(0 + value));
	    int g = (int) Math.min(255, Math.ceil(51 + value));
	    int b = (int) Math.min(255, Math.ceil(102 + value));
		return new Color(r, g, b);
	}

}
