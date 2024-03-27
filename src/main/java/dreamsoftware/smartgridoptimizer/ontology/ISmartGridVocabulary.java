package dreamsoftware.smartgridoptimizer.ontology;

/**
 * The ISmartGridVocabulary interface defines constants for the vocabulary of the smart grid ontology.
 */
public interface ISmartGridVocabulary {
	// Agent-related vocabulary
	String AGENT_AID = "aid";
	String GET_STATUS = "getStatus";

	// Power Consumption Vocabulary
	String LOAD_CONSUMPTION = "loadConsumption";
	String LOAD_CONSUMPTION_VALUE = "loadConsumptionValue";
	String PERFORM_CONSUMPTION = "PerformConsumption";
	String CURRENT_CONSUMPTION = "CurrentConsumption";

	// Generate Power Vocabulary
	String POWER_GENERATED = "PowerGenerated";
	String POWER_GENERATED_VALUE = "powerGeneratedValue";
	String GENERATE_ENERGY = "generateEnergy";
	String POWER_CURRENTLY_GENERATED = "powerCurrentlyGenerated";

	String SURPLUS = "surplus";
	String SURPLUS_VALUE = "surplusValue";

	// Batteries Vocabulary
	String STORE_IN_BATTERY = "storeInBattery";
	String RETRIEVE_DEMAND = "retrieveDemand";
	String BATTERY_LEVEL = "batteryLevel";
	String BATTERY_LEVEL_VALUE = "batteryLevelValue";
	String NOTIFIER_BATTERY_LEVEL = "NotifierBatteryLevel";
	String CURRENT_BATTERY_LEVEL = "CurrentBatteryLevel";

	// Price Vocabulary
	String CURRENT_PRICE = "currentPrice";
	String CURRENT_PRICE_VALUE = "currentPriceValue";
	String NOTIFIER_CURRENT_PRICE = "NotifierCurrentPrice";
	String CURRENT_BUDGET = "currentBudget";
	String CURRENT_BUDGET_VALUE = "currentBudgetValue";
	String NOTIFIER_CURRENT_BUDGET = "NotifierCurrentBudget";

	String SELLING_POWER = "SellingPower";
	String UPDATE_LOAD = "UpdateLoad";
	String BUY_POWER = "BuyPower";
	String POWER_BOUGHT = "powerBought";
	String POWER_BOUGHT_VALUE = "powerBoughtValue";
	String NOTIFIER_POWER_BOUGHT = "NotifierPowerBought";
	String POWER_SOLD = "powerSold";
	String POWER_SOLD_VALUE = "powerSoldValue";
	String NOTIFIER_POWER_SOLD = "NotifierPowerSold";
	String NOTIFIER_LOAD_CONSUMPTION = "NotifierLoadConsumption";
	String ITERATION_STATUS = "IterationStatus";
	String SAVE_ITERATION_STATUS = "SaveIterationStatus";
	String ADJUST_LOAD_VALUE = "adjustLoadValue";
	String CHANGE_POWER_CONSUMPTION_MEASURES = "changePowerConsumptionMeasures";
	String POWER_CONSUMPTION_MEASURES = "powerConsumptionMeasures";

	// Services
	String ENERGY = "energy";
	String REPORT_GENERATED_ENERGY = "ReportGeneratedEnergy";
	String REPORT_LOAD_ENERGY = "ReportLoadEnergy";
	String REPORT_BATTERY_LEVEL = "BatteryLevel";
	String REPORT_ITERATION_STATUS = "ReportIterationStatus";
	String BUY_AND_SELL_ENERGY = "BuyAndSellEnergy";
	String REPORT_TOTAL_BOUGHT = "ReportTotalBought";
	String REPORT_TOTAL_SOLD = "ReportTotalSold";
	String GENERATE_REPORTS = "GenerateReports";
}