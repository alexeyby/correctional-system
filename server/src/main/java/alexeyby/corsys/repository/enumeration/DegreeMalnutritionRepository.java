package alexeyby.corsys.repository.enumeration;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.enumeration.DegreeMalnutrition;
import alexeyby.corsys.model.type.DegreeMalnutritionType;

public interface DegreeMalnutritionRepository extends Repository<DegreeMalnutrition, Integer> {
	public DegreeMalnutrition findByDegreeMalnutrition(@Param("degreeMalnutrition") DegreeMalnutritionType degreeMalnutritionType);
}