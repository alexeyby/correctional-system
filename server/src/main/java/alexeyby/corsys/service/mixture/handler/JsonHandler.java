package alexeyby.corsys.service.mixture.handler;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import alexeyby.corsys.exception.NotFoundException;
import alexeyby.corsys.exception.UnprocessableEntityException;
import alexeyby.corsys.model.Checkup;
import alexeyby.corsys.model.Patient;
import alexeyby.corsys.model.calculation.AgeRange;
import alexeyby.corsys.model.calculation.BMIDegreeMalnutrition;
import alexeyby.corsys.model.calculation.BMINutritionalStatus;
import alexeyby.corsys.model.calculation.LabDegreeMalnutrition;
import alexeyby.corsys.model.calculation.SFTDegreeMalnutrition;
import alexeyby.corsys.model.calculation.SMVDegreeMalnutrition;
import alexeyby.corsys.model.enumeration.DegreeMalnutrition;
import alexeyby.corsys.model.enumeration.Indicator;
import alexeyby.corsys.model.enumeration.Injury;
import alexeyby.corsys.model.enumeration.Mobility;
import alexeyby.corsys.model.enumeration.Sex;
import alexeyby.corsys.model.enumeration.Temperature;
import alexeyby.corsys.model.type.DegreeMalnutritionType;
import alexeyby.corsys.model.type.ProteinEnergyMalnutritionType;
import alexeyby.corsys.model.type.SexType;
import alexeyby.corsys.model.util.Calorie;
import alexeyby.corsys.model.util.MixtureJsonHelper;
import alexeyby.corsys.model.util.MixtureMapping;
import alexeyby.corsys.repository.CheckupRepository;
import alexeyby.corsys.repository.PatientRepository;
import alexeyby.corsys.repository.calculation.AgeRangeRepository;
import alexeyby.corsys.repository.calculation.AgeRangeRepositoryImpl;
import alexeyby.corsys.repository.calculation.BMIDegreeMalnutritionRepository;
import alexeyby.corsys.repository.calculation.BMINutritionalStatusRepository;
import alexeyby.corsys.repository.calculation.CorrectionFactorRepository;
import alexeyby.corsys.repository.calculation.CorrectionFactorRepositoryImpl;
import alexeyby.corsys.repository.calculation.LabDegreeMalnutritionRepository;
import alexeyby.corsys.repository.calculation.SFTDegreeMalnutritionRepositiory;
import alexeyby.corsys.repository.calculation.SMVDegreeMalnutritionRepository;
import alexeyby.corsys.repository.enumeration.DegreeMalnutritionRepository;
import alexeyby.corsys.repository.enumeration.DegreeMalnutritionRepositoryImpl;
import alexeyby.corsys.repository.enumeration.IndicatorRepository;
import alexeyby.corsys.service.mixture.handler.enteral.JsonEnteralHandler;

@Component
public class JsonHandler {
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private CheckupRepository checkupRepository;
	@Autowired
	private AgeRangeRepository ageRangeRepository = new AgeRangeRepositoryImpl();
	@Autowired
	private BMINutritionalStatusRepository bmiNutritionalStatusRepository;
	@Autowired
	private BMIDegreeMalnutritionRepository bmiDegreeMalnutritionRepository;
	@Autowired
	private SMVDegreeMalnutritionRepository smvDegreeMalnutritionRepository;
	@Autowired
	private SFTDegreeMalnutritionRepositiory sftDegreeMalnutritionRepository;
	@Autowired
	private IndicatorRepository indicatorRepository;
	@Autowired
	private LabDegreeMalnutritionRepository labDegreeMalnutritionRepository;
	@Autowired 
	private DegreeMalnutritionRepository degreeMalnutritionRepository = new DegreeMalnutritionRepositoryImpl();
	@Autowired 
	private CorrectionFactorRepository correctionFactorRepository = new CorrectionFactorRepositoryImpl();
	
	@Autowired
	JsonEnteralHandler jsonEnteralHandler;
	
	public JsonNode generateReportEnteral(UUID patientId) {
		ObjectNode report = (ObjectNode) generateReport(patientId);
		JsonNode enteral = generateEnteral(report);
		
		report.set("recommendations", enteral);	
        
        return report;
	}
	
	public JsonNode generateEnteral(JsonNode base) {
		JsonNode nutrientNeeds = base.get("calculations").get("nutrientNeeds");
		double protein = nutrientNeeds.get("protein").asDouble();
		double nonprotein = nutrientNeeds.get("nonprotein").get("number").asDouble();

		return jsonEnteralHandler.generateReport(protein, nonprotein);
	}
	
	public JsonNode generateReportPeptomen(UUID patientId) {
		return generateReport(patientId);
	}
	
	public JsonNode generateReport(UUID patientId) {
		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new NotFoundException("Patient does not exist with id: " + patientId));
		
        Checkup checkup = checkupRepository.findByPatientId(patientId)
        		.orElseThrow(() -> new NotFoundException("Checkup does not exist for patient with id: " + patientId));

        int age = Period.between(patient.getBirthdate().toLocalDate(), LocalDate.now()).getYears();
        double height = checkup.getHeight();
        double weight = checkup.getWeight();
        double armCircumference = checkup.getArmCircumference();
        double sft = checkup.getSkinfoldThickness();
        double smv = calculateSMV(armCircumference, sft);
        double albumin = checkup.getAlbumin();
        double transferrin = checkup.getTransferrin();
        double lymphocyte = checkup.getLymphocyte();
        double skinTest = checkup.getSkinTest();
        Sex sex = patient.getSex();
        String sx = sex.getSex();
        String healthFacility = checkup.getHealthFacility();
        boolean failure = patient.isFailure();
        Mobility mobility = checkup.getMobility();
        Temperature temperature = checkup.getTemperature();
        Injury injury = checkup.getInjury();
        
        ObjectMapper mapper = new ObjectMapper();
        
        ObjectNode report = mapper.createObjectNode();
        if (healthFacility.equals("")) {
        	 report.put("healthFacility", "-");
        } else {
        	 report.put("healthFacility", healthFacility);
        }
        report.put("historyNumber", patient.getHistoryNumber());

        ObjectNode personalData = mapper.createObjectNode();
        personalData.put("lastName", patient.getLastName());
        personalData.put("firstName", patient.getFirstName());
        personalData.put("surname", patient.getSurname());
        personalData.put("sex", MixtureMapping.SEX.get(sx));
        personalData.put("age", age);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM.dd.yyyy");
        personalData.put("checkupDate", dateFormat.format(checkup.getCheckupDate()));
        if (failure) {
            personalData.put("failure", "Да");
        } else {
        	personalData.put("failure", "Нет");
        }
        report.set("personalData", personalData);
        
        ObjectNode anthroData = mapper.createObjectNode();
        MixtureJsonHelper.putIntOrDouble(anthroData, "weight", weight);
        MixtureJsonHelper.putIntOrDouble(anthroData, "height", height);
        MixtureJsonHelper.putIntOrDouble(anthroData, "skinfoldThickness", sft);
        MixtureJsonHelper.putIntOrDouble(anthroData, "armCircumference", armCircumference);
        report.set("anthroData", anthroData);
        
        ObjectNode labData = mapper.createObjectNode();
        MixtureJsonHelper.putIntOrDouble(labData, "albumin", albumin);
        MixtureJsonHelper.putIntOrDouble(labData, "transferrin", transferrin);
        MixtureJsonHelper.putIntOrDouble(labData, "lymphocyte", lymphocyte);
        MixtureJsonHelper.putIntOrDouble(labData, "skinTest", skinTest);
        report.set("labData", labData);
        
        ObjectNode patientActualNeeds = mapper.createObjectNode();
        patientActualNeeds.put("mobility", MixtureMapping.MOBILITY.get(mobility.getMobility()));
        patientActualNeeds.put("temperature", MixtureMapping.TEMPERATURE.get(temperature.getTemperature()));
        patientActualNeeds.put("injury", MixtureMapping.INJURY.get(injury.getInjury()));
        report.set("patientActualNeeds", patientActualNeeds);
        
        ObjectNode calculations = mapper.createObjectNode();
        
        AgeRange bmiAgeRange = ageRangeRepository.findByAgeBMI(age)
        		.orElseThrow(() -> new UnprocessableEntityException("Age range does not exist for age: " + age));
        
        double bmi = calculateBMI(height, weight);
        
        BMINutritionalStatus bmiNutritionalStatus = bmiNutritionalStatusRepository.findByBmiAgeRange(bmi, bmiAgeRange)
        		.orElseThrow(() -> new UnprocessableEntityException("BMI nutritional status does not exist for bmi: " + bmi));
        calculations.put("BMINutritionalStatus", 
        		MixtureMapping.NUTRITIONAL_STATUS.get(bmiNutritionalStatus.getNutritionalStatus().getNutritionalStatus()));
        
        ObjectNode degreeMalnutrition = mapper.createObjectNode();
        
        BMIDegreeMalnutrition bmiDegreeMalnutrition = bmiDegreeMalnutritionRepository.findByBmiAgeRange(bmi, bmiAgeRange)
        		.orElseThrow(() -> new UnprocessableEntityException("BMI degree of malnutrition does not exist for bmi: " + bmi));
        String bmiDegMal = bmiDegreeMalnutrition.getDegreeMalnutrition().getDegreeMalnutrition();
        degreeMalnutrition.put("BMI", MixtureMapping.DEGREE_MALNUTRITION.get(bmiDegMal));
        
        SMVDegreeMalnutrition smvDegreeMalnutrition = smvDegreeMalnutritionRepository.findBySmvSex(smv, sex)
        		.orElseThrow(() -> new UnprocessableEntityException("SMV degree of malnutrition does not exist for smv: " + smv));
        String smvDegMal = smvDegreeMalnutrition.getDegreeMalnutrition().getDegreeMalnutrition();
        degreeMalnutrition.put("SMV", MixtureMapping.DEGREE_MALNUTRITION.get(smvDegMal));
        
        AgeRange sftAgeRange = ageRangeRepository.findByAgeSexSFT(age, sex)
        		.orElseThrow(() -> new UnprocessableEntityException("Age range does not exist for age: " + age));
        
        SFTDegreeMalnutrition sftDegreeMalnutrition = sftDegreeMalnutritionRepository.findBySftAgeRangeSex(sft, sftAgeRange, sex)
        		.orElseThrow(() -> new UnprocessableEntityException("SFT degree of malnutrition does not exist for sft: " + sft));
        String sftDegMal = sftDegreeMalnutrition.getDegreeMalnutrition().getDegreeMalnutrition();
        degreeMalnutrition.put("SFT", MixtureMapping.DEGREE_MALNUTRITION.get(sftDegMal));
        
        Indicator albuminIndicator = indicatorRepository.findAlbumin();
        LabDegreeMalnutrition albuminDegreeMalnutrition = labDegreeMalnutritionRepository
        		.findByResultIndicator(albumin, albuminIndicator)
        		.orElseThrow(() -> new UnprocessableEntityException("Albumin degree of malnutrition does not exist for albumin: " + albumin));
        String albuminDegMal = albuminDegreeMalnutrition.getDegreeMalnutrition().getDegreeMalnutrition();
        degreeMalnutrition.put("albumin", MixtureMapping.DEGREE_MALNUTRITION.get(albuminDegMal));
        
        Indicator transferrinIndicator = indicatorRepository.findTransferrin();
        LabDegreeMalnutrition transferrinDegreeMalnutrition = labDegreeMalnutritionRepository
        		.findByResultIndicator(transferrin, transferrinIndicator)
        		.orElseThrow(() -> new UnprocessableEntityException("Transferrin degree of malnutrition does not exist for albumin: " + transferrin));
        String transferrinDegMal = transferrinDegreeMalnutrition.getDegreeMalnutrition().getDegreeMalnutrition();
        degreeMalnutrition.put("transferrin", MixtureMapping.DEGREE_MALNUTRITION.get(transferrinDegMal));
        
        Indicator lymphocyteIndicator = indicatorRepository.findLymphocyte();
        LabDegreeMalnutrition lymphocyteDegreeMalnutrition = labDegreeMalnutritionRepository
        		.findByResultIndicator(lymphocyte, lymphocyteIndicator)
        		.orElseThrow(() -> new UnprocessableEntityException("Lymphocyte degree of malnutrition does not exist for albumin: " + lymphocyte));
        String lymphocyteDegMal = lymphocyteDegreeMalnutrition.getDegreeMalnutrition().getDegreeMalnutrition();
        degreeMalnutrition.put("lymphocyte", MixtureMapping.DEGREE_MALNUTRITION.get(lymphocyteDegMal));
        
        Indicator skinTestIndicator = indicatorRepository.findSkinTest();
        LabDegreeMalnutrition skinTestDegreeMalnutrition = labDegreeMalnutritionRepository
        		.findByResultIndicator(skinTest, skinTestIndicator)
        		.orElseThrow(() -> new UnprocessableEntityException("Skin test degree of malnutrition does not exist for albumin: " + skinTest));
        String skinTestDegMal = skinTestDegreeMalnutrition.getDegreeMalnutrition().getDegreeMalnutrition();
        degreeMalnutrition.put("skinTest", MixtureMapping.DEGREE_MALNUTRITION.get(skinTestDegMal));
        
        calculations.set("degreeMalnutrition", degreeMalnutrition);
        
        ObjectNode totalValue = mapper.createObjectNode();
        
        DegreeMalnutrition totalDegreeMalnutrition = getTotalDegreeMalnutrition(
        bmiDegMal, smvDegMal, sftDegMal, albuminDegMal, transferrinDegMal, lymphocyteDegMal, skinTestDegMal	
        );
        totalValue.put("totalDegreeMalnutrition", MixtureMapping.DEGREE_MALNUTRITION.get(totalDegreeMalnutrition.getDegreeMalnutrition()));
        
        ProteinEnergyMalnutritionType proteinEnergyMalnutritionType = getProteinEnergyMalnutrition(
    	bmiDegMal, smvDegMal, sftDegMal, albuminDegMal, transferrinDegMal, skinTestDegMal	
    	);
        totalValue.put("proteinEnergyMalnutrition", MixtureMapping.PROTEIN_ENERGY_MALNUTRITION.get(proteinEnergyMalnutritionType.toString()));
        
        calculations.set("totalValue", totalValue);
        
        ObjectNode nutrientNeeds = mapper.createObjectNode();
        
        Calorie calorie = calculateNutrientNeeds(injury, temperature, mobility, bmiDegMal, sx, 
        weight, height, age, failure);
        
        nutrientNeeds.put("protein", calorie.getProtein());
        
        ObjectNode nonprotein = mapper.createObjectNode();
        int carbs = calorie.getCarbs();
        int fat = calorie.getFat();
        int nonprot = carbs + fat;
        nonprotein.put("number", nonprot);
        nonprotein.put("carbs", carbs);
        nonprotein.put("fat", fat);
        nutrientNeeds.set("nonprotein", nonprotein);
        
        calculations.set("nutrientNeeds", nutrientNeeds);
        
        report.set("calculations", calculations);
        
        return report;
	}
	
	private double calculateSMV(double armCircumference, double sft) {
		return armCircumference - 3.14 * sft;
	}
	
	private double calculateBMI(double height, double weight) {
		double metersHeight = height / 100.0;
		return weight / (metersHeight * metersHeight);
	}
	
	private DegreeMalnutrition getTotalDegreeMalnutrition(
	String bmiDegreeMalnutrition, 
	String smvDegreeMalnutrition, 
	String sftDegreeMalnutrition, 
	String albuminDegreeMalnutrition, 
	String transferrinDegreeMalnutrition, 
	String lymphocyteDegreeMalnutrition, 
	String skinTestDegreeMalnutrition
	) {
		int pointsNumber = 21;
		String[] degreesMalnutrition = new String[] { bmiDegreeMalnutrition, smvDegreeMalnutrition, sftDegreeMalnutrition,
		albuminDegreeMalnutrition, transferrinDegreeMalnutrition, lymphocyteDegreeMalnutrition, skinTestDegreeMalnutrition };
		
		for (String degreeMalnutrition : degreesMalnutrition) {
			switch(DegreeMalnutritionType.valueOf(degreeMalnutrition)) {
			   case MILD:
				   pointsNumber--;
				   break;
			   case MODERATE:
				   pointsNumber -= 2;
				   break;
			   case SEVERE:
				   pointsNumber -= 3;
				   break;
			   default:
				   break;
			}
		}
		DegreeMalnutritionType degreeMalnutritionType = DegreeMalnutritionType.NORMAL;
		if (14 <= pointsNumber && pointsNumber < 21) {
			degreeMalnutritionType = DegreeMalnutritionType.MILD;
		}
		if (7 <= pointsNumber && pointsNumber < 14) {
			degreeMalnutritionType = DegreeMalnutritionType.MODERATE;
		}
		if (0 <= pointsNumber && pointsNumber < 7) {
			degreeMalnutritionType = DegreeMalnutritionType.SEVERE;
		}
		
		return degreeMalnutritionRepository.findByDegreeMalnutrition(degreeMalnutritionType);
	}
	
	private ProteinEnergyMalnutritionType getProteinEnergyMalnutrition(
	String bmiDegreeMalnutrition, 
	String smvDegreeMalnutrition, 
	String sftDegreeMalnutrition, 
	String albuminDegreeMalnutrition, 
	String transferrinDegreeMalnutrition, 
	String skinTestDegreeMalnutrition
	) {
		ProteinEnergyMalnutritionType proteinEnergyMalnutritionType = ProteinEnergyMalnutritionType.NORMAL;
		String[] marasmusDegreesMalnutrition = new String[] { bmiDegreeMalnutrition, smvDegreeMalnutrition, sftDegreeMalnutrition, 
		skinTestDegreeMalnutrition };
		String[] kwashiorkorDegreesMalnutrition = new String[] { albuminDegreeMalnutrition, transferrinDegreeMalnutrition };
		
		for (String degreeMalnutrition : marasmusDegreesMalnutrition) {
			if (DegreeMalnutritionType.valueOf(degreeMalnutrition) != DegreeMalnutritionType.NORMAL) {
				proteinEnergyMalnutritionType = ProteinEnergyMalnutritionType.MARASMUS; 
				break;
			}
		}
		for (String degreeMalnutrition : kwashiorkorDegreesMalnutrition) {
			if (DegreeMalnutritionType.valueOf(degreeMalnutrition) != DegreeMalnutritionType.NORMAL) {
				if (proteinEnergyMalnutritionType == ProteinEnergyMalnutritionType.MARASMUS) {
					proteinEnergyMalnutritionType = ProteinEnergyMalnutritionType.MARASMUS_KWASHIORKOR;
				} else {
					proteinEnergyMalnutritionType = ProteinEnergyMalnutritionType.KWASHIORKOR;
				}
				break;
			}
		}
		
		return proteinEnergyMalnutritionType;	
	}
	
	private Calorie calculateNutrientNeeds(Injury injury, Temperature temperature, Mobility mobility, 
	String degreeMalnutrition, String sex, double weight, double height, int age, boolean failure) {
		double calories = calculateActualEnergyConsumption(injury, temperature, mobility, 
		degreeMalnutrition, sex, weight, height, age);
		double fat = failure ? 145 : 425;
		double carbs = calories / fat;
		double protein = 25 * carbs;
		double nonprotein = calories - protein;
		
		fat = 0.3 * nonprotein;
		carbs = 0.7 * nonprotein;
		
		return new Calorie((int) Math.round(protein), (int) Math.round(carbs), (int) Math.round(fat));
	}
	
	private double calculateActualEnergyConsumption(Injury injury, Temperature temperature, Mobility mobility, 
	String degreeMalnutrition, String sex, double weight, double height, int age) {
		double basalMetabolicRate = calculateBasalMetabolicRate(sex, weight, height, age);
		DegreeMalnutrition degMal = 
		degreeMalnutritionRepository.findByDegreeMalnutrition(DegreeMalnutritionType.valueOf(degreeMalnutrition));
		
		double injuryFactor = correctionFactorRepository.getValueByInjury(injury);
		double temperatureFactor = correctionFactorRepository.getValueByTemperature(temperature);
		double mobilityFactor = correctionFactorRepository.getValueByMobility(mobility);
		double degreeMalnutritionFactor = correctionFactorRepository.getValueByDegreeMalnutrition(degMal);
		
		return basalMetabolicRate * injuryFactor * temperatureFactor * mobilityFactor * degreeMalnutritionFactor;
	}
	
	private double calculateBasalMetabolicRate(String sex, double weight, double height, int age) {
		double basalMetabolicRate = 0.0;
		switch(SexType.valueOf(sex)) {
		   case MALE:
			   basalMetabolicRate = 66.47 + 13.75 * weight + 5.0 * height - 6.76 * (double) age;
			   break;
		   case FEMALE:
			   basalMetabolicRate = 665.1 + 9.65 * weight + 1.85 * height - 4.68 * (double) age;
			   break;
		}
		return basalMetabolicRate;
	}
}
