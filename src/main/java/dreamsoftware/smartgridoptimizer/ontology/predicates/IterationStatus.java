package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IReportVisitor;

import java.io.Serial;

/**
 * The IterationStatus class represents a predicate used to provide status information about an iteration.
 * It implements the Predicate, IVisitable<IReportVisitor>, and IClientVisitable interfaces.
 */
public class IterationStatus implements Predicate, IVisitable<IReportVisitor>, IClientVisitable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Double powerGeneratedValue;
	private Double loadConsumptionValue;
	private Double surplusValue;
	private Double batteryLevelValue;
	private Double powerSoldValue;
	private Double powerBoughtValue;
	private Double adjustLoadValue;

	/**
	 * Default constructor.
	 */
	public IterationStatus() {
	}

	/**
	 * Parameterized constructor to set the values of the iteration status.
	 * @param powerGeneratedValue the generated power value.
	 * @param loadConsumptionValue the load consumption value.
	 * @param surplusValue the surplus value.
	 * @param batteryLevelValue the battery level value.
	 * @param powerSoldValue the power sold value.
	 * @param powerBoughtValue the power bought value.
	 * @param adjustLoadValue the adjusted load value.
	 */
	public IterationStatus(Double powerGeneratedValue, Double loadConsumptionValue, Double surplusValue,
						   Double batteryLevelValue, Double powerSoldValue, Double powerBoughtValue, Double adjustLoadValue) {
		super();
		this.powerGeneratedValue = powerGeneratedValue;
		this.loadConsumptionValue = loadConsumptionValue;
		this.surplusValue = surplusValue;
		this.batteryLevelValue = batteryLevelValue;
		this.powerSoldValue = powerSoldValue;
		this.powerBoughtValue = powerBoughtValue;
		this.adjustLoadValue = adjustLoadValue;
	}

	public Double getPowerGeneratedValue() {
		return powerGeneratedValue;
	}

	public void setPowerGeneratedValue(Double powerGeneratedValue) {
		this.powerGeneratedValue = powerGeneratedValue;
	}

	public Double getLoadConsumptionValue() {
		return loadConsumptionValue;
	}

	public void setLoadConsumptionValue(Double loadConsumptionValue) {
		this.loadConsumptionValue = loadConsumptionValue;
	}

	public Double getSurplusValue() {
		return surplusValue;
	}

	public void setSurplusValue(Double surplusValue) {
		this.surplusValue = surplusValue;
	}

	public Double getBatteryLevelValue() {
		return batteryLevelValue;
	}

	public void setBatteryLevelValue(Double batteryLevelValue) {
		this.batteryLevelValue = batteryLevelValue;
	}

	public Double getPowerSoldValue() {
		return powerSoldValue;
	}

	public void setPowerSoldValue(Double powerSoldValue) {
		this.powerSoldValue = powerSoldValue;
	}

	public Double getPowerBoughtValue() {
		return powerBoughtValue;
	}

	public void setPowerBoughtValue(Double powerBoughtValue) {
		this.powerBoughtValue = powerBoughtValue;
	}

	public Double getAdjustLoadValue() {
		return adjustLoadValue;
	}

	public void setAdjustLoadValue(Double adjustLoadValue) {
		this.adjustLoadValue = adjustLoadValue;
	}

	/**
	 * Accepts a report visitor to process the IterationStatus instance.
	 * @param visitor the report visitor.
	 */
	public void accept(IReportVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Accepts a client visitor to process the IterationStatus instance.
	 * @param visitor the client visitor.
	 */
	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Overrides the toString method to provide a string representation of the IterationStatus instance.
	 * @return a string representation of the IterationStatus instance.
	 */
	@Override
	public String toString() {
		return "IterationStatus [powerGeneratedValue=" + powerGeneratedValue + ", loadConsumptionValue="
				+ loadConsumptionValue + ", surplusValue=" + surplusValue + ", batteryLevelValue=" + batteryLevelValue
				+ ", powerSoldValue=" + powerSoldValue + ", powerBoughtValue=" + powerBoughtValue + "]";
	}
}
