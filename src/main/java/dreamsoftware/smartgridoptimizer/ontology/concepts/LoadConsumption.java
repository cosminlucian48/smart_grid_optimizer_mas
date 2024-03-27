package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

public final class LoadConsumption implements Concept {

	private static final long serialVersionUID = 1L;
	
	private Double value;
	
	public LoadConsumption() {}

	public LoadConsumption(Double value) {
		super();
		this.value = value;
	}

	public Double getLoadConsumptionValue() {
		return value;
	}

	public void setLoadConsumptionValue(Double value) {
		this.value = value;
	}
}
