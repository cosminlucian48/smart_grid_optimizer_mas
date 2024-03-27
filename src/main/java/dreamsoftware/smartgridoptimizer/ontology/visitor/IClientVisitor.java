package dreamsoftware.smartgridoptimizer.ontology.visitor;

import dreamsoftware.smartgridoptimizer.ontology.predicates.CurrentBatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.predicates.CurrentConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.IterationStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierCurrentBudget;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierCurrentPrice;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierTotalPowerBought;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierTotalPowerSold;
import dreamsoftware.smartgridoptimizer.ontology.predicates.PowerCurrentlyGenerated;

public interface IClientVisitor extends IVisitor {
	void visit(IterationStatus iterationStatus);
	void visit(NotifierTotalPowerBought notifierTotalPowerBought);
	void visit(NotifierTotalPowerSold notifierTotalPowerSold);
	void visit(CurrentBatteryLevel currentBatteryLevel);
	void visit(CurrentConsumption currentConsumption);
	void visit(PowerCurrentlyGenerated powerCurrentlyGenerated);
    void visit(NotifierCurrentPrice notifierCurrentPrice);
    void visit(NotifierCurrentBudget notifierCurrentBudget);
}
