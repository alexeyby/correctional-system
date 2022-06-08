package alexeyby.corsys.repository.calculation;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import alexeyby.corsys.model.calculation.CorrectionFactor;
import alexeyby.corsys.model.enumeration.DegreeMalnutrition;
import alexeyby.corsys.model.enumeration.Injury;
import alexeyby.corsys.model.enumeration.Mobility;
import alexeyby.corsys.model.enumeration.Temperature;

public interface CorrectionFactorRepository extends Repository<CorrectionFactor, Integer> 
{
	public double getValueByInjury(@Param("injury") Injury injury);
	public double getValueByTemperature(@Param("temperature") Temperature temperature);
	public double getValueByMobility(@Param("mobility") Mobility mobility);
	public double getValueByDegreeMalnutrition(@Param("degreeMalnutrition") DegreeMalnutrition degreeMalnutrition);
}