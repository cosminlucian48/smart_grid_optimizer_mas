package dreamsoftware.smartgridoptimizer.gui.panel.impl;

import java.awt.Component;
import java.awt.Font;
import java.io.Serial;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public final class AdjustSmartGridModelPanel extends JPanel {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private final static Integer LOAD_FACTOR_MIN = 0;
	private final static Integer LOAD_FACTOR_MAX = 100;
	private final static Integer LOAD_FACTOR_INIT = 61;
	private final static Integer GEN_FACTOR_MIN = 0;
	private final static Integer GEN_FACTOR_MAX = 100;
	private final static Integer GEN_FACTOR_INIT = 19;
	private final static Integer CELL_MAX_BAT_LEVEL_MIN = 0;
	private final static Integer CELL_MAX_BAT_LEVEL_MAX = 1000;
	private final static Integer CELL_MAX_BAT_LEVEL_INIT = 321;

	public AdjustSmartGridModelPanel() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// load factor slider
        JLabel loadFactorSliderLabel = new JLabel("Load Factor", JLabel.CENTER);
        loadFactorSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JSlider loadFactorSlider = new JSlider(JSlider.HORIZONTAL, LOAD_FACTOR_MIN, LOAD_FACTOR_MAX, LOAD_FACTOR_INIT);
		loadFactorSlider.setPaintTicks(true);
		loadFactorSlider.setPaintLabels(true);
		Font commonFont = new Font("Serif", Font.ITALIC, 15);
		loadFactorSlider.setFont(commonFont);
		this.add(loadFactorSliderLabel);
		this.add(loadFactorSlider);
		// generation factor slider
		JLabel genFactorSliderLabel = new JLabel("Generation Factor", JLabel.CENTER);
		genFactorSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JSlider genFactorSlider = new JSlider(JSlider.HORIZONTAL, GEN_FACTOR_MIN, GEN_FACTOR_MAX, GEN_FACTOR_INIT);
		genFactorSlider.setPaintTicks(true);
		genFactorSlider.setPaintLabels(true);
		genFactorSlider.setFont(commonFont);
		this.add(genFactorSliderLabel);
		this.add(genFactorSlider);
		// cellmaxbatlevel slider
		JLabel cellmaxbatlevelSliderLabel = new JLabel("Cell Max Battery Level", JLabel.CENTER);
		cellmaxbatlevelSliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JSlider cellmaxbatlevelSlider = new JSlider(JSlider.HORIZONTAL, CELL_MAX_BAT_LEVEL_MIN, CELL_MAX_BAT_LEVEL_MAX, CELL_MAX_BAT_LEVEL_INIT);
		cellmaxbatlevelSlider.setPaintTicks(true);
		cellmaxbatlevelSlider.setPaintLabels(true);
		cellmaxbatlevelSlider.setFont(commonFont);
		this.add(cellmaxbatlevelSliderLabel);
		this.add(cellmaxbatlevelSlider);
	}
}
