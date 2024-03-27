package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

import java.io.Serial;

/**
 * The LoadConsumption class represents a concept indicating the load consumption.
 * It implements the Concept interface.
 */
public final class LoadConsumption implements Concept {

	@Serial
	private static final long serialVersionUID = 1L;

	private Double value;

	/**
	 * Default constructor.
	 */
	public LoadConsumption() {}

	/**
	 * Parameterized constructor to set the load consumption value.
	 * @param value the load consumption value to set.
	 */
	public LoadConsumption(Double value) {
		super();
		this.value = value;
	}

	/**
	 * Retrieves the load consumption value.
	 * @return the load consumption value.
	 */
	public Double getLoadConsumptionValue() {
		return value;
	}

	/**
	 * Sets the load consumption value.
	 * @param value the load consumption value to set.
	 */
	public void setLoadConsumptionValue(Double value) {
		this.value = value;
	}
}
