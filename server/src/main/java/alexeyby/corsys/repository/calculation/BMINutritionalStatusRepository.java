package alexeyby.corsys.repository.calculation;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.calculation.AgeRange;
import alexeyby.corsys.model.calculation.BMINutritionalStatus;

public interface BMINutritionalStatusRepository extends Repository<BMINutritionalStatus, Integer> 
{
	@Query(value = "SELECT bmins FROM BMINutritionalStatus bmins WHERE :bmi >= bmins.bmiLow AND :bmi < bmins.bmiHigh " +
	"AND bmins.ageRange = :ageRange")
	public Optional<BMINutritionalStatus> findByBmiAgeRange(@Param("bmi") double bmi, @Param("ageRange") AgeRange ageRange);
}
