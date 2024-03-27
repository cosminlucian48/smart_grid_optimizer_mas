package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.Surplus;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IMarketVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IMarketVisitor;

public class SellingPower implements Predicate, IMarketVisitable {
	
	private static final long serialVersionUID = 1L;
	
	private Surplus surplus;
	
	public SellingPower(){}

	public SellingPower(Surplus surplus) {
		super();
		this.surplus = surplus;
	}

	public Surplus getSurplus() {
		return surplus;
	}

	public void setSurplus(Surplus surplus) {
		this.surplus = surplus;
	}

	public Predicate accept(IMarketVisitor visitor) {
		return visitor.visit(this);
	}
}
