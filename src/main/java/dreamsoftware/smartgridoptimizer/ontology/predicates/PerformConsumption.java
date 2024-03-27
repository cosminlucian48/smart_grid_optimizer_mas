package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IControllerVisitor;

import java.io.Serial;

/**
 * The PerformConsumption class represents a predicate used to perform consumption.
 * It implements the Predicate interface and is visitable by controller visitors.
 */
public final class PerformConsumption implements Predicate, IVisitable<IControllerVisitor> {

	@Serial
	private static final long serialVersionUID = 1L;
	
	private AID aid;
	private LoadConsumption loadConsumption;

	/**
	 * Default constructor.
	 */
	public PerformConsumption(){}

	/**
	 * Parameterized constructor to set the AID and load consumption.
	 * @param aid the AID.
	 * @param loadConsumption the load consumption.
	 */
	public PerformConsumption(AID aid, LoadConsumption loadConsumption) {
		super();
		this.aid = aid;
		this.loadConsumption = loadConsumption;
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
	 * Retrieves the load consumption.
	 * @return the load consumption.
	 */
	public LoadConsumption getLoadConsumption() {
		return loadConsumption;
	}

	/**
	 * Sets the load consumption.
	 * @param loadConsumption the load consumption to set.
	 */
	public void setLoadConsumption(LoadConsumption loadConsumption) {
		this.loadConsumption = loadConsumption;
	}

	/**
	 * Accepts a controller visitor.
	 * @param visitor the visitor to accept.
	 */
	public void accept(IControllerVisitor visitor) {
		visitor.visit(this);
	}
}
