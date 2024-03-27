package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

public class PowerBought implements Concept {

	private static final long serialVersionUID = 1L;
	
	private Double powerBoughtValue;
	
	public PowerBought(){}

	public PowerBought(Double powerBoughtValue) {
		super();
		this.powerBoughtValue = powerBoughtValue;
	}

	public Double getPowerBoughtValue() {
		return powerBoughtValue;
	}

	public void setPowerBoughtValue(Double powerBoughtValue) {
		this.powerBoughtValue = powerBoughtValue;
	}
	
}
