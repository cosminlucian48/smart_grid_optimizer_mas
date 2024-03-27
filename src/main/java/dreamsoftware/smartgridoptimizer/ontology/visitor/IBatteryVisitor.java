package dreamsoftware.smartgridoptimizer.ontology.visitor;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.RetrieveDemand;
import dreamsoftware.smartgridoptimizer.ontology.predicates.StoreInBattery;

public interface IBatteryVisitor extends IRequestVisitor {
	Predicate visit(StoreInBattery request);
	Predicate visit(RetrieveDemand request);
	Predicate visit(GetStatus getStatus);
}
