package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerBought;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

public class NotifierTotalPowerBought implements Predicate, IClientVisitable {
	
	private static final long serialVersionUID = 1L;
	
	private PowerBought powerBought;
	
	public NotifierTotalPowerBought(){}

	public NotifierTotalPowerBought(PowerBought powerBought) {
		super();
		this.powerBought = powerBought;
	}

	public PowerBought getPowerBought() {
		return powerBought;
	}

	public void setPowerBought(PowerBought powerBought) {
		this.powerBought = powerBought;
	}
	
	public void setLoadConsumption(){}

	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
