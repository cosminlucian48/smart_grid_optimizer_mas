package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerBought;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

import java.io.Serial;

/**
 * The NotifierTotalPowerBought class represents a predicate used to notify about total power bought.
 * It implements the Predicate interface and is visitable by client visitors.
 */
public class NotifierTotalPowerBought implements Predicate, IClientVisitable {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	private PowerBought powerBought;

	/**
	 * Default constructor.
	 */
	public NotifierTotalPowerBought(){}

	/**
	 * Parameterized constructor to set the power bought.
	 * @param powerBought the power bought.
	 */
	public NotifierTotalPowerBought(PowerBought powerBought) {
		super();
		this.powerBought = powerBought;
	}

	/**
	 * Retrieves the power bought.
	 * @return the power bought.
	 */
	public PowerBought getPowerBought() {
		return powerBought;
	}

	/**
	 * Sets the power bought.
	 * @param powerBought the power bought to set.
	 */
	public void setPowerBought(PowerBought powerBought) {
		this.powerBought = powerBought;
	}

	/**
	 * Accepts a client visitor.
	 * @param visitor the visitor to accept.
	 */
	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
