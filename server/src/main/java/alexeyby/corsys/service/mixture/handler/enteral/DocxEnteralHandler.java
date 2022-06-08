package alexeyby.corsys.service.mixture.handler.enteral;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import alexeyby.corsys.model.util.MixtureDocxHelper;

@Component
public class DocxEnteralHandler {
	@Autowired
	JsonEnteralHandler jsonEnteralHandler;
	
	public void generateReport(XWPFDocument doc, JsonNode base) {
		JsonNode nutrientNeeds = base.get("calculations").get("nutrientNeeds");
		double protein = nutrientNeeds.get("protein").asDouble();
		double nonprotein = nutrientNeeds.get("nonprotein").get("number").asDouble();
		
		JsonNode report = jsonEnteralHandler.generateReport(protein, nonprotein);
		
		XWPFParagraph recommendationsParagraph = doc.createParagraph();
		
		MixtureDocxHelper.generateReportHeading(recommendationsParagraph, "Рекомендации");
		
		Map<String, String> enteralMap = new LinkedHashMap<String, String>();
		enteralMap.put("Рекомендовано применение препарата «Энтерал» (мл/сут):", report.get("enteral").asText());	
		XWPFTable enteralTable = doc.createTable();
		MixtureDocxHelper.generateReportTable(enteralTable, enteralMap);
		
		XWPFParagraph nutrientNeedsParagraph = doc.createParagraph();
		MixtureDocxHelper.generateReportSubheading(nutrientNeedsParagraph, "Дополнительно рекомендовано получать:");
		
		JsonNode nonproteinNode = nutrientNeeds.get("nonprotein");
		Map<String, String> nutrientNeedsMap = new LinkedHashMap<String, String>();
		nutrientNeedsMap.put("Белки (г/сут):", nutrientNeeds.get("protein").asText());
		nutrientNeedsMap.put(
		"Количество небелковых калорий (ккал/сут):", nonproteinNode.get("number").asText() +
		" (из них углеводами " + nonproteinNode.get("carbs").asText() + " ккал/сут и жирами " +
		nonproteinNode.get("fat").asText() + " ккал/сут)"
		);
		XWPFTable nutrientNeedsTable = doc.createTable();
		CTTblWidth tblInd = nutrientNeedsTable.getCTTbl().getTblPr().addNewTblInd();
		tblInd.setW(8 * MixtureDocxHelper.SPACE);
		MixtureDocxHelper.generateReportTable(nutrientNeedsTable, nutrientNeedsMap);
		nutrientNeedsTable.getRow(0).getCell(0).getCTTc().addNewTcPr().addNewTcW().setW(3.4 * MixtureDocxHelper.TWIPS_PER_INCH);
		
		XWPFParagraph nutrientsParagraph = doc.createParagraph();
		MixtureDocxHelper.generateReportSubheading(nutrientsParagraph, "Получаемые нутриенты в составе препарата «Энтерал»:");
		
		JsonNode nutrients = report.get("nutrients");
		JsonNode aminoAcids = nutrients.get("aminoAcids");
		JsonNode vitamins = nutrients.get("vitamins");
		List<Pair<List<String>, Boolean>> nutrientsList = new ArrayList<Pair<List<String>, Boolean>>();
		nutrientsList.add(Pair.of(List.of("Название аминокислоты", "Количество (мг)", "% рекомендуемой суточной дозы"), true));
		JsonNode glycine = aminoAcids.get("glycine");
		nutrientsList.add(Pair.of(List.of("Глицин", glycine.get("amount").asText(), glycine.get("percentage").asText()), false));
		JsonNode selenomethionine = aminoAcids.get("selenomethionine");
		nutrientsList.add(Pair.of(List.of("Селен-метионин", selenomethionine.get("amount").asText(), 
				selenomethionine.get("percentage").asText()), false));
		JsonNode lOrnithineLAspartate = aminoAcids.get("LOrnithineLAspartate");
		nutrientsList.add(Pair.of(List.of("L-орнитин-L-аспартат", lOrnithineLAspartate.get("amount").asText(), 
				lOrnithineLAspartate.get("percentage").asText()), false));
		JsonNode lProline = aminoAcids.get("LProline");
		nutrientsList.add(Pair.of(List.of("L-пролин", lProline.get("amount").asText(), lProline.get("percentage").asText()), false));
		JsonNode lSerine = aminoAcids.get("LSerine");
		nutrientsList.add(Pair.of(List.of("L-серин", lSerine.get("amount").asText(), lSerine.get("percentage").asText()), false));
		JsonNode lAsparticAcid = aminoAcids.get("LAsparticAcid");
		nutrientsList.add(Pair.of(List.of("L-аспарагиновая кислота", lAsparticAcid.get("amount").asText(), 
				lAsparticAcid.get("percentage").asText()), false));
		JsonNode lGlutamicAcid = aminoAcids.get("LGlutamicAcid");
		nutrientsList.add(Pair.of(List.of("L-глютаминовая кислота", lGlutamicAcid.get("amount").asText(), 
				lGlutamicAcid.get("percentage").asText()), false));
		JsonNode lPhenylalanine = aminoAcids.get("LPhenylalanine");
		nutrientsList.add(Pair.of(List.of("L-фенилаланин", lPhenylalanine.get("amount").asText(), lPhenylalanine.get("percentage").asText()), false));
		JsonNode lTyrosine = aminoAcids.get("LTyrosine");
		nutrientsList.add(Pair.of(List.of("L-тирозин", lTyrosine.get("amount").asText(), lTyrosine.get("percentage").asText()), false));
		JsonNode lLeucine = aminoAcids.get("LLeucine");
		nutrientsList.add(Pair.of(List.of("L-лейцин", lLeucine.get("amount").asText(), lLeucine.get("percentage").asText()), false));
		JsonNode lLysineHydrochloride = aminoAcids.get("LLysineHydrochloride");
		nutrientsList.add(Pair.of(List.of("L-лизин-хлорид", lLysineHydrochloride.get("amount").asText(), 
				lLysineHydrochloride.get("percentage").asText()), false));
		JsonNode lIsoleucine = aminoAcids.get("LIsoleucine");
		nutrientsList.add(Pair.of(List.of("L-изолейцин", lIsoleucine.get("amount").asText(), lIsoleucine.get("percentage").asText()), false));
		JsonNode lMethionine = aminoAcids.get("LMethionine");
		nutrientsList.add(Pair.of(List.of("L-метионин", lMethionine.get("amount").asText(), lMethionine.get("percentage").asText()), false));
		JsonNode lValine = aminoAcids.get("LValine");
		nutrientsList.add(Pair.of(List.of("L-валин", lValine.get("amount").asText(), lValine.get("percentage").asText()), false));
		JsonNode lTryptophan = aminoAcids.get("LTryptophan");
		nutrientsList.add(Pair.of(List.of("L-триптофан", lTryptophan.get("amount").asText(), lTryptophan.get("percentage").asText()), false));
		JsonNode lThreonine = aminoAcids.get("LThreonine");
		nutrientsList.add(Pair.of(List.of("L-треонин", lThreonine.get("amount").asText(), lThreonine.get("percentage").asText()), false));
		JsonNode lArginine = aminoAcids.get("LArginine");
		nutrientsList.add(Pair.of(List.of("L-аргинин", lArginine.get("amount").asText(), lArginine.get("percentage").asText()), false));
		JsonNode lHistidine = aminoAcids.get("LHistidine");
		nutrientsList.add(Pair.of(List.of("L-гистидин", lHistidine.get("amount").asText(), lHistidine.get("percentage").asText()), false));
		JsonNode lAlanine = aminoAcids.get("LAlanine");
		nutrientsList.add(Pair.of(List.of("L-аланин", lAlanine.get("amount").asText(), lAlanine.get("percentage").asText()), false));
		
		nutrientsList.add(Pair.of(List.of("", "", ""), false));
		
		nutrientsList.add(Pair.of(List.of("Витамины", "Количество (мг)", "% рекомендуемой суточной дозы"), true));
		JsonNode riboflavin = vitamins.get("riboflavin");
		nutrientsList.add(Pair.of(List.of("Рибофлавин", riboflavin.get("amount").asText(), riboflavin.get("percentage").asText()), false));
		JsonNode pyridoxineHydrochloride = vitamins.get("pyridoxineHydrochloride");
		nutrientsList.add(Pair.of(List.of("Пиродоксина гидрохлорид", pyridoxineHydrochloride.get("amount").asText(), 
				pyridoxineHydrochloride.get("percentage").asText()), false));
		JsonNode nicotinamide = vitamins.get("nicotinamide");
		nutrientsList.add(Pair.of(List.of("Никотинамид", nicotinamide.get("amount").asText(), 
				nicotinamide.get("percentage").asText()), false));
		XWPFTable nutrientsTable = doc.createTable();
		tblInd = nutrientsTable.getCTTbl().getTblPr().addNewTblInd();
		tblInd.setW(8 * MixtureDocxHelper.SPACE);
		generateReportNutrientsTable(nutrientsTable, nutrientsList);
	}
	
	private void generateReportNutrientsTable(XWPFTable table, List<Pair<List<String>, Boolean>> data) {
	    table.removeBorders();
	    table.setCellMargins(0, 0, 0, 5 * MixtureDocxHelper.SPACE);
		
	    boolean init = true;
	   
	    for (Pair<List<String>, Boolean> record : data) {
	    	List<String> list = record.getFirst();
	    	XWPFTableRow tableRow;
	    	if (init) {
	    		tableRow = table.getRow(0);
	    	} else {
	    		tableRow = table.createRow();
	    	}
	    	
   	        XWPFTableCell cell = tableRow.getCell(0);
   	        cell.removeParagraph(0);
   	        XWPFParagraph paragraph = cell.addParagraph();
   	        paragraph.setSpacingAfter(0);
   	        XWPFRun run = paragraph.createRun();
   	        run.setText(list.get(0));
   		    run.setFontFamily("Times New Roman");
   		    run.setFontSize(12);
   	        run.setBold(true);
   	        
	        if (init) {
	        	cell = tableRow.addNewTableCell();
	        } else {
	        	cell = tableRow.getCell(1);
	        }
   	        cell.removeParagraph(0);
   	        paragraph = cell.addParagraph();
   	        paragraph.setSpacingAfter(0);
   	        run = paragraph.createRun();
   	        run.setText(list.get(1));
   		    run.setFontFamily("Times New Roman");
   		    run.setFontSize(12);
   		    if (record.getSecond()) {
   		    	run.setBold(true);
   		    }
   		    
	        if (init) {
	        	cell = tableRow.addNewTableCell();
	        	init = false;
	        } else {
	        	cell = tableRow.getCell(2);
	        }
   	        cell.removeParagraph(0);
   	        paragraph = cell.addParagraph();
   	        paragraph.setSpacingAfter(0);
   	        run = paragraph.createRun();
   	        run.setText(list.get(2));
   		    run.setFontFamily("Times New Roman");
   		    run.setFontSize(12);
   		    if (record.getSecond()) {
   		    	run.setBold(true);
   		    }
	    }
	}
}
