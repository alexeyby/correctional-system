package alexeyby.corsys.repository.calculation;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import alexeyby.corsys.model.calculation.CorrectionFactor;
import alexeyby.corsys.model.enumeration.DegreeMalnutrition;
import alexeyby.corsys.model.enumeration.Injury;
import alexeyby.corsys.model.enumeration.Mobility;
import alexeyby.corsys.model.enumeration.Temperature;

public class CorrectionFactorRepositoryImpl implements CorrectionFactorRepository
{
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public double getValueByInjury(Injury injury) {
		double corFactor = 1.0;
		CorrectionFactor correctionFactor;
		try {
			correctionFactor = (CorrectionFactor) entityManager.createQuery("SELECT corf FROM CorrectionFactor corf " + 
					"WHERE corf.injury = :injury").setParameter("injury", injury).getSingleResult();
		}
		catch (NoResultException e) {
			correctionFactor = null;
		}
		if (correctionFactor != null) {
			corFactor = correctionFactor.getCorrectionFactor();
		}
		return corFactor;
	}

	@Override
	public double getValueByTemperature(Temperature temperature) {
		double corFactor = 1.0;
		CorrectionFactor correctionFactor;
		try {
			correctionFactor = (CorrectionFactor) entityManager.createQuery("SELECT corf FROM CorrectionFactor corf " + 
					"WHERE corf.temperature = :temperature").setParameter("temperature", temperature).getSingleResult();
		}
		catch (NoResultException e) {
			correctionFactor = null;
		}
		if (correctionFactor != null) {
			corFactor = correctionFactor.getCorrectionFactor();
		}
		return corFactor;
	}
	
	@Override
	public double getValueByMobility(Mobility mobility) {
		double corFactor = 1.0;
		CorrectionFactor correctionFactor;
		try {
			correctionFactor = (CorrectionFactor) entityManager.createQuery("SELECT corf FROM CorrectionFactor corf " + 
					"WHERE corf.mobility = :mobility").setParameter("mobility", mobility).getSingleResult();
		}
		catch (NoResultException e) {
			correctionFactor = null;
		}
		if (correctionFactor != null) {
			corFactor = correctionFactor.getCorrectionFactor();
		}
		return corFactor;
	}

	@Override
	public double getValueByDegreeMalnutrition(DegreeMalnutrition degreeMalnutrition) {
		double corFactor = 1.0;
		CorrectionFactor correctionFactor;
		try {
			correctionFactor = (CorrectionFactor) entityManager.createQuery("SELECT corf FROM CorrectionFactor corf " + 
					"WHERE corf.degreeMalnutrition = :degreeMalnutrition").setParameter("degreeMalnutrition", degreeMalnutrition).getSingleResult();
		}
		catch (NoResultException e) {
			correctionFactor = null;
		}
		if (correctionFactor != null) {
			corFactor = correctionFactor.getCorrectionFactor();
		}
		return corFactor;
	}
}
