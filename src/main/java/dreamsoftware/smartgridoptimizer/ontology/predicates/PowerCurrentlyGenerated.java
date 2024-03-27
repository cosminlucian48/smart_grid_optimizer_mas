package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerGenerated;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

public class PowerCurrentlyGenerated implements Predicate, IClientVisitable {

	private static final long serialVersionUID = 1L;
	
	private AID aid;
	private PowerGenerated powerGenerated;
	
	public PowerCurrentlyGenerated(){}
	
	public PowerCurrentlyGenerated(AID aid, PowerGenerated powerGenerated) {
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

	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
