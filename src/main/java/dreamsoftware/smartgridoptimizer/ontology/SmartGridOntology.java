package dreamsoftware.smartgridoptimizer.ontology;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.AgentActionSchema;
import jade.content.schema.ConceptSchema;
import jade.content.schema.ObjectSchema;
import jade.content.schema.PredicateSchema;
import jade.content.schema.PrimitiveSchema;
import dreamsoftware.smartgridoptimizer.ontology.actions.ChangePowerConsumptionMeasures;
import dreamsoftware.smartgridoptimizer.ontology.concepts.BatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.concepts.CurrentBudget;
import dreamsoftware.smartgridoptimizer.ontology.concepts.CurrentPrice;
import dreamsoftware.smartgridoptimizer.ontology.concepts.LoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerBought;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerGenerated;
import dreamsoftware.smartgridoptimizer.ontology.concepts.PowerSold;
import dreamsoftware.smartgridoptimizer.ontology.concepts.Surplus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.BuyPower;
import dreamsoftware.smartgridoptimizer.ontology.predicates.CurrentBatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.predicates.CurrentConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GenerateEnergy;
import dreamsoftware.smartgridoptimizer.ontology.predicates.GetStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.IterationStatus;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierBatteryLevel;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierCurrentBudget;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierCurrentPrice;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierLoadConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierTotalPowerBought;
import dreamsoftware.smartgridoptimizer.ontology.predicates.NotifierTotalPowerSold;
import dreamsoftware.smartgridoptimizer.ontology.predicates.PerformConsumption;
import dreamsoftware.smartgridoptimizer.ontology.predicates.PowerCurrentlyGenerated;
import dreamsoftware.smartgridoptimizer.ontology.predicates.RetrieveDemand;
import dreamsoftware.smartgridoptimizer.ontology.predicates.SellingPower;
import dreamsoftware.smartgridoptimizer.ontology.predicates.StoreInBattery;
import dreamsoftware.smartgridoptimizer.ontology.predicates.UpdateLoad;

import java.io.Serial;

public final class SmartGridOntology extends Ontology implements ISmartGridVocabulary {
	
	@Serial
	private static final long serialVersionUID = 1L;
	public static final String ONTOLOGY_NAME = "SmartGrid Ontology";
	
	private static Ontology instance = null;

	private SmartGridOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		
		try {	
			
			// ------- Add Concepts
			
			// Load consumption Concept Schema
			ConceptSchema lccs = new ConceptSchema(LOAD_CONSUMPTION);
			lccs.add(LOAD_CONSUMPTION_VALUE, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			
			add(lccs, LoadConsumption.class);
			
			// Power Generated Concept Schema
			ConceptSchema pgcs = new ConceptSchema(POWER_GENERATED);
			pgcs.add(POWER_GENERATED_VALUE, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
						
			add(pgcs, PowerGenerated.class);
			
			// Surplus Concept Schema
			ConceptSchema spcs = new ConceptSchema(SURPLUS);
			spcs.add(SURPLUS_VALUE, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
						
			add(spcs, Surplus.class);
			
			//Battery Level Concept Schema
			ConceptSchema blcs = new ConceptSchema(BATTERY_LEVEL);
			blcs.add(BATTERY_LEVEL_VALUE, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			
			add(blcs, BatteryLevel.class);
			
			//Power Bought Concept Schema
			ConceptSchema pbcs = new ConceptSchema(POWER_BOUGHT);
			pbcs.add(POWER_BOUGHT_VALUE,  (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			
			add(pbcs, PowerBought.class);
			
			//Power Sold Concept Schema
			ConceptSchema pscs = new ConceptSchema(POWER_SOLD);
			pscs.add(POWER_SOLD_VALUE, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			
			add(pscs, PowerSold.class);
			
			//Current Price Concept Schema
			ConceptSchema cpcs = new ConceptSchema(CURRENT_PRICE);
			cpcs.add(CURRENT_PRICE_VALUE, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			
			add(cpcs, CurrentPrice.class);
			
			// Current Budget Concept Schema
			ConceptSchema cbcs = new ConceptSchema(CURRENT_BUDGET);
			cbcs.add(CURRENT_BUDGET_VALUE, (PrimitiveSchema) getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			
			add(cbcs, CurrentBudget.class);
			
			// ------- Add Predicates
			
			// Perform Consumption Predicate Schema
			PredicateSchema pcps = new PredicateSchema(PERFORM_CONSUMPTION);
			pcps.add(AGENT_AID, (ConceptSchema) getSchema(BasicOntology.AID), ObjectSchema.MANDATORY);
			pcps.add(LOAD_CONSUMPTION, lccs, ObjectSchema.MANDATORY);
			
			add(pcps, PerformConsumption.class);
			
			// Current Consumption for a PowerLoadAgent Schema
			PredicateSchema ccps = new PredicateSchema(CURRENT_CONSUMPTION);
			ccps.add(AGENT_AID, (ConceptSchema) getSchema(BasicOntology.AID), ObjectSchema.MANDATORY);
			ccps.add(LOAD_CONSUMPTION, lccs, ObjectSchema.MANDATORY);
			
			add(ccps, CurrentConsumption.class);
		
			
			//Generate Energy Predicate Schema
			PredicateSchema geps = new PredicateSchema(GENERATE_ENERGY);
			geps.add(AGENT_AID, (ConceptSchema) getSchema(BasicOntology.AID), ObjectSchema.MANDATORY);
			geps.add(POWER_GENERATED, pgcs, ObjectSchema.MANDATORY);
			
			add(geps, GenerateEnergy.class);
			
			// Power Currently Generated.
			PredicateSchema pcgps = new PredicateSchema(POWER_CURRENTLY_GENERATED);
			pcgps.add(AGENT_AID, (ConceptSchema) getSchema(BasicOntology.AID), ObjectSchema.MANDATORY);
			pcgps.add(POWER_GENERATED, pgcs, ObjectSchema.MANDATORY);
			
			add(pcgps, PowerCurrentlyGenerated.class);
			
			//Store surplus in battery Predicate Schema
			PredicateSchema ssps = new PredicateSchema(STORE_IN_BATTERY);
			ssps.add(SURPLUS, spcs);
			
			add(ssps, StoreInBattery.class);
			
			//Retrieve surplus from battery Predicate Schema
			PredicateSchema rbps = new PredicateSchema(RETRIEVE_DEMAND);
			rbps.add(SURPLUS, spcs);
			
			add(rbps, RetrieveDemand.class);
			
			//Notifier Battery level Predicate Schema
			PredicateSchema nblps = new PredicateSchema(NOTIFIER_BATTERY_LEVEL);
			nblps.add(AGENT_AID, (ConceptSchema) getSchema(BasicOntology.AID), ObjectSchema.MANDATORY);
			nblps.add(BATTERY_LEVEL, blcs, ObjectSchema.MANDATORY);
			
			add(nblps, NotifierBatteryLevel.class);
			
			// Current Battery Level Predicate Schema
			PredicateSchema cblps = new PredicateSchema(CURRENT_BATTERY_LEVEL);
			cblps.add(AGENT_AID, (ConceptSchema) getSchema(BasicOntology.AID), ObjectSchema.MANDATORY);
			cblps.add(BATTERY_LEVEL, blcs, ObjectSchema.MANDATORY);
			
			add(cblps, CurrentBatteryLevel.class);
			
			//Selling Power Predicate Schema
			PredicateSchema spps = new PredicateSchema(SELLING_POWER);
			spps.add(SURPLUS, spcs);
			
			add(spps, SellingPower.class);
			
			//Buy Power Predicate Schema
			PredicateSchema bpps = new PredicateSchema(BUY_POWER);
			bpps.add(SURPLUS, spcs);
			
			add(bpps, BuyPower.class);
			
			//Update Load Predicate Schema
			PredicateSchema ulps = new PredicateSchema(UPDATE_LOAD);
			ulps.add(LOAD_CONSUMPTION, lccs);
			
			add(ulps, UpdateLoad.class);
			
			//Notifier Power Bought Predicate Schema
			PredicateSchema ntpbps = new PredicateSchema(NOTIFIER_POWER_BOUGHT);
			ntpbps.add(POWER_BOUGHT, pbcs);
			
			add(ntpbps, NotifierTotalPowerBought.class);
			
			//Notifier Power Sold Predicate Schema.
			PredicateSchema ntpsps = new PredicateSchema(NOTIFIER_POWER_SOLD);
			ntpsps.add(POWER_SOLD, pscs);
			
			add(ntpsps, NotifierTotalPowerSold.class);
			
			//Notifier Load Consumption Predicate Schema
			PredicateSchema nlcps = new PredicateSchema(NOTIFIER_LOAD_CONSUMPTION);
			nlcps.add(LOAD_CONSUMPTION, lccs, ObjectSchema.MANDATORY);
			
			add(nlcps, NotifierLoadConsumption.class);
			
			//Iteration Status Predicate Schema
			PredicateSchema isps = new PredicateSchema(ITERATION_STATUS);
			isps.add(POWER_GENERATED_VALUE, getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			isps.add(LOAD_CONSUMPTION_VALUE, getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			isps.add(SURPLUS_VALUE, getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			isps.add(BATTERY_LEVEL_VALUE, getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			isps.add(POWER_SOLD_VALUE, getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			isps.add(POWER_BOUGHT_VALUE, getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			isps.add(ADJUST_LOAD_VALUE, getSchema(BasicOntology.FLOAT), ObjectSchema.MANDATORY);
			
			add(isps, IterationStatus.class);
			
			// Get Status Predicate Schema
			PredicateSchema gsps = new PredicateSchema(GET_STATUS);
			add(gsps, GetStatus.class);
			
			// Notifier Current Price Predicate Schema
			PredicateSchema ncpps = new PredicateSchema(NOTIFIER_CURRENT_PRICE);
			ncpps.add(CURRENT_PRICE, cpcs);
			
			add(ncpps, NotifierCurrentPrice.class);
			
			// Notifier Current Budget Predicate Schema
			PredicateSchema ncbps = new PredicateSchema(NOTIFIER_CURRENT_BUDGET);
			ncbps.add(CURRENT_BUDGET, cbcs);
			
			add(ncbps, NotifierCurrentBudget.class);
			// Agent Actions
			AgentActionSchema cpcms = new AgentActionSchema(CHANGE_POWER_CONSUMPTION_MEASURES);
			cpcms.add(POWER_CONSUMPTION_MEASURES, lccs, ObjectSchema.UNLIMITED);
			add(cpcms, ChangePowerConsumptionMeasures.class);
		} catch (OntologyException e) {
			e.printStackTrace();
		}
	}

	public static Ontology getInstance() {
		if(instance == null)
			instance = new SmartGridOntology();
	     return instance;
	 }
}
