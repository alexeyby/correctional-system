package alexeyby.corsys.repository.calculation;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.calculation.LabDegreeMalnutrition;
import alexeyby.corsys.model.enumeration.Indicator;

public interface LabDegreeMalnutritionRepository extends Repository<LabDegreeMalnutrition, Integer>
{
	@Query(value = "SELECT labdm FROM LabDegreeMalnutrition labdm WHERE :result >= labdm.resultLow AND :result < labdm.resultHigh " +
	"AND labdm.indicator = :indicator")
	public Optional<LabDegreeMalnutrition> findByResultIndicator(@Param("result") double result, @Param("indicator") Indicator indicator);
}
