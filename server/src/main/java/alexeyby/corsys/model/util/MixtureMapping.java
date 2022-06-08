package alexeyby.corsys.model.util;

import java.util.Map;
import alexeyby.corsys.model.type.DegreeMalnutritionType;
import alexeyby.corsys.model.type.InjuryType;
import alexeyby.corsys.model.type.MobilityType;
import alexeyby.corsys.model.type.NutritionalStatusType;
import alexeyby.corsys.model.type.ProteinEnergyMalnutritionType;
import alexeyby.corsys.model.type.SexType;
import alexeyby.corsys.model.type.TemperatureType;
import static java.util.Map.entry;

public abstract class MixtureMapping {
	public static final Map<String, String> SEX = Map.ofEntries(
			entry(SexType.MALE.toString(), "Мужской"),
			entry(SexType.FEMALE.toString(), "Женский")
    );
	
	public static final Map<String, String> MOBILITY = Map.ofEntries(
			entry(MobilityType.BEDREST.toString(), "Постельный режим"),
			entry(MobilityType.AMBULATION.toString(), "Палатный режим"),
			entry(MobilityType.NO_RESTRICTION.toString(), "Общий режим")
    );
	
	public static final Map<String, String> TEMPERATURE = Map.ofEntries(
			entry(TemperatureType.NORMAL.toString(), "Менее 38°С"),
			entry(TemperatureType.SLIGHTLY_HIGH.toString(), "38°С - 38,9°"),
			entry(TemperatureType.HIGH.toString(), "39°С - 39,9°С"),
			entry(TemperatureType.VERY_HIGH.toString(), "40°С - 40,9°С"),
			entry(TemperatureType.EXTREMELY_HIGH.toString(), "41°С и выше")
    );
	
	public static final Map<String, String> INJURY = Map.ofEntries(
			entry(InjuryType.NO_INJURIES.toString(), "Пациент без осложнений"),
			entry(InjuryType.AFTER_SURGERY.toString(), "После операции"),
			entry(InjuryType.BONE_FRACTURE.toString(), "Перелом"),
			entry(InjuryType.SEPSIS.toString(), "Сепсис"),
			entry(InjuryType.PERITONITIS.toString(), "Перитонит"),
			entry(InjuryType.POLYTRAUMA_REHABILITATION.toString(), "Политравма, реабилитация"),
			entry(InjuryType.POLYTRAUMA_SEPSIS.toString(), "Политравма и сепсис"),
			entry(InjuryType.MODERATE_BURNS.toString(), "Ожоги 30 - 50%"),
			entry(InjuryType.SEVERE_BURNS.toString(), "Ожоги 50 - 70%"),
			entry(InjuryType.VERY_SEVERE_BURNS.toString(), "Ожоги 70 - 90%")
    );
	
	public static final Map<String, String> NUTRITIONAL_STATUS = Map.ofEntries(
			entry(NutritionalStatusType.NORMAL.toString(), "Норма"),
			entry(NutritionalStatusType.OVERWEIGHT.toString(), "Повышенное питание"),
			entry(NutritionalStatusType.OBESITY_CLASS_ONE.toString(), "Ожирение 1 степени"),
			entry(NutritionalStatusType.OBESITY_CLASS_TWO.toString(), "Ожирение 2 степени"),
			entry(NutritionalStatusType.OBESITY_CLASS_THREE.toString(), "Ожирение 3 степени"),
			entry(NutritionalStatusType.OBESITY_CLASS_FOUR.toString(), "Ожирение 4 степени"),
			entry(NutritionalStatusType.UNDERWEIGHT.toString(), "Пониженное питание"),
			entry(NutritionalStatusType.HYPOTROPHY_CLASS_ONE.toString(), "Гипотрофия 1 степени"),
			entry(NutritionalStatusType.HYPOTROPHY_CLASS_TWO.toString(), "Гипотрофия 2 степени"),
			entry(NutritionalStatusType.HYPOTROPHY_CLASS_THREE.toString(), "Гипотрофия 3 степени")
    );
	
	public static final Map<String, String> DEGREE_MALNUTRITION = Map.ofEntries(
			entry(DegreeMalnutritionType.NORMAL.toString(), "Норма"),
			entry(DegreeMalnutritionType.MILD.toString(), "Легкие нарушения"),
			entry(DegreeMalnutritionType.MODERATE.toString(), "Средние нарушения"),
			entry(DegreeMalnutritionType.SEVERE.toString(), "Тяжелые нарушения")
    );
	
	public static final Map<String, String> PROTEIN_ENERGY_MALNUTRITION = Map.ofEntries(
			entry(ProteinEnergyMalnutritionType.NORMAL.toString(), "Норма"),
			entry(ProteinEnergyMalnutritionType.MARASMUS.toString(), "Дефицит массы мышечного белка на фоне отсутствия дефицитов висцеральных белков (маразм)"),
			entry(ProteinEnergyMalnutritionType.KWASHIORKOR.toString(), "Дефицит висцеральных белков при отсутствии снижения мышечных белков (квашиоркор)"),
			entry(ProteinEnergyMalnutritionType.MARASMUS_KWASHIORKOR.toString(), "Дефицит как мышечных, так и висцеральных белков (маразм и квашиоркор)")
    );
}
