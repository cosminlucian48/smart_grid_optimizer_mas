package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.CurrentBudget;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

import java.io.Serial;

/**
 * The NotifierCurrentBudget class represents a predicate used to notify about the current budget.
 * It implements the Predicate and IClientVisitable interfaces.
 */
public final class NotifierCurrentBudget implements Predicate, IClientVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	private CurrentBudget currentBudget;

	/**
	 * Default constructor.
	 */
	public NotifierCurrentBudget() {
	}

	/**
	 * Parameterized constructor to set the current budget.
	 * @param currentBudget the current budget.
	 */
	public NotifierCurrentBudget(CurrentBudget currentBudget) {
		super();
		this.currentBudget = currentBudget;
	}

	/**
	 * Retrieves the current budget.
	 * @return the current budget.
	 */
	public CurrentBudget getCurrentBudget() {
		return currentBudget;
	}

	/**
	 * Sets the current budget.
	 * @param currentBudget the current budget to set.
	 */
	public void setCurrentBudget(CurrentBudget currentBudget) {
		this.currentBudget = currentBudget;
	}

	/**
	 * Accepts a client visitor to process the NotifierCurrentBudget instance.
	 * @param visitor the client visitor.
	 */
	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
