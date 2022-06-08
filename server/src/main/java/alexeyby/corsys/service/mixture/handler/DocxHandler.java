package alexeyby.corsys.service.mixture.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTabStop;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTabJc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import alexeyby.corsys.model.util.MixtureDocxHelper;
import alexeyby.corsys.service.mixture.handler.enteral.DocxEnteralHandler;

@Component
public class DocxHandler {	
	@Autowired
	JsonHandler jsonHandler;
	
	@Autowired
	DocxEnteralHandler docxEnteralHandler;
	
	public byte[] generateReportEnteral(UUID patientId) {
		byte[] byteDoc = null;
		
		try {
			XWPFDocument doc = new XWPFDocument();
			
			JsonNode base = jsonHandler.generateReport(patientId);
			
			generateReportBase(doc, base);
			docxEnteralHandler.generateReport(doc, base);
			generateReportSignature(doc);
	        
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.write(out);
				
			doc.close();
			
			byteDoc = out.toByteArray();
			
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return byteDoc;
	}
	
	public byte[] generateReportPeptomen(UUID patientId) {
		return generateReport(patientId);
	}
	
	public byte[] generateReport(UUID patientId) {	 
		byte[] byteDoc = null;
	
		try {
			XWPFDocument doc = new XWPFDocument();
			
			JsonNode base = jsonHandler.generateReport(patientId);
			
			generateReportBase(doc, base);
			generateReportSignature(doc);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			doc.write(out);
				
			doc.close();
			
			byteDoc = out.toByteArray();
			
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return byteDoc;
	}
	
	public void generateReportBase(XWPFDocument doc, JsonNode base) {
		XWPFParagraph firstParagraph = doc.createParagraph();		
		firstParagraph.setAlignment(ParagraphAlignment.LEFT);
		CTTabStop tabStop = firstParagraph.getCTP().getPPr().addNewTabs().addNewTab();
		tabStop.setVal(STTabJc.RIGHT);
		tabStop.setPos(6.5 * MixtureDocxHelper.TWIPS_PER_INCH);
		
		XWPFRun run = firstParagraph.createRun();
		run.setText("Клиника, отделение:");
		run.setFontFamily("Times New Roman");
		run.setFontSize(12);
		run.setBold(true);	              
		run = firstParagraph.createRun();
		run.addTab();
		run.setText("№ истории болезни:");
		run.setFontFamily("Times New Roman");
		run.setFontSize(12);
		run.setBold(true);
		run.addBreak();
		
		run = firstParagraph.createRun();
		run.setFontFamily("Times New Roman");
		run.setFontSize(12);
		String healthFacility = base.get("healthFacility").asText();
		run.setText(healthFacility);	
		run = firstParagraph.createRun();
		run.setFontFamily("Times New Roman");
		run.setFontSize(12);
		run.addTab();
		run.setText(base.get("historyNumber").asText());
		
		XWPFParagraph personalDataHeaderParagraph = doc.createParagraph();
		MixtureDocxHelper.generateReportHeading(personalDataHeaderParagraph, "Персональные данные");
		
		JsonNode personalData = base.get("personalData");
		Map<String, String> personalDataMap = new LinkedHashMap<String, String>();
		personalDataMap.put("Фамилия:", personalData.get("lastName").asText());
		personalDataMap.put("Имя:", personalData.get("firstName").asText());
		personalDataMap.put("Отчество:", personalData.get("surname").asText());
		personalDataMap.put("Пол:", personalData.get("sex").asText());
		personalDataMap.put("Возраст (полных лет):", personalData.get("age").asText());
		personalDataMap.put("Дата обследования:", personalData.get("checkupDate").asText());
		personalDataMap.put("Печеночная и (или) почечная недостаточность:", personalData.get("failure").asText());		
		XWPFTable personalDataTable = doc.createTable();
		MixtureDocxHelper.generateReportTable(personalDataTable, personalDataMap);
		
		XWPFParagraph anthroDataHeaderParagraph = doc.createParagraph();
		MixtureDocxHelper.generateReportHeading(anthroDataHeaderParagraph, "Антропометричекие данные");
		
		JsonNode anthroData = base.get("anthroData");
		Map<String, String> anthroDataMap = new LinkedHashMap<String, String>();
		anthroDataMap.put("Вес (кг):", anthroData.get("weight").asText());
		anthroDataMap.put("Рост (см):", anthroData.get("height").asText());
		anthroDataMap.put("Толщина КЖСТ (мм):", anthroData.get("skinfoldThickness").asText());
		anthroDataMap.put("Окружность плеча (см):", anthroData.get("armCircumference").asText());		
		XWPFTable anthroDataTable = doc.createTable();
		MixtureDocxHelper.generateReportTable(anthroDataTable, anthroDataMap);
		
		XWPFParagraph labDataHeaderParagraph = doc.createParagraph();
		MixtureDocxHelper.generateReportHeading(labDataHeaderParagraph, "Лабораторные данные");
		
		JsonNode labData = base.get("labData");
		Map<String, String> labDataMap = new LinkedHashMap<String, String>();
		labDataMap.put("Альбумин (г/л):", labData.get("albumin").asText());
		labDataMap.put("Трансферин (г/л):", labData.get("transferrin").asText());
		labDataMap.put("Лимфоциты (10^9/л):", labData.get("lymphocyte").asText());
		labDataMap.put("Кожная реакция (мм):", labData.get("skinTest").asText());
		XWPFTable labDataTable = doc.createTable();
		MixtureDocxHelper.generateReportTable(labDataTable, labDataMap);
		
		XWPFParagraph patientActualNeedsParagraph = doc.createParagraph();
		MixtureDocxHelper.generateReportHeading(patientActualNeedsParagraph, "Фактические потребности пациента");
		
		JsonNode patientActualNeeds = base.get("patientActualNeeds");
		Map<String, String> patientActualNeedsMap = new LinkedHashMap<String, String>();
		patientActualNeedsMap.put("Активность:", patientActualNeeds.get("mobility").asText());
		patientActualNeedsMap.put("Температура тела:", patientActualNeeds.get("temperature").asText());
		patientActualNeedsMap.put("Фактор повреждения/увечья:", patientActualNeeds.get("injury").asText());
		XWPFTable patientActualNeedsTable = doc.createTable();
		MixtureDocxHelper.generateReportTable(patientActualNeedsTable, patientActualNeedsMap);
		
		XWPFParagraph calculationsParagraph = doc.createParagraph();
		MixtureDocxHelper.generateReportHeading(calculationsParagraph, "Вычисления");
		
		JsonNode calculations = base.get("calculations");
		Map<String, String>  bmiNutritionalStatusMap = new LinkedHashMap<String, String>();
		bmiNutritionalStatusMap.put("Питательный статус по ИМТ:", calculations.get("BMINutritionalStatus").asText());
		XWPFTable bmiNutritionalStatusTable = doc.createTable();
		MixtureDocxHelper.generateReportTable(bmiNutritionalStatusTable,  bmiNutritionalStatusMap);  
		
		XWPFParagraph degreeMalnutritionParagraph = doc.createParagraph();
		MixtureDocxHelper.generateReportSubheading(degreeMalnutritionParagraph, "Степень недостаточности питания:");
		
		JsonNode degreeMalnutrition = calculations.get("degreeMalnutrition");
		Map<String, String> degreeMalnutritionMap = new LinkedHashMap<String, String>();
		degreeMalnutritionMap.put("по ИМТ:", degreeMalnutrition.get("BMI").asText());
		degreeMalnutritionMap.put("по ОМП:", degreeMalnutrition.get("SMV").asText());
		degreeMalnutritionMap.put("по КЖСТ:", degreeMalnutrition.get("SFT").asText());
		degreeMalnutritionMap.put("Альбумин:", degreeMalnutrition.get("albumin").asText());
		degreeMalnutritionMap.put("Трансферрин:", degreeMalnutrition.get("transferrin").asText());
		degreeMalnutritionMap.put("Лимфоциты:", degreeMalnutrition.get("lymphocyte").asText());
		degreeMalnutritionMap.put("Кожная реакция:", degreeMalnutrition.get("skinTest").asText());
		XWPFTable degreeMalnutritionTable = doc.createTable();
		CTTblWidth tblInd = degreeMalnutritionTable.getCTTbl().getTblPr().addNewTblInd();
		tblInd.setW(8 * MixtureDocxHelper.SPACE);
		MixtureDocxHelper.generateReportTable(degreeMalnutritionTable, degreeMalnutritionMap);  
		
		XWPFParagraph totalValueParagraph = doc.createParagraph();
		MixtureDocxHelper.generateReportSubheading(totalValueParagraph, "Суммарная оценка:");
		
		JsonNode totalValue = calculations.get("totalValue");
		Map<String, String>  totalValueMap = new LinkedHashMap<String, String>();
		totalValueMap.put("Тип белковой недостаточности:", totalValue.get("proteinEnergyMalnutrition").asText());
		totalValueMap.put("Степень недостаточности питания:", totalValue.get("totalDegreeMalnutrition").asText());
		XWPFTable totalValueTable = doc.createTable();
		tblInd = totalValueTable.getCTTbl().getTblPr().addNewTblInd();
		tblInd.setW(8 * MixtureDocxHelper.SPACE);
		MixtureDocxHelper.generateReportTable(totalValueTable, totalValueMap);  
		
		XWPFParagraph nutrientNeedsParagraph = doc.createParagraph();
		MixtureDocxHelper.generateReportSubheading(nutrientNeedsParagraph, "Потребности в основных нутриентах:");
		
		JsonNode nutrientNeeds = calculations.get("nutrientNeeds");
		JsonNode nonprotein = nutrientNeeds.get("nonprotein");
		Map<String, String>  nutrientNeedsMap = new LinkedHashMap<String, String>();
		nutrientNeedsMap.put("Белки (г/сут):", nutrientNeeds.get("protein").asText());
		nutrientNeedsMap.put(
		"Количество небелковых калорий (ккал/сут):", nonprotein.get("number").asText() +
		" (из них углеводами " + nonprotein.get("carbs").asText() + " ккал/сут и жирами " +
		nonprotein.get("fat").asText() + " ккал/сут)"
		);
		XWPFTable nutrientNeedsTable = doc.createTable();
		tblInd = nutrientNeedsTable.getCTTbl().getTblPr().addNewTblInd();
		tblInd.setW(8 * MixtureDocxHelper.SPACE);
		MixtureDocxHelper.generateReportTable(nutrientNeedsTable, nutrientNeedsMap);
		nutrientNeedsTable.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(3.4 * MixtureDocxHelper.TWIPS_PER_INCH);
	}
	
	
	private void generateReportSignature(XWPFDocument doc) {
		XWPFParagraph preSignatureParagraph = doc.createParagraph();
		preSignatureParagraph.setSpacingBefore(16 * MixtureDocxHelper.SPACE);
		preSignatureParagraph.setSpacingAfter(16 * MixtureDocxHelper.SPACE);
		XWPFRun run = preSignatureParagraph.createRun();
		run.setFontFamily("Times New Roman");
		run.setFontSize(12);
		
		XWPFTable signatureTable = doc.createTable();
		generateReportSignatureTable(signatureTable);
	}
	
	private void generateReportSignatureTable(XWPFTable table) {
	    table.removeBorders();
	    table.setCellMargins(0, 0, 0, 5 * MixtureDocxHelper.SPACE);
		
   	    XWPFTableRow tableRow = table.getRow(0);
	    XWPFTableCell cell = tableRow.getCell(0);
	    cell.removeParagraph(0);
	    XWPFParagraph paragraph = cell.addParagraph();
	    paragraph.setSpacingAfter(0);
	    XWPFRun run = paragraph.createRun();
	    run.setText("Врач, проводивший исследование:");
		run.setFontFamily("Times New Roman");
		run.setFontSize(12);
	    run.setBold(true);
	    
	    generateReportSignatureFields(tableRow);
	    
	    tableRow = table.createRow();
	    cell = tableRow.getCell(1);
	    cell.removeParagraph(0);
	    paragraph = cell.addParagraph();
	    paragraph.setSpacingAfter(0);
	    paragraph.setAlignment(ParagraphAlignment.CENTER);
	    run = paragraph.createRun();
	    run.setText("(подпись)");
		run.setFontFamily("Times New Roman");
		run.setFontSize(12);
		
	    cell = tableRow.getCell(2);
	    cell.removeParagraph(0);
	    paragraph = cell.addParagraph();
	    paragraph.setSpacingAfter(0);
	    paragraph.setAlignment(ParagraphAlignment.CENTER);
	    run = paragraph.createRun();
	    run.setText("(фамилия и.о.)");
		run.setFontFamily("Times New Roman");
		run.setFontSize(12);
	}
	
	private void generateReportSignatureFields(XWPFTableRow tableRow) {
		for (int i = 0; i < 2; i++) {
			XWPFTableCell cell = tableRow.addNewTableCell();
		    cell.removeParagraph(0);
		    XWPFParagraph paragraph = cell.addParagraph();
		    paragraph.setSpacingAfter(0);
		    paragraph.setAlignment(ParagraphAlignment.CENTER);
		    XWPFRun run = paragraph.createRun();
		    run.setText("______________________");
			run.setFontFamily("Times New Roman");
			run.setFontSize(12);
		}
	}
}
