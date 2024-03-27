package dreamsoftware.smartgridoptimizer.ontology.visitable;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IMarketVisitor;

public interface IMarketVisitable extends IRequestVisitable{
	Predicate accept(IMarketVisitor visitor);
}
