package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;

public class NotifierLoadConsumption implements Predicate {

	private static final long serialVersionUID = 1L;
	
	private LoadConsumption loadConsumption;

	public NotifierLoadConsumption(){}


	public NotifierLoadConsumption(LoadConsumption loadConsumption) {
		super();
		this.loadConsumption = loadConsumption;
	}

	public LoadConsumption getLoadConsumption() {
		return loadConsumption;
	}

	public void setLoadConsumption(LoadConsumption loadConsumption) {
		this.loadConsumption = loadConsumption;
	}

}
