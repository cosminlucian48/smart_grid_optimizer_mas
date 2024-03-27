package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.CurrentBudget;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

public final class NotifierCurrentBudget implements Predicate, IClientVisitable {

	private static final long serialVersionUID = 1L;
	
	private CurrentBudget currentBudget;
	
	public NotifierCurrentBudget() {}

    public NotifierCurrentBudget(CurrentBudget currentBudget) {
		super();
		this.currentBudget = currentBudget;
	}

	public CurrentBudget getCurrentBudget() {
		return currentBudget;
	}

	public void setCurrentBudget(CurrentBudget currentBudget) {
		this.currentBudget = currentBudget;
	}

	public void accept(IClientVisitor visitor) {
        visitor.visit(this);
    }
}
