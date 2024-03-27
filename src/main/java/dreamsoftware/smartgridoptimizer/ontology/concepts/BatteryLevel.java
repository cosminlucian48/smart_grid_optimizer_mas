package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

import java.io.Serial;

/**
 * The BatteryLevel class represents a concept indicating the battery level.
 * It implements the Concept interface.
 */
public class BatteryLevel implements Concept {

	@Serial
	private static final long serialVersionUID = 1L;

	private Double batteryLevelValue;

	/**
	 * Default constructor.
	 */
	public BatteryLevel(){}

	/**
	 * Parameterized constructor to set the battery level.
	 * @param batteryLevelValue the battery level value to set.
	 */
	public BatteryLevel(Double batteryLevelValue) {
		super();
		this.batteryLevelValue = batteryLevelValue;
	}

	/**
	 * Retrieves the battery level value.
	 * @return the battery level value.
	 */
	public Double getBatteryLevelValue() {
		return batteryLevelValue;
	}

	/**
	 * Sets the battery level value.
	 * @param batteryLevelValue the battery level value to set.
	 */
	public void setBatteryLevelValue(Double batteryLevelValue) {
		this.batteryLevelValue = batteryLevelValue;
	}
}