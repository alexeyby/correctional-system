package alexeyby.corsys.service.mixture;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import alexeyby.corsys.service.mixture.handler.DocxHandler;
import alexeyby.corsys.service.mixture.handler.JsonHandler;
import alexeyby.corsys.model.type.MixtureType;

@Service
public class MixtureService 
{	
	@Autowired
	private JsonHandler jsonHandler;
	@Autowired
	private DocxHandler docxHandler;

	public JsonNode generateReport(String mixture, UUID patientId) {
		JsonNode report = null;
		
		switch (MixtureType.valueOf(mixture)) {
	        case ENTERAL: {
	        	report = jsonHandler.generateReportEnteral(patientId);
	        	break;
	        }
	        case PEPTOMEN: {
	        	report = jsonHandler.generateReportPeptomen(patientId);
	        	break;
	        }
	        default: {
	        	report = jsonHandler.generateReport(patientId);
	        	break;
	        }
	}
		
		return report;
	}
	
	public byte[] generateReportDocx(String mixture, UUID patientId) {
		byte[] doc = null;
				
		switch (MixtureType.valueOf(mixture)) {
		    case ENTERAL: {
		    	doc = docxHandler.generateReportEnteral(patientId);
		    	break;
		    }
		    case PEPTOMEN: {
		    	doc = docxHandler.generateReportPeptomen(patientId);
		    	break;
		    }
		    default: {
		    	doc = docxHandler.generateReport(patientId);
		    	break;
		    }
		}
		
		return doc;
	}
}
