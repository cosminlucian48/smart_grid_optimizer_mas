package dreamsoftware.smartgridoptimizer.ontology.visitable;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerLoadVisitor;

public interface IPowerLoadVisitable extends IRequestVisitable{
	Predicate accept(IPowerLoadVisitor visitor);
}
