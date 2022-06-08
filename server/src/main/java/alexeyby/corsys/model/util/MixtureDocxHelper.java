package alexeyby.corsys.model.util;

import java.util.Map;
import java.util.Map.Entry;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public abstract class MixtureDocxHelper {
	public static final int TWIPS_PER_INCH = 1440;
	public static final int SPACE = 20;
	
	public static void generateReportHeading(XWPFParagraph paragraph, String text) {
		paragraph.setAlignment(ParagraphAlignment.CENTER);
		paragraph.setSpacingBefore(16 * SPACE);
		paragraph.setSpacingAfter(12 * SPACE);
		
		XWPFRun run =  paragraph.createRun();
		run.setText(text);
		run.setFontFamily("Times New Roman");
		run.setFontSize(14);
		run.setBold(true);
	}
	
	public static void generateReportTable(XWPFTable table, Map<String, String> data) {
	    table.removeBorders();
	    table.setCellMargins(0, 0, 0, 5 * MixtureDocxHelper.SPACE);
		
	    boolean init = true;
	    for (Entry<String, String> entry : data.entrySet()) {
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
   	        run.setText(entry.getKey());
   		    run.setFontFamily("Times New Roman");
   		    run.setFontSize(12);
   	        run.setBold(true);
   	        
	        if (init) {
	        	cell = tableRow.addNewTableCell();
	        	init = false;
	        } else {
	        	cell = tableRow.getCell(1);
	        }
   	        cell.removeParagraph(0);
   	        paragraph = cell.addParagraph();
   	        paragraph.setSpacingAfter(0);
   	        run = paragraph.createRun();
   	        run.setText(entry.getValue());
   		    run.setFontFamily("Times New Roman");
   		    run.setFontSize(12);
	    }
	}
	
	public static void generateReportSubheading(XWPFParagraph paragraph, String text) {
		paragraph.setSpacingBefore(12 * MixtureDocxHelper.SPACE);
		paragraph.setSpacingAfter(12 * MixtureDocxHelper.SPACE);
		
		XWPFRun run =  paragraph.createRun();
		run.setText(text);
		run.setFontFamily("Times New Roman");
		run.setFontSize(12);
		run.setBold(true);
	}
}