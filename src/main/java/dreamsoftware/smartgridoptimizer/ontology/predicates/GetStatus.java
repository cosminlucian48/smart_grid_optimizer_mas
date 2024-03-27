package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IBatteryVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IPowerGenerateVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IPowerLoadVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IBatteryVisitor;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerGenerateVisitor;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IPowerLoadVisitor;

import java.io.Serial;

/**
 * The GetStatus class represents a predicate used to request status information.
 * It implements the Predicate, IBatteryVisitable, IPowerLoadVisitable, and IPowerGenerateVisitable interfaces.
 */
public class GetStatus implements Predicate, IBatteryVisitable, IPowerLoadVisitable, IPowerGenerateVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Accepts a battery visitor to process the GetStatus instance.
	 * @param visitor the battery visitor.
	 * @return the result of the visit.
	 */
	public Predicate accept(IBatteryVisitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Accepts a power load visitor to process the GetStatus instance.
	 * @param visitor the power load visitor.
	 * @return the result of the visit.
	 */
	public Predicate accept(IPowerLoadVisitor visitor) {
		return visitor.visit(this);
	}

	/**
	 * Accepts a power generate visitor to process the GetStatus instance.
	 * @param visitor the power generate visitor.
	 * @return the result of the visit.
	 */
	public Predicate accept(IPowerGenerateVisitor visitor) {
		return visitor.visit(this);
	}
}