package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

public final class PowerGenerated implements Concept {

	private static final long serialVersionUID = 1L;
	
	private Double powerGeneratedValue;
	
	public PowerGenerated(){}

	public PowerGenerated(Double powerGeneratedValue) {
		super();
		this.powerGeneratedValue = powerGeneratedValue;
	}

	public Double getPowerGeneratedValue() {
		return powerGeneratedValue;
	}

	public void setPowerGeneratedValue(Double powerGeneratedValue) {
		this.powerGeneratedValue = powerGeneratedValue;
	}
}
