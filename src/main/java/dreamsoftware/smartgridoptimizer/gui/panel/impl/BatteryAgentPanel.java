package dreamsoftware.smartgridoptimizer.gui.panel.impl;

import java.awt.Color;

public class BatteryAgentPanel extends AgentPanel {

	private static final long serialVersionUID = 1L;
	
	private static final Integer BATTERY_WIDTH = 60;
	private static final Integer BATTERY_HEIGHT = 60;

	public BatteryAgentPanel(String label, Double value) {
		super(BATTERY_WIDTH, BATTERY_HEIGHT, label, value);
	}
	

	@Override
	protected Color getColor() {
		int r = (int) Math.min(255, Math.ceil(102 + value));
	    int g = (int) Math.min(255, Math.ceil(102 + value));
	    int b = (int) Math.min(255, Math.ceil(0 + value));
		return new Color(r, g, b);
	}

}
