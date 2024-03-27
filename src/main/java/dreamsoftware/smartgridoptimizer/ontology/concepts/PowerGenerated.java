package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

import java.io.Serial;

/**
 * The PowerGenerated class represents a concept containing information about the amount of power generated.
 * It implements the Concept interface.
 */
public final class PowerGenerated implements Concept {

	@Serial
	private static final long serialVersionUID = 1L;

	private Double powerGeneratedValue;

	/**
	 * Default constructor.
	 */
	public PowerGenerated() {}

	/**
	 * Constructs a PowerGenerated object with the specified power generated value.
	 * @param powerGeneratedValue the amount of power generated.
	 */
	public PowerGenerated(Double powerGeneratedValue) {
		super();
		this.powerGeneratedValue = powerGeneratedValue;
	}

	/**
	 * Retrieves the amount of power generated.
	 * @return the amount of power generated.
	 */
	public Double getPowerGeneratedValue() {
		return powerGeneratedValue;
	}

	/**
	 * Sets the amount of power generated.
	 * @param powerGeneratedValue the amount of power generated to set.
	 */
	public void setPowerGeneratedValue(Double powerGeneratedValue) {
		this.powerGeneratedValue = powerGeneratedValue;
	}
}
