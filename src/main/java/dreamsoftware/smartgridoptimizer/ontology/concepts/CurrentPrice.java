package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

public class CurrentPrice implements Concept {

	private static final long serialVersionUID = 1L;
	
	private Double currentPriceValue;
	
	public CurrentPrice(){}


	public CurrentPrice(Double currentPriceValue) {
		super();
		this.currentPriceValue = currentPriceValue;
	}

	public Double getCurrentPriceValue() {
		return currentPriceValue;
	}

	public void setCurrentPriceValue(Double currentPriceValue) {
		this.currentPriceValue = currentPriceValue;
	}
}
