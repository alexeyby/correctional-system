package alexeyby.corsys.repository.calculation;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.calculation.AgeRange;
import alexeyby.corsys.model.calculation.BMIDegreeMalnutrition;

public interface BMIDegreeMalnutritionRepository  extends Repository<BMIDegreeMalnutrition, Integer> 
{
	@Query(value = "SELECT bmidm FROM BMIDegreeMalnutrition bmidm WHERE :bmi >= bmidm.bmiLow AND :bmi < bmidm.bmiHigh " +
	"AND bmidm.ageRange = :ageRange")
	public Optional<BMIDegreeMalnutrition> findByBmiAgeRange(@Param("bmi") double bmi, @Param("ageRange") AgeRange ageRange);
}
