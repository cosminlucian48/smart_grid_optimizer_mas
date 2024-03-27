package dreamsoftware.smartgridoptimizer.ontology.visitable;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IMarketVisitor;

/**
 * The IMarketVisitable interface represents an object that can accept a market visitor.
 */
public interface IMarketVisitable extends IRequestVisitable {

	/**
	 * Accepts a market visitor.
	 * @param visitor the market visitor to accept.
	 * @return the result of visiting this object.
	 */
	Predicate accept(IMarketVisitor visitor);
}