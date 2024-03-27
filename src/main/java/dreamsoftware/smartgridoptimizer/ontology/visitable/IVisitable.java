package dreamsoftware.smartgridoptimizer.ontology.visitable;

import dreamsoftware.smartgridoptimizer.ontology.visitor.IVisitor;

/**
 * The IVisitable interface represents a visitable object that accepts a visitor of type T.
 *
 * @param <T> The type of visitor that can visit this object.
 */
public interface IVisitable<T extends IVisitor> {
    /**
     * Accepts a visitor and allows it to perform operations on this visitable object.
     *
     * @param visitor The visitor to accept.
     */
    void accept(T visitor);
}