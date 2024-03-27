package dreamsoftware.smartgridoptimizer.ontology.visitor;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.predicates.BuyPower;
import dreamsoftware.smartgridoptimizer.ontology.predicates.SellingPower;

public interface IMarketVisitor extends IRequestVisitor {
	Predicate visit(BuyPower request);
	Predicate visit(SellingPower request);
}
