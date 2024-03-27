package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.Surplus;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IBatteryVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IBatteryVisitor;

public final class StoreInBattery implements Predicate, IBatteryVisitable {

	private static final long serialVersionUID = 1L;
	
	private Surplus surplus;
	
	public StoreInBattery() {}

	public StoreInBattery(Surplus surplus) {
		super();
		this.surplus = surplus;
	}

	public Surplus getSurplus() {
		return surplus;
	}

	public void setSurplus(Surplus surplus) {
		this.surplus = surplus;
	}

	public Predicate accept(IBatteryVisitor visitor) {
		return visitor.visit(this);
	}
}
