package alexeyby.corsys.repository.calculation;

import java.util.Optional;
import org.springframework.data.repository.Repository;
import alexeyby.corsys.model.calculation.AgeRange;
import alexeyby.corsys.model.enumeration.Sex;

public interface AgeRangeRepository extends Repository<AgeRange, Integer>
{
	public Optional<AgeRange> findByAgeBMI(int age);
	public Optional<AgeRange> findByAgeSexSFT(int age, Sex sex);
}