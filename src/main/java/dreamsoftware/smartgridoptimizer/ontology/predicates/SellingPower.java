package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.Surplus;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IMarketVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IMarketVisitor;

import java.io.Serial;

/**
 * The SellingPower class represents a predicate indicating the intention to sell surplus power.
 * It implements the Predicate interface and is visitable by market visitors.
 */
public class SellingPower implements Predicate, IMarketVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Surplus surplus;

	/**
	 * Default constructor.
	 */
	public SellingPower() {}

	/**
	 * Parameterized constructor to set the surplus.
	 * @param surplus the surplus to set.
	 */
	public SellingPower(Surplus surplus) {
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
	 * Accepts a market visitor.
	 * @param visitor the visitor to accept.
	 * @return the result of visiting this predicate by the visitor.
	 */
	public Predicate accept(IMarketVisitor visitor) {
		return visitor.visit(this);
	}
}