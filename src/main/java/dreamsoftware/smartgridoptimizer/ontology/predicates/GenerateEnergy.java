package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerGenerated;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IControllerVisitor;

import java.io.Serial;

/**
 * The GenerateEnergy class represents a predicate used to indicate the generation of energy.
 * It implements the Predicate and IVisitable interfaces.
 */
public final class GenerateEnergy implements Predicate, IVisitable<IControllerVisitor> {

	@Serial
	private static final long serialVersionUID = 1L;

	private AID aid;
	private PowerGenerated powerGenerated;

	/**
	 * Constructs a new GenerateEnergy instance.
	 */
	public GenerateEnergy() {}

	/**
	 * Constructs a new GenerateEnergy instance with the specified AID and power generated.
	 * @param aid the AID.
	 * @param powerGenerated the power generated.
	 */
	public GenerateEnergy(AID aid, PowerGenerated powerGenerated) {
		this.aid = aid;
		this.powerGenerated = powerGenerated;
	}

	/**
	 * Retrieves the AID.
	 * @return the AID.
	 */
	public AID getAid() {
		return aid;
	}

	/**
	 * Sets the AID.
	 * @param aid the AID to set.
	 */
	public void setAid(AID aid) {
		this.aid = aid;
	}

	/**
	 * Retrieves the power generated.
	 * @return the power generated.
	 */
	public PowerGenerated getPowerGenerated() {
		return powerGenerated;
	}

	/**
	 * Sets the power generated.
	 * @param powerGenerated the power generated to set.
	 */
	public void setPowerGenerated(PowerGenerated powerGenerated) {
		this.powerGenerated = powerGenerated;
	}

	/**
	 * Accepts a visitor to process the GenerateEnergy instance.
	 * @param visitor the controller visitor.
	 */
	public void accept(IControllerVisitor visitor) {
		visitor.visit(this);
	}
}
