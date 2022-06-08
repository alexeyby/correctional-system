package alexeyby.corsys.repository.enumeration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import alexeyby.corsys.model.enumeration.Indicator;

public interface IndicatorRepository extends Repository<Indicator, Integer> {
	@Query(value = "SELECT ind FROM Indicator ind WHERE ind.indicatorId = 1")
	public Indicator findAlbumin();
	@Query(value = "SELECT ind FROM Indicator ind WHERE ind.indicatorId = 2")
	public Indicator findTransferrin();
	@Query(value = "SELECT ind FROM Indicator ind WHERE ind.indicatorId = 3")
	public Indicator findLymphocyte();
	@Query(value = "SELECT ind FROM Indicator ind WHERE ind.indicatorId = 4")
	public Indicator findSkinTest();
}