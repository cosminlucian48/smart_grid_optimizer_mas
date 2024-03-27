package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;

import java.io.Serial;

/**
 * The NotifierLoadConsumption class represents a predicate used to notify about load consumption.
 * It implements the Predicate interface.
 */
public class NotifierLoadConsumption implements Predicate {

	@Serial
	private static final long serialVersionUID = 1L;

	private LoadConsumption loadConsumption;

	/**
	 * Default constructor.
	 */
	public NotifierLoadConsumption() {
	}

	/**
	 * Parameterized constructor to set the load consumption.
	 * @param loadConsumption the load consumption.
	 */
	public NotifierLoadConsumption(LoadConsumption loadConsumption) {
		super();
		this.loadConsumption = loadConsumption;
	}

	/**
	 * Retrieves the load consumption.
	 * @return the load consumption.
	 */
	public LoadConsumption getLoadConsumption() {
		return loadConsumption;
	}

	/**
	 * Sets the load consumption.
	 * @param loadConsumption the load consumption to set.
	 */
	public void setLoadConsumption(LoadConsumption loadConsumption) {
		this.loadConsumption = loadConsumption;
	}
}