package alexeyby.corsys.repository.enumeration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import alexeyby.corsys.model.enumeration.DegreeMalnutrition;
import alexeyby.corsys.model.type.DegreeMalnutritionType;

public class DegreeMalnutritionRepositoryImpl implements DegreeMalnutritionRepository
{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public DegreeMalnutrition findByDegreeMalnutrition(DegreeMalnutritionType degreeMalnutritionType) {
		return (DegreeMalnutrition) entityManager.createQuery("SELECT degm FROM DegreeMalnutrition degm " +
	    "WHERE degm.degreeMalnutrition = :degreeMalnutrition").setParameter("degreeMalnutrition", 
	    degreeMalnutritionType.toString()).getSingleResult();
	}
}