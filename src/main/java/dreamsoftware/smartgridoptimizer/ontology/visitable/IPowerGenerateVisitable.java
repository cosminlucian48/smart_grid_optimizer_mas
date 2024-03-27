package dreamsoftware.smartgridoptimizer.ontology.visitable;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerGenerateVisitor;

public interface IPowerGenerateVisitable extends IRequestVisitable{
	Predicate accept(IPowerGenerateVisitor visitor);
}
