package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerSold;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

public class NotifierTotalPowerSold implements Predicate, IClientVisitable {
	
	private static final long serialVersionUID = 1L;
	
	private PowerSold powerSold;
	
	public NotifierTotalPowerSold(){}

	public NotifierTotalPowerSold(PowerSold powerSold) {
		super();
		this.powerSold = powerSold;
	}

	public PowerSold getPowerSold() {
		return powerSold;
	}

	public void setPowerSold(PowerSold powerSold) {
		this.powerSold = powerSold;
	}

	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
