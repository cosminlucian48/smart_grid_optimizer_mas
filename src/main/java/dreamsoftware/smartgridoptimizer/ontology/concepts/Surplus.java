package dreamsoftware.smartgridoptimizer.ontology.concepts;

import jade.content.Concept;

public final class Surplus implements Concept {

	private static final long serialVersionUID = 1L;
	
	private Double surplusValue;
	
	public Surplus(){}

	public Surplus(Double surplusValue) {
		super();
		this.surplusValue = surplusValue;
	}

	public Double getSurplusValue() {
		return surplusValue;
	}

	public void setSurplusValue(Double surplusValue) {
		this.surplusValue = surplusValue;
	}
}
