package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;
import java.io.Serial;

/**
 * The Surplus class represents a concept containing information about surplus.
 * It implements the Concept interface.
 */
public final class Surplus implements Concept {

	@Serial
	private static final long serialVersionUID = 1L;

	private Double surplusValue;

	/**
	 * Default constructor.
	 */
	public Surplus() {}

	/**
	 * Constructs a Surplus object with the specified surplus value.
	 * @param surplusValue the surplus value to set.
	 */
	public Surplus(Double surplusValue) {
		super();
		this.surplusValue = surplusValue;
	}

	/**
	 * Retrieves the surplus value.
	 * @return the surplus value.
	 */
	public Double getSurplusValue() {
		return surplusValue;
	}

	/**
	 * Sets the surplus value.
	 * @param surplusValue the surplus value to set.
	 */
	public void setSurplusValue(Double surplusValue) {
		this.surplusValue = surplusValue;
	}
}
