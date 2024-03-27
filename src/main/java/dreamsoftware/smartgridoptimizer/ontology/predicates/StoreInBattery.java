package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.Surplus;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IBatteryVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IBatteryVisitor;

import java.io.Serial;

/**
 * The StoreInBattery class represents a predicate used to store surplus energy in a battery.
 * It implements the Predicate and IBatteryVisitable interfaces.
 */
public final class StoreInBattery implements Predicate, IBatteryVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Surplus surplus;

	/**
	 * Constructs a new StoreInBattery instance.
	 */
	public StoreInBattery() {}

	/**
	 * Constructs a new StoreInBattery instance with the specified surplus energy.
	 * @param surplus the surplus energy to store.
	 */
	public StoreInBattery(Surplus surplus) {
		this.surplus = surplus;
	}

	/**
	 * Retrieves the surplus energy to be stored.
	 * @return the surplus energy.
	 */
	public Surplus getSurplus() {
		return surplus;
	}

	/**
	 * Sets the surplus energy to be stored.
	 * @param surplus the surplus energy to set.
	 */
	public void setSurplus(Surplus surplus) {
		this.surplus = surplus;
	}

	/**
	 * Accepts a visitor to process the StoreInBattery instance.
	 * @param visitor the battery visitor.
	 * @return the result of the visit.
	 */
	public Predicate accept(IBatteryVisitor visitor) {
		return visitor.visit(this);
	}
}
