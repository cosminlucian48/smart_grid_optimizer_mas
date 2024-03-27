package dreamsoftware.smartgridoptimizer.gui.panel.impl;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public abstract class AgentPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	protected Integer preferredWidth; 
	protected Integer preferredHeight;
	protected String name;
	protected Double value;
	private JLabel nameLabel;
	private JLabel valueLabel;

	public AgentPanel(Integer preferredWidth, Integer preferredHeight, String name, Double value) {
		super();
		this.name = name;
		this.value = value;
		setLayout(new GridBagLayout());
		setPreferredSize(new Dimension(preferredWidth, preferredHeight));
		
		Color backgroundColor = getColor();
		Color textColor = getContrastColor(backgroundColor);
		
		setBackground(backgroundColor);
		
		GridBagConstraints nameLabelConstraint = new GridBagConstraints();
		nameLabelConstraint.fill = GridBagConstraints.HORIZONTAL;
		nameLabelConstraint.weightx = 0.5;
		nameLabelConstraint.gridx = 1;
		nameLabelConstraint.gridy = 0;
		nameLabel = new JLabel(name, JLabel.CENTER);
		nameLabel.setForeground(textColor);
		add(nameLabel, nameLabelConstraint);
		
		GridBagConstraints valueLabelConstraint = new GridBagConstraints();
		valueLabelConstraint.fill = GridBagConstraints.HORIZONTAL;
		valueLabelConstraint.weightx = 0.5;
		valueLabelConstraint.gridx = 1;
		valueLabelConstraint.gridy = 1;
		valueLabel = new JLabel(new DecimalFormat("###.###").format(value), JLabel.CENTER);
		valueLabel.setForeground(textColor);
		add(valueLabel, valueLabelConstraint);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
	public String getLabel() {
		return name;
	}
	
	public void setLabel(String label) {
		this.name = label;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}
	
	public void updateValue(Double value){
		this.value = value;
		Color backgroundColor = getColor();
		Color textColor = getContrastColor(backgroundColor);
		setBackground(backgroundColor);
		nameLabel.setForeground(textColor);
		valueLabel.setText(new DecimalFormat("###.###").format(value));
		valueLabel.setForeground(textColor);
	}
	
	/**
	 * convert the RGB values into YIQ values. Simply decides to use black or white, based upon the brightness of the original color. 
	 * @param color
	 * @return
	 */
	public Color getContrastColor(Color color) {
		  double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		  return y >= 128 ? Color.black : Color.white;
	}
	
	protected abstract Color getColor();
}
