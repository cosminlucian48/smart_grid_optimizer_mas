package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

public class CurrentBudget implements Concept {

	private static final long serialVersionUID = 1L;
	
	private Double currentBudgetValue;
	
	public CurrentBudget(){}


	public CurrentBudget(Double currentBudgetValue) {
		super();
		this.currentBudgetValue = currentBudgetValue;
	}


	public Double getCurrentBudgetValue() {
		return currentBudgetValue;
	}


	public void setCurrentBudgetValue(Double currentBudgetValue) {
		this.currentBudgetValue = currentBudgetValue;
	}
	
}
