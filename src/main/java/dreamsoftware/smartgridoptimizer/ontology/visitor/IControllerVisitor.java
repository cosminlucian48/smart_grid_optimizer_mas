package dreamsoftware.smartgridoptimizer.ontology.visitor;

import dreamsoftware.smartgridoptimizer.ontology.predicates.GenerateEnergy;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierBatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.predicates.PerformConsumption;

/**
 * The IControllerVisitor interface represents a visitor for controller-related entities.
 * It extends the IVisitor interface.
 */
public interface IControllerVisitor extends IVisitor {
	/**
	 * Visits a GenerateEnergy entity.
	 *
	 * @param generateEnergy The GenerateEnergy entity to visit.
	 */
	void visit(GenerateEnergy generateEnergy);

	/**
	 * Visits a PerformConsumption entity.
	 *
	 * @param performConsumption The PerformConsumption entity to visit.
	 */
	void visit(PerformConsumption performConsumption);

	/**
	 * Visits a NotifierBatteryLevel entity.
	 *
	 * @param notifierBatteryLevel The NotifierBatteryLevel entity to visit.
	 */
	void visit(NotifierBatteryLevel notifierBatteryLevel);
}
