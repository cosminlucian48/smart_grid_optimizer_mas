package dreamsoftware.smartgridoptimizer.ontology.visitor;

import dreamsoftware.smartgridoptimizer.ontology.predicates.IterationStatus;

/**
 * The IReportVisitor interface represents a visitor for reporting-related entities.
 * It extends the IVisitor interface.
 */
public interface IReportVisitor extends IVisitor {
	/**
	 * Visits an IterationStatus entity for reporting.
	 *
	 * @param iterationStatus The IterationStatus entity to visit.
	 */
	void visit(IterationStatus iterationStatus);
}