package dreamsoftware.smartgridoptimizer.ontology.visitable;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IBatteryVisitor;

public interface IBatteryVisitable extends IRequestVisitable{
	Predicate accept(IBatteryVisitor visitor);
}
