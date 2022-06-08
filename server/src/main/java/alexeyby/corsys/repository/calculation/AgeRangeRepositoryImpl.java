package alexeyby.corsys.repository.calculation;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import alexeyby.corsys.model.calculation.AgeRange;
import alexeyby.corsys.model.enumeration.Sex;

public class AgeRangeRepositoryImpl implements AgeRangeRepository 
{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public Optional<AgeRange> findByAgeBMI(int age) {
		AgeRange ageRange;
		try {
			ageRange = (AgeRange) entityManager.createQuery("SELECT agerng FROM AgeRange agerng WHERE :age >= agerng.ageLow AND :age < agerng.ageHigh " +
				    "AND agerng.ageRangeId BETWEEN 1 AND 2").setParameter("age", age).getSingleResult();
		} 
		catch (NoResultException e) {
			ageRange = null;
		}
		return Optional.of(ageRange);
	}

	@Override
	public Optional<AgeRange> findByAgeSexSFT(int age, Sex sex) {
		AgeRange ageRange;
		try {
			ageRange = (AgeRange) entityManager.createQuery("SELECT agerng FROM AgeRange agerng WHERE :age >= agerng.ageLow " + 
				    "AND :age < agerng.ageHigh AND (:sexId = 1 AND agerng.ageRangeId BETWEEN 3 AND 7 OR :sexId = 2 AND agerng.ageRangeId BETWEEN 6 AND 8)")
					.setParameter("age", age).setParameter("sexId", sex.getSexId()).getSingleResult();
		} 
		catch (NoResultException e) {
			ageRange = null;
		}
		return Optional.of(ageRange);
	}
}
