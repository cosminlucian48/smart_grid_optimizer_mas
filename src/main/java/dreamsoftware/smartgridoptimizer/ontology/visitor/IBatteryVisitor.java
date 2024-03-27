package dreamsoftware.smartgridoptimizer.ontology.visitor;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.RetrieveDemand;
import dreamsoftware.smartgridoptimizer.ontology.predicates.StoreInBattery;

/**
 * The IBatteryVisitor interface represents a visitor for battery-related requests.
 * It extends the IRequestVisitor interface.
 */
public interface IBatteryVisitor extends IRequestVisitor {
	/**
	 * Visits a StoreInBattery request.
	 *
	 * @param request The StoreInBattery request to visit.
	 * @return The response predicate generated as a result of the visit.
	 */
	Predicate visit(StoreInBattery request);

	/**
	 * Visits a RetrieveDemand request.
	 *
	 * @param request The RetrieveDemand request to visit.
	 * @return The response predicate generated as a result of the visit.
	 */
	Predicate visit(RetrieveDemand request);

	/**
	 * Visits a GetStatus request.
	 *
	 * @param getStatus The GetStatus request to visit.
	 * @return The response predicate generated as a result of the visit.
	 */
	Predicate visit(GetStatus getStatus);
}
