package alexeyby.corsys.repository.enumeration;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.enumeration.Temperature;

public interface TemperatureRepository extends Repository<Temperature, Integer> 
{
	@Query(value = "SELECT temp FROM Temperature temp WHERE temp.temperature = :temperature")
	public Optional<Temperature> findByTemperature(@Param("temperature") String temperature);
}