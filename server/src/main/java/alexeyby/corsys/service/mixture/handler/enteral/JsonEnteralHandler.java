package alexeyby.corsys.service.mixture.handler.enteral;

import java.util.Iterator;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import org.apache.commons.math3.util.Precision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import alexeyby.corsys.model.calculation.Mixture;
import alexeyby.corsys.model.type.MixtureType;
import alexeyby.corsys.model.util.MixtureJsonHelper;
import alexeyby.corsys.repository.calculation.MixtureRepository;

@Component
public class JsonEnteralHandler {
	@Autowired
	private MixtureRepository mixtureRepository;
	
	private double maxMl;
	private double proteinIn1000Ml;
	private double kcaloriesIn1000Ml;
	private JsonNode ingredients;
	
	@PostConstruct
	public void init() {      
		try {
	        Mixture mixture = mixtureRepository.findByName(MixtureType.ENTERAL.toString()).orElseThrow();
	        
	        ObjectMapper mapper = new ObjectMapper();
	        
			JsonNode data = mapper.readTree(mixture.getData());
			
			maxMl = data.get("maxMl").asDouble();
			proteinIn1000Ml = data.get("proteinIn1000Ml").asDouble();
			kcaloriesIn1000Ml = data.get("kcaloriesIn1000Ml").asDouble();
			ingredients = data.get("ingredients");
		} 
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public JsonNode generateReport(double protein, double nonprotein) {
		ObjectMapper mapper = new ObjectMapper();
		
		ObjectNode report = mapper.createObjectNode();
		
		double enteral = kcaloriesToEnteral(protein, nonprotein);
		report.put("enteral", (int) Math.round(enteral));
		
		ObjectNode nutrientNeeds = mapper.createObjectNode();
		
		Pair<Double, Double> calories = enteralToKcalories(enteral);
		double calculatedProtein = calories.getFirst();
		if (calculatedProtein >= protein) {
			nutrientNeeds.put("protein", 0.0);
		} else {
			nutrientNeeds.put("protein", (int) Math.round(protein - calculatedProtein));
		}
		
		ObjectNode nonprot = mapper.createObjectNode();
				
		double calculatedNonprotein = calories.getSecond();
		if (calculatedNonprotein >= nonprotein) {
			nonprot.put("number", 0.0);
			nonprot.put("carbs", 0.0);
			nonprot.put("fat", 0.0);
		} else {
			calculatedNonprotein = nonprotein - calculatedNonprotein;
			nonprot.put("number", (int) Math.round(calculatedNonprotein));
			nonprot.put("carbs", (int) Math.round(0.7 * calculatedNonprotein));
			nonprot.put("fat", (int) Math.round(0.3 * calculatedNonprotein));
		}
		
		nutrientNeeds.set("nonprotein", nonprot);
		
		report.set("nutrientNeeds", nutrientNeeds);
		
		ObjectNode nutrients = mapper.createObjectNode();
		
		ObjectNode aminoAcids = mapper.createObjectNode();
		
		Iterator<Entry<String, JsonNode>> aminoAcidsIt = ingredients.get("aminoAcids").fields();
		while (aminoAcidsIt.hasNext()) {
			Entry<String, JsonNode> aminoAcid = aminoAcidsIt.next();
			JsonNode value = aminoAcid.getValue();
			
			ObjectNode amAc = mapper.createObjectNode();
			
			MixtureJsonHelper.putIntOrDouble(amAc, "amount", roundValue(countNutrient(value.get("amount").asDouble(), enteral)));
			MixtureJsonHelper.putIntOrDouble(amAc, "percentage", roundValue(countNutrient(value.get("percentage").asDouble(), enteral)));
			
			aminoAcids.set(aminoAcid.getKey(), amAc);
		}		
		
		nutrients.set("aminoAcids", aminoAcids);
		
		ObjectNode vitamins = mapper.createObjectNode();
		
		Iterator<Entry<String, JsonNode>> vitaminsIt = ingredients.get("vitamins").fields();
		while (vitaminsIt.hasNext()) {
			Entry<String, JsonNode> vitamin = vitaminsIt.next();
			JsonNode value = vitamin.getValue();
			
			ObjectNode vit = mapper.createObjectNode();
			
			MixtureJsonHelper.putIntOrDouble(vit, "amount", roundValue(countNutrient(value.get("amount").asDouble(), enteral)));
			MixtureJsonHelper.putIntOrDouble(vit, "percentage", roundValue(countNutrient(value.get("percentage").asDouble(), enteral)));
			
			vitamins.set(vitamin.getKey(), vit);
		}		
		
		nutrients.set("vitamins", vitamins);
		
		report.set("nutrients", nutrients);
		
		return report;
	}
	
	private double roundValue(double value) {
		return Precision.round(value, 3);
	}
	
	private double countNutrient(double value, double enteral) {
		return ((value / 1000.0) * enteral);
	}
	
	private double kcaloriesToEnteral(double protein, double nonprotein) {
	    double proteinMl = (proteinIn1000Ml * 4.0) / 1000.0;
	    double nonproteinMl = (kcaloriesIn1000Ml - (proteinIn1000Ml * 4.0)) / 1000.0;

	    proteinMl = protein / proteinMl;
	    nonproteinMl = nonprotein / nonproteinMl;
	    
	    if (nonproteinMl < proteinMl) {
	    	proteinMl = nonproteinMl;
	    }

	    if (proteinMl > maxMl) {
	        proteinMl = maxMl;
	    }
	    
	    return proteinMl;
	}
	
	private Pair<Double, Double> enteralToKcalories(double enteral) {
	    enteral /= 1000.0;
	    
	    double protein = (proteinIn1000Ml * 4.0) * enteral;
	    double nonprotein = (kcaloriesIn1000Ml - (proteinIn1000Ml * 4.0)) * enteral;
	    
	    return Pair.of(protein, nonprotein);
	}
}
