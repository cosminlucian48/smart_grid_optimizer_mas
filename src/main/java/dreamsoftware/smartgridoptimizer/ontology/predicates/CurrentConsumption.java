package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import jade.core.AID;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

public class CurrentConsumption implements Predicate, IClientVisitable {

	private static final long serialVersionUID = 1L;
	
	private AID aid;
	private LoadConsumption loadConsumption;
	
	public CurrentConsumption(){}
	
	public CurrentConsumption(AID aid, LoadConsumption loadConsumption) {
		super();
		this.aid = aid;
		this.loadConsumption = loadConsumption;
	}

	public AID getAid() {
		return aid;
	}

	public void setAid(AID aid) {
		this.aid = aid;
	}

	public LoadConsumption getLoadConsumption() {
		return loadConsumption;
	}

	public void setLoadConsumption(LoadConsumption loadConsumption) {
		this.loadConsumption = loadConsumption;
	}

	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}
}
