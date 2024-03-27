package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.CurrentPrice;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

import java.io.Serial;

/**
 * The NotifierCurrentPrice class represents a predicate used to notify about the current price.
 * It implements the Predicate and IClientVisitable interfaces.
 */
public final class NotifierCurrentPrice implements Predicate, IClientVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	private CurrentPrice currentPrice;

	/**
	 * Default constructor.
	 */
	public NotifierCurrentPrice() {
	}

	/**
	 * Parameterized constructor to set the current price.
	 * @param currentPrice the current price.
	 */
	public NotifierCurrentPrice(CurrentPrice currentPrice) {
		super();
		this.currentPrice = currentPrice;
	}

	/**
	 * Retrieves the current price.
	 * @return the current price.
	 */
	public CurrentPrice getCurrentPrice() {
		return currentPrice;
	}

	/**
	 * Sets the current price.
	 * @param currentPrice the current price to set.
	 */
	public void setCurrentPrice(CurrentPrice currentPrice) {
		this.currentPrice = currentPrice;
	}

	/**
	 * Accepts a client visitor to process the NotifierCurrentPrice instance.
	 * @param visitor the client visitor.
	 */
	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
