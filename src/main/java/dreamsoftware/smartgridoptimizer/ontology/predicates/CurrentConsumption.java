package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

import java.io.Serial;

/**
 * The CurrentConsumption class represents a predicate used to indicate the current consumption.
 * It implements the Predicate and IClientVisitable interfaces.
 */
public class CurrentConsumption implements Predicate, IClientVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	private AID aid;
	private LoadConsumption loadConsumption;

	/**
	 * Constructs a new CurrentConsumption instance.
	 */
	public CurrentConsumption(){}

	/**
	 * Constructs a new CurrentConsumption instance with the specified AID and load consumption.
	 * @param aid the AID.
	 * @param loadConsumption the load consumption.
	 */
	public CurrentConsumption(AID aid, LoadConsumption loadConsumption) {
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
	 * Accepts a visitor to process the CurrentConsumption instance.
	 * @param visitor the client visitor.
	 */
	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
