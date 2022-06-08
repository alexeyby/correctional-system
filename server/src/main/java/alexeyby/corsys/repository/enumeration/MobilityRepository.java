package alexeyby.corsys.repository.enumeration;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.enumeration.Mobility;

public interface MobilityRepository extends Repository<Mobility, Integer> 
{
	@Query(value = "SELECT mob FROM Mobility mob WHERE mob.mobility = :mobility")
	public Optional<Mobility> findByMobility(@Param("mobility") String mobility);
}