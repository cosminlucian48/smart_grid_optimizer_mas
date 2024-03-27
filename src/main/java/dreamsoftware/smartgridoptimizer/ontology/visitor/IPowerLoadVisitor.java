package dreamsoftware.smartgridoptimizer.ontology.visitor;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.UpdateLoad;

/**
 * The IPowerLoadVisitor interface represents a visitor for power load-related entities.
 * It extends the IRequestVisitor interface.
 */
public interface IPowerLoadVisitor extends IRequestVisitor {
	/**
	 * Visits an UpdateLoad request related to power load.
	 *
	 * @param request The UpdateLoad request to visit.
	 * @return The result of visiting the UpdateLoad request.
	 */
	Predicate visit(UpdateLoad request);

	/**
	 * Visits a GetStatus request related to power load.
	 *
	 * @param getStatus The GetStatus request to visit.
	 * @return The result of visiting the GetStatus request.
	 */
	Predicate visit(GetStatus getStatus);
}
