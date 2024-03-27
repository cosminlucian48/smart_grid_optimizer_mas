package dreamsoftware.smartgridoptimizer.ontology.visitable;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerLoadVisitor;

/**
 * The IPowerLoadVisitable interface represents an object that can accept a power load visitor.
 */
public interface IPowerLoadVisitable extends IRequestVisitable {

	/**
	 * Accepts a power load visitor.
	 * @param visitor the power load visitor to accept.
	 * @return the result of visiting this object.
	 */
	Predicate accept(IPowerLoadVisitor visitor);
}