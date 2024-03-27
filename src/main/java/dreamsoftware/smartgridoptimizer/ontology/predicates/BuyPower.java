package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.Surplus;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IMarketVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IMarketVisitor;

import java.io.Serial;

/**
 * The BuyPower class represents a predicate used to indicate buying power in the market.
 * It implements the Predicate and IMarketVisitable interfaces.
 */
public class BuyPower implements Predicate, IMarketVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Surplus surplus;

	/**
	 * Constructs a new BuyPower instance.
	 */
	public BuyPower(){}

	/**
	 * Constructs a new BuyPower instance with the specified surplus.
	 * @param surplus the surplus to set.
	 */
	public BuyPower(Surplus surplus) {
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
	 * Accepts a visitor to process the BuyPower instance.
	 * @param visitor the market visitor.
	 * @return the result of the visit.
	 */
	public Predicate accept(IMarketVisitor visitor) {
		return visitor.visit(this);
	}
}