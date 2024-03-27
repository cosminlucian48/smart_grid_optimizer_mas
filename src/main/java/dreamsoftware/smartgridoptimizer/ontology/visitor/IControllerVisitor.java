package dreamsoftware.smartgridoptimizer.ontology.visitor;

import dreamsoftware.smartgridoptimizer.ontology.predicates.GenerateEnergy;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierBatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.predicates.PerformConsumption;

public interface IControllerVisitor extends IVisitor {
	void visit(GenerateEnergy generateEnergy);
	void visit(PerformConsumption performConsumption);
	void visit(NotifierBatteryLevel notifierBatteryLevel);
}
