package alexeyby.corsys.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.JsonNode;
import alexeyby.corsys.service.mixture.MixtureService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/mixtures")
public class MixtureController 
{
	@Autowired
	private MixtureService mixtureService;
	
	@GetMapping("/generate-report/{mixture}/{id}")
	public ResponseEntity<JsonNode> generateReport(@PathVariable("mixture") String mixture, 
			@PathVariable("id") UUID patientId) {
		return ResponseEntity.ok(mixtureService.generateReport(mixture, patientId));
	}
	
	@GetMapping(value = "/generate-report/docx/{mixture}/{id}", 
			produces = "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
	public ResponseEntity<byte[]> generateReportDocx(@PathVariable("mixture") String mixture, 
			@PathVariable("id") UUID patientId) {
        return ResponseEntity.ok().body(mixtureService.generateReportDocx(mixture, patientId));
	}
}