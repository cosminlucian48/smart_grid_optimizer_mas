package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.Surplus;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IBatteryVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IBatteryVisitor;

import java.io.Serial;

/**
 * The RetrieveDemand class represents a predicate indicating the demand to retrieve surplus from the battery.
 * It implements the Predicate interface and is visitable by battery visitors.
 */
public class RetrieveDemand implements Predicate, IBatteryVisitable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private Surplus surplus;

	/**
	 * Default constructor.
	 */
	public RetrieveDemand() {
	}

	/**
	 * Parameterized constructor to set the surplus.
	 * @param surplus the surplus to set.
	 */
	public RetrieveDemand(Surplus surplus) {
		super();
		this.surplus = surplus;
	}

	/**
	 * Retrieves the surplus.
	 * @return the surplus.
	 */
	public Surplus getSurplus() {
		return surplus;
	}

	/**
	 * Sets the surplus.
	 * @param surplus the surplus to set.
	 */
	public void setSurplus(Surplus surplus) {
		this.surplus = surplus;
	}

	/**
	 * Accepts a battery visitor.
	 * @param visitor the visitor to accept.
	 * @return the result of visiting this predicate by the visitor.
	 */
	public Predicate accept(IBatteryVisitor visitor) {
		return visitor.visit(this);
	}
}
