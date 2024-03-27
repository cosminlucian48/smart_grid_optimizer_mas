package dreamsoftware.smartgridoptimizer.ontology.visitable;

import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

/**
 * The IClientVisitable interface represents an object that can accept a client visitor.
 */
public interface IClientVisitable {

	/**
	 * Accepts a client visitor.
	 * @param visitor the client visitor to accept.
	 */
	void accept(IClientVisitor visitor);
}
