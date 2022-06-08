package alexeyby.corsys.repository.calculation;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.calculation.Mixture;

public interface MixtureRepository extends Repository<Mixture, Integer> 
{
	@Query(value = "SELECT mix FROM Mixture mix WHERE mix.name = :name")
	public Optional<Mixture> findByName(@Param("name") String name);
}