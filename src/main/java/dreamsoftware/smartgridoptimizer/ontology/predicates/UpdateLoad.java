package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IPowerLoadVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerLoadVisitor;

import java.io.Serial;

/**
 * The UpdateLoad class represents a predicate indicating the update of load consumption.
 * It implements the Predicate interface and is visitable by power load visitors.
 */
public class UpdateLoad implements Predicate, IPowerLoadVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	private LoadConsumption loadConsumption;

	/**
	 * Default constructor.
	 */
	public UpdateLoad() {}

	/**
	 * Parameterized constructor to set the load consumption.
	 * @param loadConsumption the load consumption to set.
	 */
	public UpdateLoad(LoadConsumption loadConsumption) {
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

	/**
	 * Accepts a power load visitor.
	 * @param visitor the visitor to accept.
	 * @return the result of visiting this predicate by the visitor.
	 */
	public Predicate accept(IPowerLoadVisitor visitor) {
		return visitor.visit(this);
	}
}
