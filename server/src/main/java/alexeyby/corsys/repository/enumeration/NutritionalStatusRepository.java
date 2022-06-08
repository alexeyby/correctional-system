package alexeyby.corsys.repository.enumeration;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.enumeration.NutritionalStatus;

public interface NutritionalStatusRepository extends Repository<NutritionalStatus, Integer> 
{
	@Query(value = "SELECT nsts FROM NutritionalStatus nsts WHERE nsts.nutritionalStatus = :nutritionalStatus")
	public Optional<NutritionalStatus> findByInjury(@Param("nutritionalStatus") String nutritionalStatus);
}
