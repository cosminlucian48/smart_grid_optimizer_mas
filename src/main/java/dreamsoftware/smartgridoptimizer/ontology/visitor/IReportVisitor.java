package dreamsoftware.smartgridoptimizer.ontology.visitor;

import dreamsoftware.smartgridoptimizer.ontology.predicates.IterationStatus;

public interface IReportVisitor extends IVisitor {
	void visit(IterationStatus iterationStatus);
}
