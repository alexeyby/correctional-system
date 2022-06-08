package alexeyby.corsys.repository.calculation;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.calculation.SMVDegreeMalnutrition;
import alexeyby.corsys.model.enumeration.Sex;

public interface SMVDegreeMalnutritionRepository extends Repository<SMVDegreeMalnutrition, Integer>
{
	@Query(value = "SELECT smvdm FROM SMVDegreeMalnutrition smvdm WHERE :smv >= smvdm.smvLow AND :smv < smvdm.smvHigh " +
	"AND smvdm.sex = :sex")
	public Optional<SMVDegreeMalnutrition> findBySmvSex(@Param("smv") double smv, @Param("sex") Sex sex);
}