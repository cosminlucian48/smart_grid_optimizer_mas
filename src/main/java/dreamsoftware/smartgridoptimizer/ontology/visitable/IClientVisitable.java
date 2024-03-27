package dreamsoftware.smartgridoptimizer.ontology.visitable;

import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;

public interface IClientVisitable {
	void accept(IClientVisitor visitor);
}
