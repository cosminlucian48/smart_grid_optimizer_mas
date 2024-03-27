package dreamsoftware.smartgridoptimizer.ontology.visitable;

import dreamsoftware.smartgridoptimizer.ontology.visitor.IVisitor;

public interface IVisitable<T extends IVisitor> {
    void accept(T visitor);
}
