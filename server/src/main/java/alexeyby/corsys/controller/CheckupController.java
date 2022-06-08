package alexeyby.corsys.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import alexeyby.corsys.model.Checkup;
import alexeyby.corsys.service.CheckupService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/checkups")
public class CheckupController 
{
	@Autowired
	private CheckupService checkupService;
	
	@GetMapping
	public ResponseEntity<Iterable<Checkup>> getAllCheckups() {
		return ResponseEntity.ok(checkupService.getAllCheckups());
	}
	
	@GetMapping("/by-checkup-id/{id}")
	public ResponseEntity<Checkup> getCheckup(@PathVariable("id") UUID id) {
		return ResponseEntity.ok(checkupService.getCheckup(id));
	}
	
	@GetMapping("/by-patient-id/{patientId}")
	public ResponseEntity<Checkup> getCheckupByPatientId(@PathVariable("patientId") UUID patientId) {
		return checkupService.getCheckupByPatientId(patientId).map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.noContent().build());
	}
	
	@PostMapping
	public ResponseEntity<Checkup> postCheckup(@RequestBody Checkup checkup) {
		return ResponseEntity.ok(checkupService.postCheckup(checkup));
	}
	
	@PutMapping
	public ResponseEntity<Checkup> putCheckup(@RequestBody Checkup checkup) {
		return ResponseEntity.ok(checkupService.putCheckup(checkup));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Checkup> deleteCheckup(@PathVariable("id") UUID id) {
		checkupService.deleteCheckup(id);
		return ResponseEntity.noContent().build();
	}
}
