package dreamsoftware.smartgridoptimizer.ontology.visitable;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IBatteryVisitor;

/**
 * The IBatteryVisitable interface extends the IRequestVisitable interface and represents an object
 * that can accept a battery visitor.
 */
public interface IBatteryVisitable extends IRequestVisitable {

	/**
	 * Accepts a battery visitor.
	 * @param visitor the battery visitor to accept.
	 * @return the predicate produced by the visitor.
	 */
	Predicate accept(IBatteryVisitor visitor);
}
