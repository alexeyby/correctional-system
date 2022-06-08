package alexeyby.corsys.repository.calculation;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.calculation.AgeRange;
import alexeyby.corsys.model.calculation.SFTDegreeMalnutrition;
import alexeyby.corsys.model.enumeration.Sex;

public interface SFTDegreeMalnutritionRepositiory extends Repository<SFTDegreeMalnutrition, Integer>
{
	@Query(value = "SELECT sftdm FROM SFTDegreeMalnutrition sftdm WHERE :sft >= sftdm.sftLow AND :sft < sftdm.sftHigh " +
	"AND sftdm.ageRange = :ageRange AND sftdm.sex = :sex")
	public Optional<SFTDegreeMalnutrition> findBySftAgeRangeSex(@Param("sft") double smv, @Param("ageRange") AgeRange ageRange, @Param("sex") Sex sex);
}
