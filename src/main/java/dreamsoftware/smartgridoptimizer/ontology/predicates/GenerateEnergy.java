package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerGenerated;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IControllerVisitor;

public final class GenerateEnergy implements Predicate, IVisitable<IControllerVisitor> {

	private static final long serialVersionUID = 1L;
	
	private AID aid;
	private PowerGenerated powerGenerated;
	
	public GenerateEnergy() {}

	public GenerateEnergy(AID aid, PowerGenerated powerGenerated) {
		super();
		this.aid = aid;
		this.powerGenerated = powerGenerated;
	}

	public AID getAid() {
		return aid;
	}

	public void setAid(AID aid) {
		this.aid = aid;
	}

	public PowerGenerated getPowerGenerated() {
		return powerGenerated;
	}

	public void setPowerGenerated(PowerGenerated powerGenerated) {
		this.powerGenerated = powerGenerated;
	}

	public void accept(IControllerVisitor visitor) {
		visitor.visit(this);
	}
}
