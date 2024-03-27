package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

import java.io.Serial;

/**
 * The CurrentBudget class represents a concept indicating the current budget.
 * It implements the Concept interface.
 */
public class CurrentBudget implements Concept {

	@Serial
	private static final long serialVersionUID = 1L;

	private Double currentBudgetValue;

	/**
	 * Default constructor.
	 */
	public CurrentBudget(){}

	/**
	 * Parameterized constructor to set the current budget.
	 * @param currentBudgetValue the current budget value to set.
	 */
	public CurrentBudget(Double currentBudgetValue) {
		super();
		this.currentBudgetValue = currentBudgetValue;
	}

	/**
	 * Retrieves the current budget value.
	 * @return the current budget value.
	 */
	public Double getCurrentBudgetValue() {
		return currentBudgetValue;
	}

	/**
	 * Sets the current budget value.
	 * @param currentBudgetValue the current budget value to set.
	 */
	public void setCurrentBudgetValue(Double currentBudgetValue) {
		this.currentBudgetValue = currentBudgetValue;
	}
}
