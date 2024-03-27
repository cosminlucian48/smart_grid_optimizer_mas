package dreamsoftware.smartgridoptimizer.ontology;

public interface ISmartGridVocabulary {
	// Vocabulary of the ontology that will manage the agents
	public static final String AGENT_AID = "aid";
	public static final String GET_STATUS = "getStatus";
	// Power Consumption Vocabulary
	public static final String LOAD_CONSUMPTION = "loadConsumption";
	public static final String LOAD_CONSUMPTION_VALUE = "loadConsumptionValue";
	public static final String PERFORM_CONSUMPTION = "PerformConsumption";
	public static final String CURRENT_CONSUMPTION = "CurrentConsumption";
	// Generate Power Vocabulary
	public static final String POWER_GENERATED = "PowerGenerated";
	public static final String POWER_GENERATED_VALUE = "powerGeneratedValue";
	public static final String GENERATE_ENERGY = "generateEnergy";
	public static final String POWER_CURRENTLY_GENERATED = "powerCurrentlyGenerated";
	
	public static final String SURPLUS = "surplus";
	public static final String SURPLUS_VALUE = "surplusValue";
	// Batteries Vocabulary
	public static final String STORE_IN_BATTERY = "storeInBattery";
	public static final String RETRIEVE_DEMAND = "retrieveDemand";
	public static final String BATTERY_LEVEL = "batteryLevel";
	public static final String BATTERY_LEVEL_VALUE = "batteryLevelValue";
	public static final String NOTIFIER_BATTERY_LEVEL = "NotifierBatteryLevel";
	public static final String CURRENT_BATTERY_LEVEL = "CurrentBatteryLevel";
	
	//Price Vocabulary
	public static final String CURRENT_PRICE = "currentPrice";
	public static final String CURRENT_PRICE_VALUE = "currentPriceValue";
	public static final String NOTIFIER_CURRENT_PRICE = "NotifierCurrentPrice";
	public static final String CURRENT_BUDGET = "currentBudget";
	public static final String CURRENT_BUDGET_VALUE = "currentBudgetValue";
	public static final String NOTIFIER_CURRENT_BUDGET = "NotifierCurrentBudget";
	
	
	public static final String SELLING_POWER = "SellingPower";
	public static final String UPDATE_LOAD = "UpdateLoad";
	public static final String BUY_POWER = "BuyPower";
	public static final String POWER_BOUGHT = "powerBought";
	public static final String POWER_BOUGHT_VALUE = "powerBoughtValue";
	public static final String NOTIFIER_POWER_BOUGHT = "NotifierPowerBought";
	public static final String POWER_SOLD = "powerSold";
	public static final String POWER_SOLD_VALUE = "powerSoldValue";
	public static final String NOTIFIER_POWER_SOLD = "NotifierPowerSold";
	public static final String NOTIFIER_LOAD_CONSUMPTION = "NotifierLoadConsumption";
	public static final String ITERATION_STATUS = "IterationStatus";
	public static final String SAVE_ITERATION_STATUS = "SaveIterationStatus";
	public static final String ADJUST_LOAD_VALUE = "adjustLoadValue";
	public static final String CHANGE_POWER_CONSUMPTION_MEASURES = "changePowerConsumptionMeasures";
	public static final String POWER_CONSUMPTION_MEASURES = "powerConsumptionMeasures";
	
	
	//services
	public static final String ENERGY = "energy";
	public static final String REPORT_GENERATED_ENERGY = "ReportGeneratedEnergy";
	public static final String REPORT_LOAD_ENERGY = "ReportLoadEnergy";
	public static final String REPORT_BATTERY_LEVEL = "BatteryLevel";
	public static final String REPORT_ITERATION_STATUS = "ReportIterationStatus";
	public static final String BUY_AND_SELL_ENERGY = "BuyAndSellEnergy";
	public static final String REPORT_TOTAL_BOUGHT = "ReportTotalBought";
	public static final String REPORT_TOTAL_SOLD = "ReportTotalSold";
	public static final String GENERATE_REPORTS = "GenerateReports";
	
}
