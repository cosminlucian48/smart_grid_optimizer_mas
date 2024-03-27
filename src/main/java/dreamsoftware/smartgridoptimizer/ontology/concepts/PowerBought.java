package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;
import java.io.Serial;

/**
 * The PowerBought class represents a concept containing information about the amount of power bought.
 * It implements the Concept interface.
 */
public class PowerBought implements Concept {

	@Serial
	private static final long serialVersionUID = 1L;

	private Double powerBoughtValue;

	/**
	 * Default constructor.
	 */
	public PowerBought() {}

	/**
	 * Constructs a PowerBought object with the specified power bought value.
	 * @param powerBoughtValue the amount of power bought.
	 */
	public PowerBought(Double powerBoughtValue) {
		super();
		this.powerBoughtValue = powerBoughtValue;
	}

	/**
	 * Retrieves the amount of power bought.
	 * @return the amount of power bought.
	 */
	public Double getPowerBoughtValue() {
		return powerBoughtValue;
	}

	/**
	 * Sets the amount of power bought.
	 * @param powerBoughtValue the amount of power bought to set.
	 */
	public void setPowerBoughtValue(Double powerBoughtValue) {
		this.powerBoughtValue = powerBoughtValue;
	}
}
