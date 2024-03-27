package dreamsoftware.smartgridoptimizer.ontology.visitor;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;

public interface IPowerGenerateVisitor extends IRequestVisitor {
	Predicate visit(GetStatus getStatus);
}
