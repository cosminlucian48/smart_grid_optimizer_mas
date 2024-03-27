package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerGenerated;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

import java.io.Serial;

/**
 * The PowerCurrentlyGenerated class represents a predicate indicating the power currently generated.
 * It implements the Predicate interface and is visitable by client visitors.
 */
public class PowerCurrentlyGenerated implements Predicate, IClientVisitable {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private AID aid;
	private PowerGenerated powerGenerated;

	/**
	 * Default constructor.
	 */
	public PowerCurrentlyGenerated(){}

	/**
	 * Parameterized constructor to set the AID and power generated.
	 * @param aid the AID.
	 * @param powerGenerated the power generated.
	 */
	public PowerCurrentlyGenerated(AID aid, PowerGenerated powerGenerated) {
		super();
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
	 * Accepts a client visitor.
	 * @param visitor the visitor to accept.
	 */
	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
