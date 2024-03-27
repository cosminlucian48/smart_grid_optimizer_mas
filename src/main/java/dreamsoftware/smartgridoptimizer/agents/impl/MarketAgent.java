package dreamsoftware.smartgridoptimizer.agents.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.simplejmx.common.JmxAttributeFieldInfo;
import com.j256.simplejmx.common.JmxAttributeMethodInfo;
import com.j256.simplejmx.common.JmxOperationInfo;
import com.j256.simplejmx.common.JmxResourceInfo;
import jade.content.ContentElement;
import jade.content.Predicate;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import dreamsoftware.smartgridoptimizer.agents.PublishSubscribeAgent;
import dreamsoftware.smartgridoptimizer.agents.behaviours.HandlerRequest;
import dreamsoftware.smartgridoptimizer.ontology.concepts.CurrentBudget;
import dreamsoftware.smartgridoptimizer.ontology.concepts.CurrentPrice;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerBought;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerSold;
import dreamsoftware.smartgridoptimizer.ontology.predicates.BuyPower;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierCurrentBudget;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierCurrentPrice;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierTotalPowerBought;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierTotalPowerSold;
import dreamsoftware.smartgridoptimizer.ontology.predicates.SellingPower;
import dreamsoftware.smartgridoptimizer.ontology.visitable.IMarketVisitable;
import dreamsoftware.smartgridoptimizer.ontology.visitor.IMarketVisitor;

public class MarketAgent extends PublishSubscribeAgent implements IMarketVisitor {
	
	private Logger logger = LoggerFactory.getLogger(MarketAgent.class);
	
	private static final long serialVersionUID = 1L;
	
	public final static String AGENT_NAME = "MARKET_AGENT";
	public final static String AGENT_DESCRIPTION = "Agent that sells and buys energy";
	
	private static final Integer UPDATE_PRICE_INTERVAL = 1000;
	private static final Integer CHECK_BUDGET_INTERVAL = 1000;
	
	private Double pbought = 0.0; //power bought to the grid
	private Double totalbought = 0.0;
	private Double psold = 0.0; //power sold to the grid
	private Double totalsold = 0.0;
	private Double price = 0.0; //current price at the given time
	private Double budget = 0.0;
	
	private HandlerRequest handlerRequest = new HandlerRequest(this, fipaRequestTemplate) {
		
		private static final long serialVersionUID = 1L;

		@Override
		protected boolean isValidRequest(ContentElement ce) {
			return ce instanceof BuyPower || ce instanceof SellingPower || ce instanceof GetStatus;
		}

		@Override
		protected Predicate onGenerateResponse(ContentElement requestContent) {
			IMarketVisitable requestVisitable = (IMarketVisitable)requestContent;
			return requestVisitable.accept(MarketAgent.this);
		}
		
	};
	
	@Override
	protected void setup() {
		super.setup();
		this.addBehaviour(handlerRequest);
		
		/**
		 * Behaviour for load csv with price value at the given time.
		 */
		this.addBehaviour(new OneShotBehaviour() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void action() {
				
				String csvFile = "/csv/net_price.csv";
				BufferedReader br = null;
				
				List<Double> netPrices = new ArrayList<Double>();
				
				try {
					br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(csvFile), "UTF-8"));
					String line = "";
		            while ((line = br.readLine()) != null) {
		            	// check if is a double value.
		            	if(Pattern.matches("([0-9]*)\\.([0-9]*)", line)){
		            		netPrices.add(Double.parseDouble(line));
		            	}
		            }
		         
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(netPrices.size() > 0) {
					myAgent.addBehaviour(new PeriodicallyUpdatePriceBehaviour(myAgent, UPDATE_PRICE_INTERVAL, netPrices));
					myAgent.addBehaviour(new PeriodicallyCheckBudgetBehaviour(myAgent, CHECK_BUDGET_INTERVAL));
				}
			}
			
		});
	}

	@Override
	protected void onRegisterServices(DFAgentDescription description) {
		ServiceDescription buyAndSellEnergyService = new ServiceDescription();
		buyAndSellEnergyService.setType(ENERGY);
		buyAndSellEnergyService.setName(BUY_AND_SELL_ENERGY);
        description.addServices(buyAndSellEnergyService);
        // Report Total Power Bought
        ServiceDescription reportTotalBought = new ServiceDescription();
        reportTotalBought.setType(ENERGY);
        reportTotalBought.setName(REPORT_TOTAL_BOUGHT);
        description.addServices(reportTotalBought);
        // Report Total Power Sold
        ServiceDescription reportTotalSold = new ServiceDescription();
        reportTotalSold.setType(ENERGY);
        reportTotalSold.setName(REPORT_TOTAL_SOLD);
        description.addServices(reportTotalSold);
	}


	/**
	 * Process a BuyPower FIPA-Request
	 */
	public Predicate visit(BuyPower request) {
		logger.debug("BuyPower received ...");
		pbought = request.getSurplus().getSurplusValue();
		totalbought += pbought;
		NotifierTotalPowerBought totalPowerBought =  new NotifierTotalPowerBought(new PowerBought(totalbought));
		notifyToSubscribers(totalPowerBought);
		return totalPowerBought;
	}

	/**
	 * Process a SellingPower FIPA-Request
	 */
	public Predicate visit(SellingPower sellingPower) {
		logger.debug("SellingPower received ...");
		psold = sellingPower.getSurplus().getSurplusValue();
		totalsold += psold;
		NotifierTotalPowerSold totalPowerSold = new NotifierTotalPowerSold(new PowerSold(psold));
		notifyToSubscribers(totalPowerSold);
		return totalPowerSold;
	}

	@Override
	protected void onCreateMBean(JmxResourceInfo resourceInfo, List<JmxAttributeFieldInfo> attributeFieldInfoList,
								 List<JmxAttributeMethodInfo> attributeMethodInfoList, List<JmxOperationInfo> operationInfoList) {
		
		resourceInfo.setJmxDomainName(AGENT_NAME);
		resourceInfo.setJmxBeanName(this.getAID().getLocalName());
		resourceInfo.setJmxDescription(AGENT_DESCRIPTION);
	}
	
	
	/**
	 * Tick Behaviour to periodically check budget
	 * @author BISITE
	 */
	class PeriodicallyCheckBudgetBehaviour extends TickerBehaviour {
		
		private static final long serialVersionUID = 1L;

		public PeriodicallyCheckBudgetBehaviour(Agent a, long period) {
			super(a, period);
		}
		
		@Override
		protected void onTick() {
			// money spent = (sold - bought) * price
			budget += (totalsold - totalbought) * price;
			// Notify current budget
			MarketAgent.this.notifyToSubscribers(new NotifierCurrentBudget(new CurrentBudget(budget)));
		}
	}
	
	
	/**
	 * Tick Behaviour to periodically update price
	 * @author BISITE
	 */
	class PeriodicallyUpdatePriceBehaviour extends TickerBehaviour {
		
		private static final long serialVersionUID = 1L;
		
		private List<Double> netPrices;
		private Integer tick = 0;
		
		public PeriodicallyUpdatePriceBehaviour(Agent a, long period, List<Double> netPrices) {
			super(a, period);
			this.netPrices = netPrices;
		}
		
		@Override
		protected void onTick() {
			if(tick >= netPrices.size()) tick = 0;
			price = netPrices.get(tick++);
			logger.debug("Price to notify -> " + price);
			// notify current price to subscribers.
			MarketAgent.this.notifyToSubscribers(new NotifierCurrentPrice(new CurrentPrice(price)));
		}
	}

	
}
