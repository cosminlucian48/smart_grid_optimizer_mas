package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.BatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

public class CurrentBatteryLevel implements Predicate, IClientVisitable {

	private static final long serialVersionUID = 1L;
	
	private AID aid;
	private BatteryLevel batteryLevel;
	
	public CurrentBatteryLevel(){}

	public CurrentBatteryLevel(AID aid, BatteryLevel batteryLevel) {
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

	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
