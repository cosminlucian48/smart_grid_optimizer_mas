package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.BatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IControllerVisitor;

public class NotifierBatteryLevel implements Predicate, IVisitable<IControllerVisitor> {

	private static final long serialVersionUID = 1L;
	
	private AID aid;
	private BatteryLevel batteryLevel;
	
	public NotifierBatteryLevel(){}

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

	public void accept(IControllerVisitor visitor) {
		visitor.visit(this);
	}
}
