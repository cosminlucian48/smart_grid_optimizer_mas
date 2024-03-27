package dreamsoftware.smartgridoptimizer.ontology.visitor;

import dreamsoftware.smartgridoptimizer.ontology.predicates.CurrentBatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.predicates.CurrentConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.IterationStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierCurrentBudget;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierCurrentPrice;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierTotalPowerBought;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierTotalPowerSold;
import dreamsoftware.smartgridoptimizer.ontology.predicates.PowerCurrentlyGenerated;

/**
 * The IClientVisitor interface represents a visitor for client-related entities.
 * It extends the IVisitor interface.
 */
public interface IClientVisitor extends IVisitor {
	/**
	 * Visits an IterationStatus entity.
	 *
	 * @param iterationStatus The IterationStatus entity to visit.
	 */
	void visit(IterationStatus iterationStatus);

	/**
	 * Visits a NotifierTotalPowerBought entity.
	 *
	 * @param notifierTotalPowerBought The NotifierTotalPowerBought entity to visit.
	 */
	void visit(NotifierTotalPowerBought notifierTotalPowerBought);

	/**
	 * Visits a NotifierTotalPowerSold entity.
	 *
	 * @param notifierTotalPowerSold The NotifierTotalPowerSold entity to visit.
	 */
	void visit(NotifierTotalPowerSold notifierTotalPowerSold);

	/**
	 * Visits a CurrentBatteryLevel entity.
	 *
	 * @param currentBatteryLevel The CurrentBatteryLevel entity to visit.
	 */
	void visit(CurrentBatteryLevel currentBatteryLevel);

	/**
	 * Visits a CurrentConsumption entity.
	 *
	 * @param currentConsumption The CurrentConsumption entity to visit.
	 */
	void visit(CurrentConsumption currentConsumption);

	/**
	 * Visits a PowerCurrentlyGenerated entity.
	 *
	 * @param powerCurrentlyGenerated The PowerCurrentlyGenerated entity to visit.
	 */
	void visit(PowerCurrentlyGenerated powerCurrentlyGenerated);

	/**
	 * Visits a NotifierCurrentPrice entity.
	 *
	 * @param notifierCurrentPrice The NotifierCurrentPrice entity to visit.
	 */
	void visit(NotifierCurrentPrice notifierCurrentPrice);

	/**
	 * Visits a NotifierCurrentBudget entity.
	 *
	 * @param notifierCurrentBudget The NotifierCurrentBudget entity to visit.
	 */
	void visit(NotifierCurrentBudget notifierCurrentBudget);
}
