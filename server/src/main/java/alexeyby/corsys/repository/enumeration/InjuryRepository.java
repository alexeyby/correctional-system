package alexeyby.corsys.repository.enumeration;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.enumeration.Injury;

public interface InjuryRepository extends Repository<Injury, Integer> 
{
	@Query(value = "SELECT inj FROM Injury inj WHERE inj.injury = :injury")
	public Optional<Injury> findByInjury(@Param("injury") String injury);
}