package dreamsoftware.smartgridoptimizer.gui;


public interface ISmartGridMonitor {
	void setLoadValue(Double loadValue);
	void setWindPower(Double windPowerValue);
	void setSurplus(Double surplus);
	void setAdjustLoad(Double adjustLoad);
	void setTotalBattery(Double totalBattery);
	void setPowerSold(Double powerSold);
	void setPowerBought(Double powerBought);
	void setTotalSold(Double totalSold);
	void setTotalBought(Double totalBought);
	void addGenerateEnergyAgent(String name);
	void addConsumptionEnergyAgent(String name);
	void addBatteryAgent(String name);
	void removeGenerateEnergyAgent(String name);
	void removeConsumptionEnergyAgent(String name);
	void removeBatteryAgent(String name);
	void updateAgentValue(String name, Double value);
	void setCurrentPrice(Double currentPriceValue);
	void setCurrentBudget(Double currentBudgetValue);
}
