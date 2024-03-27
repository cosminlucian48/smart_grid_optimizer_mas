package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IBatteryVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IPowerGenerateVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IPowerLoadVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IBatteryVisitor;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerGenerateVisitor;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerLoadVisitor;

public class GetStatus implements Predicate, IBatteryVisitable, IPowerLoadVisitable, IPowerGenerateVisitable {

	private static final long serialVersionUID = 1L;

	public Predicate accept(IBatteryVisitor visitor) {
		return visitor.visit(this);
	}

	public Predicate accept(IPowerLoadVisitor visitor) {
		return visitor.visit(this);
	}

	public Predicate accept(IPowerGenerateVisitor visitor) {
		return visitor.visit(this);
	}
}
