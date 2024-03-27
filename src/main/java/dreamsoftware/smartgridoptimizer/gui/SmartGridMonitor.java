package dreamsoftware.smartgridoptimizer.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jade.gui.GuiEvent;
import dreamsoftware.smartgridoptimizer.agents.impl.ClientAgent;
import dreamsoftware.smartgridoptimizer.gui.panel.ISmartGridStatusPanel;
import dreamsoftware.smartgridoptimizer.gui.panel.ModelValuesEnum;
import dreamsoftware.smartgridoptimizer.gui.panel.impl.ActionsPanel;
import dreamsoftware.smartgridoptimizer.gui.panel.impl.DynamicTimeSeriesChart;
import dreamsoftware.smartgridoptimizer.gui.panel.impl.SmartGridStatusPanel;
import dreamsoftware.smartgridoptimizer.gui.panel.impl.SimulationViewerPanel;

public final class SmartGridMonitor extends JFrame implements ISmartGridMonitor, ActionsPanel.ActionsListener {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(SmartGridMonitor.class);
	
	private ClientAgent clientAgent;
	private ISmartGridStatusPanel mashesStatusPanel;
	private SimulationViewerPanel simulationViewer;
	private DynamicTimeSeriesChart windPowerChart;
	private DynamicTimeSeriesChart loadChart;
	private DynamicTimeSeriesChart batteryChart;
	private DynamicTimeSeriesChart powerSoldChart;
	private DynamicTimeSeriesChart boughtPowerChart;
	private DynamicTimeSeriesChart currentPriceChart;
	
	private Integer generateEnergyAgents = 0;
	private Integer consumptionEnergyAgents = 0;
	private Integer batteryAgents = 0;
	
	public SmartGridMonitor(ClientAgent clientAgent) {
		this.clientAgent = clientAgent;
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setTitle("MASHES sim v2");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		// simulation viewer panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mashesStatusPanel = new SmartGridStatusPanel();
		mainPanel.add((SmartGridStatusPanel)mashesStatusPanel);
		simulationViewer = new SimulationViewerPanel();
		mainPanel.add(simulationViewer);
		this.add(mainPanel);
		// Time Series Panel
		
		JPanel timeSeriesPanel = new JPanel();
		timeSeriesPanel.setLayout(new GridLayout(0, 2));
		windPowerChart = new DynamicTimeSeriesChart("Wind Power", 0, 50);
		timeSeriesPanel.add(windPowerChart);
		loadChart = new DynamicTimeSeriesChart("Power Consumption", 0 ,50);
		timeSeriesPanel.add(loadChart);
		batteryChart = new DynamicTimeSeriesChart("Battery", 0 , 100);
		timeSeriesPanel.add(batteryChart);
		powerSoldChart = new DynamicTimeSeriesChart("Power Sold", 0 ,100);
		timeSeriesPanel.add(powerSoldChart);
		boughtPowerChart = new DynamicTimeSeriesChart("Bought Power", 0, 100);
		timeSeriesPanel.add(boughtPowerChart);
		currentPriceChart = new DynamicTimeSeriesChart("Current Price", 0, 10);
		timeSeriesPanel.add(currentPriceChart);
		this.add(timeSeriesPanel);
		
		/*ActionsPanel actionsPanel = new ActionsPanel(this);
		statusPanel.add(actionsPanel);*/
		
		/*AdjustMashesModelPanel adjustMashesModelPanel = new AdjustMashesModelPanel();
		statusPanel.add(adjustMashesModelPanel);
		*/
		// Adjust your size to your content
		this.pack();
		
		// center frame on screen
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
		this.setResizable(false);
		this.setVisible(true);
	}

	public void setLoadValue(Double loadValue) {
		mashesStatusPanel.setValue(ModelValuesEnum.LOAD, new DecimalFormat("###.##").format(loadValue));
		logger.info("loadValue.floatValue() -> " + loadValue.floatValue());
		loadChart.update(loadValue.floatValue());
	}

	public void setWindPower(Double windPowerValue) {
		mashesStatusPanel.setValue(ModelValuesEnum.WIND_POWER, new DecimalFormat("###.##").format(windPowerValue));
		windPowerChart.update(windPowerValue.floatValue());
	}

	public void setSurplus(Double surplus) {
		mashesStatusPanel.setValue(ModelValuesEnum.SURPLUS, new DecimalFormat("###.##").format(surplus));
	}

	public void setAdjustLoad(Double adjustLoad) {
		mashesStatusPanel.setValue(ModelValuesEnum.ADJUST_LOAD, new DecimalFormat("###.##").format(adjustLoad));
	}

	public void setTotalBattery(Double totalBattery) {
		mashesStatusPanel.setValue(ModelValuesEnum.TOTAL_BATTERY, new DecimalFormat("###.##").format(totalBattery));
		batteryChart.update(totalBattery.floatValue());
	}

	public void setPowerSold(Double powerSold) {
		mashesStatusPanel.setValue(ModelValuesEnum.POWER_SOLD, new DecimalFormat("###.##").format(powerSold));
		powerSoldChart.update(powerSold.floatValue());
	}

	public void setPowerBought(Double powerBought) {
		mashesStatusPanel.setValue(ModelValuesEnum.POWER_BOUGHT, new DecimalFormat("###.##").format(powerBought));
		boughtPowerChart.update(powerBought.floatValue());
	}

	public void setTotalSold(Double totalSold) {
		mashesStatusPanel.setValue(ModelValuesEnum.TOTAL_SOLD, new DecimalFormat("###.##").format(totalSold));
		
	}

	public void setTotalBought(Double totalBought) {
		mashesStatusPanel.setValue(ModelValuesEnum.TOTAL_BOUGHT, new DecimalFormat("###.##").format(totalBought));
	}

	public void onChangePowerConsumptionMeasures(List<Double> measures) {
		GuiEvent ge = new GuiEvent(this, ClientAgent.CHANGE_POWER_CONSUMPTION_MEASURES);
		ge.addParameter(measures);
		clientAgent.postGuiEvent(ge);
	}

	public void addGenerateEnergyAgent(String name) {
		simulationViewer.addGeneratePowerAgentComponent(name, 0.0);
		mashesStatusPanel.setValue(ModelValuesEnum.GENERATE_ENERGY_AGENTS, String.valueOf(++generateEnergyAgents));
	}

	public void addConsumptionEnergyAgent(String name) {
		simulationViewer.addConsumePowerAgentComponent(name, 0.0);
		mashesStatusPanel.setValue(ModelValuesEnum.CONSUMPTION_ENERGY_AGENTS, String.valueOf(++consumptionEnergyAgents));
	}

	public void addBatteryAgent(String name) {
		simulationViewer.addBatteryAgentComponent(name, 0.0);
		mashesStatusPanel.setValue(ModelValuesEnum.BATTERY_AGENTS, String.valueOf(++batteryAgents));
	}

	public void removeGenerateEnergyAgent(String name) {
		simulationViewer.removeAgent(name);
		mashesStatusPanel.setValue(ModelValuesEnum.GENERATE_ENERGY_AGENTS, String.valueOf(--generateEnergyAgents));
	}

	public void removeConsumptionEnergyAgent(String name) {
		simulationViewer.removeAgent(name);
		mashesStatusPanel.setValue(ModelValuesEnum.CONSUMPTION_ENERGY_AGENTS, String.valueOf(--consumptionEnergyAgents));
	}

	public void removeBatteryAgent(String name) {
		simulationViewer.removeAgent(name);
		mashesStatusPanel.setValue(ModelValuesEnum.BATTERY_AGENTS, String.valueOf(--batteryAgents));
	}

	public void updateAgentValue(String name, Double value) {
		logger.info("Update Agent -> " + name + " with value -> " + value);
		simulationViewer.setAgentValue(name, value);
	}

	public void setCurrentPrice(Double currentPriceValue) {
		logger.info("Current Price Value -> " + currentPriceValue);
		currentPriceChart.update(currentPriceValue.floatValue());
		mashesStatusPanel.setValue(ModelValuesEnum.CURRENT_PRICE, new DecimalFormat("###.##").format(currentPriceValue));
	}

	public void setCurrentBudget(Double currentBudgetValue) {
		mashesStatusPanel.setValue(ModelValuesEnum.BUDGET, new DecimalFormat("###.##").format(currentBudgetValue));
	}
}
