package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

import java.io.Serial;

/**
 * The PowerSold class represents a concept containing information about the amount of power sold.
 * It implements the Concept interface.
 */
public class PowerSold implements Concept {

	@Serial
	private static final long serialVersionUID = 1L;

	private Double powerSoldValue;

	/**
	 * Default constructor.
	 */
	public PowerSold() {}

	/**
	 * Constructs a PowerSold object with the specified power sold value.
	 * @param powerSoldValue the amount of power sold.
	 */
	public PowerSold(Double powerSoldValue) {
		super();
		this.powerSoldValue = powerSoldValue;
	}

	/**
	 * Retrieves the amount of power sold.
	 * @return the amount of power sold.
	 */
	public Double getPowerSoldValue() {
		return powerSoldValue;
	}

	/**
	 * Sets the amount of power sold.
	 * @param powerSoldValue the amount of power sold to set.
	 */
	public void setPowerSoldValue(Double powerSoldValue) {
		this.powerSoldValue = powerSoldValue;
	}
}