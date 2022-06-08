package alexeyby.corsys.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import alexeyby.corsys.model.Patient;
import alexeyby.corsys.service.PatientService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/patients")
public class PatientController 
{
	@Autowired
	private PatientService patientService;
	
	@GetMapping
	public ResponseEntity<Iterable<Patient>> getAllPatients() {
		return ResponseEntity.ok(patientService.getAllPatients());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Patient> getPatient(@PathVariable("id") UUID id) {
		return ResponseEntity.ok(patientService.getPatient(id));
	}
	
	@PostMapping
	public ResponseEntity<Patient> postPatient(@RequestBody Patient patient) {
		return ResponseEntity.ok(patientService.postPatient(patient));
	}
	
	@PutMapping
	public ResponseEntity<Patient> putPatient(@RequestBody Patient patient) { 
		return ResponseEntity.ok(patientService.putPatient(patient));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Patient> deletePatient(@PathVariable("id") UUID id) {
		patientService.deletePatient(id);
		return ResponseEntity.noContent().build();
	}
	
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Patient> handleConstraintViolationException() {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
}
