package dreamsoftware.smartgridoptimizer.gui.panel.impl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import dreamsoftware.smartgridoptimizer.gui.panel.ISmartGridStatusPanel;
import dreamsoftware.smartgridoptimizer.gui.panel.ModelValuesEnum;

public final class SmartGridStatusPanel extends JPanel implements ISmartGridStatusPanel {

	private static final long serialVersionUID = 1L;
	
	private Map<ModelValuesEnum, JLabel> modelLabelValues = new HashMap<ModelValuesEnum, JLabel>();
	
	public SmartGridStatusPanel() {
		super();
		this.setPreferredSize(new Dimension(400,200));
		this.setLayout(new GridLayout(0,3)); // top to bottom
		for(ModelValuesEnum modelValue: ModelValuesEnum.values()) {
			JPanel controlPanel = new JPanel();
		    controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
			JLabel label = new JLabel(modelValue.name(), JLabel.CENTER);
			label.setOpaque(true);
			label.setBackground(Color.YELLOW);
			label.setAlignmentX(Component.CENTER_ALIGNMENT);
			JLabel value = new JLabel("0", JLabel.CENTER);
			value.setAlignmentX(Component.CENTER_ALIGNMENT);
			controlPanel.add(label);
			controlPanel.add(value);
			modelLabelValues.put(modelValue, value);
			this.add(controlPanel);
		}
	}

	public void setValue(ModelValuesEnum modelValue, String value) {
		if(modelLabelValues.containsKey(modelValue)) {
			JLabel modelValueLabel = modelLabelValues.get(modelValue);
			modelValueLabel.setText(value);
		}
	}
	
}
