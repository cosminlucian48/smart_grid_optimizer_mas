package dreamsoftware.smartgridoptimizer.ontology.actions;


import jade.content.AgentAction;
import jade.util.leap.List;
import java.io.Serial;

/**
 * The ChangePowerConsumptionMeasures class represents an agent action to change power consumption measures.
 * It implements the AgentAction interface.
 */
public class ChangePowerConsumptionMeasures implements AgentAction {

	@Serial
	private static final long serialVersionUID = 1L;

	private List powerConsumptionMeasures;

	/**
	 * Constructs a ChangePowerConsumptionMeasures object with the specified power consumption measures.
	 * @param powerConsumptionMeasures the power consumption measures to set.
	 */
	public ChangePowerConsumptionMeasures(List powerConsumptionMeasures) {
		super();
		this.powerConsumptionMeasures = powerConsumptionMeasures;
	}

	/**
	 * Retrieves the power consumption measures.
	 * @return the power consumption measures.
	 */
	public List getPowerConsumptionMeasures() {
		return powerConsumptionMeasures;
	}

	/**
	 * Sets the power consumption measures.
	 * @param powerConsumptionMeasures the power consumption measures to set.
	 */
	public void setPowerConsumptionMeasures(List powerConsumptionMeasures) {
		this.powerConsumptionMeasures = powerConsumptionMeasures;
	}
}