package dreamsoftware.smartgridoptimizer.ontology.predicates;

import jade.content.Predicate;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IClientVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IClientVisitor;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IReportVisitor;

public class IterationStatus implements Predicate, IVisitable<IReportVisitor>, IClientVisitable {

	private static final long serialVersionUID = 1L;

	private Double powerGeneratedValue;
	private Double loadConsumptionValue;
	private Double surplusValue;
	private Double batteryLevelValue;
	private Double powerSoldValue;
	private Double powerBoughtValue;
	private Double adjustLoadValue;
	
	public IterationStatus(){}

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

	public void accept(IReportVisitor visitor) {
		visitor.visit(this);
	}
	
	public void accept(IClientVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String toString() {
		return "IterationStatus [powerGeneratedValue=" + powerGeneratedValue + ", loadConsumptionValue="
				+ loadConsumptionValue + ", surplusValue=" + surplusValue + ", batteryLevelValue=" + batteryLevelValue
				+ ", powerSoldValue=" + powerSoldValue + ", powerBoughtValue=" + powerBoughtValue + "]";
	}
}
