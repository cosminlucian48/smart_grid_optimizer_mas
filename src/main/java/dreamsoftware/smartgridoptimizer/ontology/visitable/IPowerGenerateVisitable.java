package dreamsoftware.smartgridoptimizer.ontology.visitable;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerGenerateVisitor;

/**
 * The IPowerGenerateVisitable interface represents an object that can accept a power generation visitor.
 */
public interface IPowerGenerateVisitable extends IRequestVisitable {

	/**
	 * Accepts a power generation visitor.
	 * @param visitor the power generation visitor to accept.
	 * @return the result of visiting this object.
	 */
	Predicate accept(IPowerGenerateVisitor visitor);
}
