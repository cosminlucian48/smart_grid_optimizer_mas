package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

public class PowerSold implements Concept {

	private static final long serialVersionUID = 1L;
	
	private Double powerSoldValue;
	
	public PowerSold(){}

	public PowerSold(Double powerSoldValue) {
		super();
		this.powerSoldValue = powerSoldValue;
	}

	public Double getPowerSoldValue() {
		return powerSoldValue;
	}

	public void setPowerSoldValue(Double powerSoldValue) {
		this.powerSoldValue = powerSoldValue;
	}
}
