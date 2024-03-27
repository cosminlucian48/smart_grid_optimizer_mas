package dreamsoftware.smartgridoptimizer.ontology.visitor;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;

/**
 * The IPowerGenerateVisitor interface represents a visitor for power generation-related entities.
 * It extends the IRequestVisitor interface.
 */
public interface IPowerGenerateVisitor extends IRequestVisitor {
	/**
	 * Visits a GetStatus request related to power generation.
	 *
	 * @param getStatus The GetStatus request to visit.
	 * @return The result of visiting the GetStatus request.
	 */
	Predicate visit(GetStatus getStatus);
}
