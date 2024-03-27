package dreamsoftware.smartgridoptimizer.ontology.actions;


import jade.content.AgentAction;
import jade.util.leap.List;

public class ChangePowerConsumptionMeasures implements AgentAction {

	private static final long serialVersionUID = 1L;
	
	private List powerConsumptionMeasures;

	public ChangePowerConsumptionMeasures(List powerConsumptionMeasures) {
		super();
		this.powerConsumptionMeasures = powerConsumptionMeasures;
	}

	public List getPowerConsumptionMeasures() {
		return powerConsumptionMeasures;
	}

	public void setPowerConsumptionMeasures(List powerConsumptionMeasures) {
		this.powerConsumptionMeasures = powerConsumptionMeasures;
	}
}
