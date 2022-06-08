package alexeyby.corsys.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alexeyby.corsys.exception.UnprocessableEntityException;
import alexeyby.corsys.exception.ConflictException;
import alexeyby.corsys.exception.NotFoundException;
import alexeyby.corsys.model.Checkup;
import alexeyby.corsys.model.Patient;
import alexeyby.corsys.model.enumeration.Injury;
import alexeyby.corsys.model.enumeration.Mobility;
import alexeyby.corsys.model.enumeration.Temperature;
import alexeyby.corsys.repository.CheckupRepository;
import alexeyby.corsys.repository.PatientRepository;
import alexeyby.corsys.repository.enumeration.InjuryRepository;
import alexeyby.corsys.repository.enumeration.MobilityRepository;
import alexeyby.corsys.repository.enumeration.TemperatureRepository;

@Service
public class CheckupService 
{
	@Autowired
	private CheckupRepository checkupRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private MobilityRepository mobilityRepository;
	@Autowired
	private TemperatureRepository temperatureRepository;
	@Autowired
	private InjuryRepository injuryRepository;
	
	
	public List<Checkup> getAllCheckups() {
		return checkupRepository.findAll();
	}
	
	public Checkup getCheckup(UUID id) {
		return checkupRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Checkup does not exist with id: " + id));
	}
			
	public Optional<Checkup> getCheckupByPatientId(UUID patientId) {
		return checkupRepository.findByPatientId(patientId);
	}
	
	public Checkup postCheckup(Checkup checkup) {
		UUID patientUuid = checkup.getPatient().getPatientUuid();
		Patient patient = patientRepository.findById(patientUuid)
				.orElseThrow(() -> new UnprocessableEntityException("Patient does not exist with id: " + patientUuid));
		checkup.setPatient(patient);
		if (patient.isHasCheckup()) throw new ConflictException("Patient with id " + patientUuid + " already has a checkup");
		
		String mobility = checkup.getMobility().getMobility();
		Mobility mob = mobilityRepository.findByMobility(mobility)
				.orElseThrow(() -> new UnprocessableEntityException("Mobility does not exist: " + mobility));
		checkup.setMobility(mob);
		
		String temperature = checkup.getTemperature().getTemperature();
		Temperature temp = temperatureRepository.findByTemperature(temperature)
				.orElseThrow(() -> new UnprocessableEntityException("Temperature does not exist: " + temperature));
		checkup.setTemperature(temp);
		
		String injury = checkup.getInjury().getInjury();
		Injury inj = injuryRepository.findByInjury(injury)
				.orElseThrow(() -> new UnprocessableEntityException("Injury does not exist: " + injury));
		checkup.setInjury(inj);
		
		Checkup ckup = checkupRepository.saveAndFlush(checkup);
		
		patient.setHasCheckup(true);
		patientRepository.saveAndFlush(patient);
		
		return ckup;
	}
	
	public Checkup putCheckup(Checkup checkup) {
		UUID checkupUuid = checkup.getCheckupUuid();
        Checkup chkup = checkupRepository.findById(checkupUuid)
        		.orElseThrow(() -> new NotFoundException("Checkup does not exist with id: " + checkupUuid));
        
        UUID patientUuid = checkup.getPatient().getPatientUuid();
		Patient patient = patientRepository.findById(patientUuid).get();
        
		Patient pt = checkup.getPatient();
		chkup.setPatient(patient);
		
        chkup.setWeight(checkup.getWeight());
        chkup.setHeight(checkup.getHeight());
        chkup.setSkinfoldThickness(checkup.getSkinfoldThickness());
        chkup.setArmCircumference(checkup.getArmCircumference());
        chkup.setAlbumin(checkup.getAlbumin());
        chkup.setTransferrin(checkup.getTransferrin());
        chkup.setLymphocyte(checkup.getLymphocyte());
        chkup.setSkinTest(checkup.getSkinTest());
        
		String mobility = checkup.getMobility().getMobility();
		Mobility mob = mobilityRepository.findByMobility(mobility)
				.orElseThrow(() -> new UnprocessableEntityException("Mobility does not exist: " + mobility));
		chkup.setMobility(mob);
		
		String temperature = checkup.getTemperature().getTemperature();
		Temperature temp = temperatureRepository.findByTemperature(temperature)
				.orElseThrow(() -> new UnprocessableEntityException("Temperature does not exist: " + temperature));
		chkup.setTemperature(temp);
		
		String injury = checkup.getInjury().getInjury();
		Injury inj = injuryRepository.findByInjury(injury)
				.orElseThrow(() -> new UnprocessableEntityException("Injury does not exist: " + injury));
		chkup.setInjury(inj);
        
        chkup.setCheckupDate(checkup.getCheckupDate());
        chkup.setHealthFacility(checkup.getHealthFacility());
        
		if (!patientUuid.equals(pt.getPatientUuid())) {
			pt.setHasCheckup(false);
			patientRepository.saveAndFlush(pt);
		}
        
        return checkupRepository.saveAndFlush(chkup);
	}
	
	public void deleteCheckup(UUID id) {
        Checkup checkup = checkupRepository.findById(id)
        		.orElseThrow(() -> new NotFoundException("Checkup does not exist with id: " + id));     
        
        Patient patient = checkup.getPatient();
        
		checkupRepository.delete(checkup);
		
        patient.setHasCheckup(false);
        patientRepository.saveAndFlush(patient);
	}
}
