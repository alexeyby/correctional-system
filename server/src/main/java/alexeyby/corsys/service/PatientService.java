package alexeyby.corsys.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import alexeyby.corsys.exception.UnprocessableEntityException;
import alexeyby.corsys.exception.NotFoundException;
import alexeyby.corsys.model.Patient;
import alexeyby.corsys.model.enumeration.Sex;
import alexeyby.corsys.repository.CheckupRepository;
import alexeyby.corsys.repository.PatientRepository;
import alexeyby.corsys.repository.enumeration.SexRepository;

@Service
public class PatientService 
{
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private CheckupRepository checkupRepository;
	@Autowired
	private SexRepository sexRepository;
	
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}
	
	public Patient getPatient(UUID id) {
		return patientRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Patient does not exist with id: " + id));
	}
	
	public Patient postPatient(Patient patient) {
		String sex = patient.getSex().getSex();
		Sex sx = sexRepository.findBySex(sex)
				.orElseThrow(() -> new UnprocessableEntityException("Sex does not exist: " + sex));
		patient.setSex(sx);
		
		patient.setHasCheckup(false);
		
		return patientRepository.saveAndFlush(patient);
	}
	
	public Patient putPatient(Patient patient) {
		UUID patientUuid = patient.getPatientUuid();
        Patient pt = patientRepository.findById(patientUuid)
        		.orElseThrow(() -> new NotFoundException("Patient does not exist with id: " + patientUuid));
        
        pt.setHistoryNumber(patient.getHistoryNumber());
        pt.setLastName(patient.getLastName());
        pt.setFirstName(patient.getFirstName());
        pt.setSurname(patient.getSurname());
        pt.setBirthdate(patient.getBirthdate());

		String sex = patient.getSex().getSex();
		Sex sx = sexRepository.findBySex(sex)
				.orElseThrow(() -> new UnprocessableEntityException("Sex does not exist: " + sex));
        pt.setSex(sx);
		
        pt.setFailure(patient.isFailure());
       
        return patientRepository.saveAndFlush(pt);
	}
	
	public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id)
        		.orElseThrow(() -> new NotFoundException("Patient does not exist with id: " + id));
		
        checkupRepository.findByPatientId(id).ifPresent((checkup) -> checkupRepository.delete(checkup));
        
		patientRepository.delete(patient);
	}
}
