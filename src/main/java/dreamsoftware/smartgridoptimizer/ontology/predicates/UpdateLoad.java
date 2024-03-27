package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IPowerLoadVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerLoadVisitor;

public class UpdateLoad implements Predicate, IPowerLoadVisitable {

	private static final long serialVersionUID = 1L;
	
	private LoadConsumption loadConsumption;
	
	public UpdateLoad(){}

	public UpdateLoad(LoadConsumption loadConsumption) {
		super();
		this.loadConsumption = loadConsumption;
	}

	public LoadConsumption getLoadConsumption() {
		return loadConsumption;
	}

	public void setLoadConsumption(LoadConsumption loadConsumption) {
		this.loadConsumption = loadConsumption;
	}

	public Predicate accept(IPowerLoadVisitor visitor) {
		return visitor.visit(this);
	}
}
