package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.CurrentPrice;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

public final class NotifierCurrentPrice implements Predicate, IClientVisitable {

	private static final long serialVersionUID = 1L;
	
	private CurrentPrice currentPrice;
	
	public NotifierCurrentPrice() {}

	public NotifierCurrentPrice(CurrentPrice currentPrice) {
		super();
		this.currentPrice = currentPrice;
	}

	public CurrentPrice getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(CurrentPrice currentPrice) {
		this.currentPrice = currentPrice;
	}

    public void accept(IClientVisitor visitor) {
        visitor.visit(this);
    }
}
