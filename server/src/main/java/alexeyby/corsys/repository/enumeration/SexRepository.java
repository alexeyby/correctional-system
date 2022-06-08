package alexeyby.corsys.repository.enumeration;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.enumeration.Sex;

public interface SexRepository extends Repository<Sex, Integer> 
{
	@Query(value = "SELECT sx FROM Sex sx WHERE sx.sex = :sex")
	public Optional<Sex> findBySex(@Param("sex") String sex);
}