package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerSold;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

import java.io.Serial;

/**
 * The NotifierTotalPowerSold class represents a predicate used to notify about total power sold.
 * It implements the Predicate interface and is visitable by client visitors.
 */
public class NotifierTotalPowerSold implements Predicate, IClientVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	private PowerSold powerSold;

	/**
	 * Default constructor.
	 */
	public NotifierTotalPowerSold() {
	}

	/**
	 * Parameterized constructor to set the power sold.
	 * @param powerSold the power sold.
	 */
	public NotifierTotalPowerSold(PowerSold powerSold) {
		super();
		this.powerSold = powerSold;
	}

	/**
	 * Retrieves the power sold.
	 * @return the power sold.
	 */
	public PowerSold getPowerSold() {
		return powerSold;
	}

	/**
	 * Sets the power sold.
	 * @param powerSold the power sold to set.
	 */
	public void setPowerSold(PowerSold powerSold) {
		this.powerSold = powerSold;
	}

	/**
	 * Accepts a client visitor.
	 * @param visitor the visitor to accept.
	 */
	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
