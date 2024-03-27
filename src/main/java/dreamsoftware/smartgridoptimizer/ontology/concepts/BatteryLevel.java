package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

public class BatteryLevel implements Concept {

	private static final long serialVersionUID = 1L;
	
	private Double batteryLevelValue;
	
	public BatteryLevel(){}

	public BatteryLevel(Double batteryLevelValue) {
		super();
		this.batteryLevelValue = batteryLevelValue;
	}

	public Double getBatteryLevelValue() {
		return batteryLevelValue;
	}

	public void setBatteryLevelValue(Double batteryLevelValue) {
		this.batteryLevelValue = batteryLevelValue;
	}
}
