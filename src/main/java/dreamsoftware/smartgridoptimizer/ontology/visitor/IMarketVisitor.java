package dreamsoftware.smartgridoptimizer.ontology.visitor;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.predicates.BuyPower;
import dreamsoftware.smartgridoptimizer.ontology.predicates.SellingPower;

/**
 * The IMarketVisitor interface represents a visitor for market-related entities.
 * It extends the IRequestVisitor interface.
 */
public interface IMarketVisitor extends IRequestVisitor {
	/**
	 * Visits a BuyPower request.
	 *
	 * @param request The BuyPower request to visit.
	 * @return The result of visiting the BuyPower request.
	 */
	Predicate visit(BuyPower request);

	/**
	 * Visits a SellingPower request.
	 *
	 * @param request The SellingPower request to visit.
	 * @return The result of visiting the SellingPower request.
	 */
	Predicate visit(SellingPower request);
}
