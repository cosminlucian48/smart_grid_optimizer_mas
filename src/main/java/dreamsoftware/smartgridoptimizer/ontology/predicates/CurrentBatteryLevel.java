package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.BatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

import java.io.Serial;

/**
 * The CurrentBatteryLevel class represents a predicate used to indicate the current battery level.
 * It implements the Predicate and IClientVisitable interfaces.
 */
public class CurrentBatteryLevel implements Predicate, IClientVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	private AID aid;
	private BatteryLevel batteryLevel;

	/**
	 * Constructs a new CurrentBatteryLevel instance.
	 */
	public CurrentBatteryLevel(){}

	/**
	 * Constructs a new CurrentBatteryLevel instance with the specified AID and battery level.
	 * @param aid the AID.
	 * @param batteryLevel the battery level.
	 */
	public CurrentBatteryLevel(AID aid, BatteryLevel batteryLevel) {
		this.aid = aid;
		this.batteryLevel = batteryLevel;
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
	 * Retrieves the battery level.
	 * @return the battery level.
	 */
	public BatteryLevel getBatteryLevel() {
		return batteryLevel;
	}

	/**
	 * Sets the battery level.
	 * @param batteryLevel the battery level to set.
	 */
	public void setBatteryLevel(BatteryLevel batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	/**
	 * Accepts a visitor to process the CurrentBatteryLevel instance.
	 * @param visitor the client visitor.
	 */
	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
