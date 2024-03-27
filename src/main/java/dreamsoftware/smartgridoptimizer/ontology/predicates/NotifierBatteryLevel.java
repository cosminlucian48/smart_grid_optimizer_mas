package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.BatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IControllerVisitor;

import java.io.Serial;

/**
 * The NotifierBatteryLevel class represents a predicate used to notify about battery levels.
 * It implements the Predicate and IVisitable<IControllerVisitor> interfaces.
 */
public class NotifierBatteryLevel implements Predicate, IVisitable<IControllerVisitor> {

	@Serial
	private static final long serialVersionUID = 1L;

	private AID aid;
	private BatteryLevel batteryLevel;

	/**
	 * Default constructor.
	 */
	public NotifierBatteryLevel() {
	}

	/**
	 * Parameterized constructor to set the values of the notifier battery level.
	 * @param aid the AID of the agent.
	 * @param batteryLevel the battery level.
	 */
	public NotifierBatteryLevel(AID aid, BatteryLevel batteryLevel) {
		super();
		this.aid = aid;
		this.batteryLevel = batteryLevel;
	}


	public AID getAid() {
		return aid;
	}

	public void setAid(AID aid) {
		this.aid = aid;
	}

	public BatteryLevel getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(BatteryLevel batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	/**
	 * Accepts a controller visitor to process the NotifierBatteryLevel instance.
	 * @param visitor the controller visitor.
	 */
	public void accept(IControllerVisitor visitor) {
		visitor.visit(this);
	}
}
