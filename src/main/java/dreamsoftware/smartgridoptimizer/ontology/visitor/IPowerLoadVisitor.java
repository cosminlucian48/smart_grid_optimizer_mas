package dreamsoftware.smartgridoptimizer.ontology.visitor;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.UpdateLoad;

public interface IPowerLoadVisitor extends IRequestVisitor {
	Predicate visit(UpdateLoad request);
	Predicate visit(GetStatus getStatus);
}
