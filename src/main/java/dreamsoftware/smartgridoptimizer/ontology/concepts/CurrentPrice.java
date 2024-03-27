package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;
import java.io.Serial;

/**
 * The CurrentPrice class represents a concept indicating the current price.
 * It implements the Concept interface.
 */
public class CurrentPrice implements Concept {

	@Serial
	private static final long serialVersionUID = 1L;

	private Double currentPriceValue;

	/**
	 * Default constructor.
	 */
	public CurrentPrice(){}

	/**
	 * Parameterized constructor to set the current price.
	 * @param currentPriceValue the current price value to set.
	 */
	public CurrentPrice(Double currentPriceValue) {
		super();
		this.currentPriceValue = currentPriceValue;
	}

	/**
	 * Retrieves the current price value.
	 * @return the current price value.
	 */
	public Double getCurrentPriceValue() {
		return currentPriceValue;
	}

	/**
	 * Sets the current price value.
	 * @param currentPriceValue the current price value to set.
	 */
	public void setCurrentPriceValue(Double currentPriceValue) {
		this.currentPriceValue = currentPriceValue;
	}
}
