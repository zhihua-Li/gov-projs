/**
 * SampleExcelParser.java
 *
 * 2013-8-15
 */
package com.lims.action.helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author lizhihua
 *
 * 样本条码Excel
 *
 */
public class SampleExcelParser {

	public static List<String> parseSampleExcel(File excelFile) throws Exception {
		List<String> sampleNoList = new ArrayList<String>();
		Workbook wb = WorkbookFactory.create(excelFile);
		Sheet sheet = wb.getSheetAt(0);

		Iterator<Row> rowIterator = sheet.rowIterator();
		String sampleNo = null;
		while(rowIterator.hasNext()){
			Row row = rowIterator.next();
			Cell cell = row.getCell(0);

			switch(cell.getCellType()){
				case Cell.CELL_TYPE_NUMERIC:
					sampleNo = String.valueOf((int) cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					sampleNo = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					sampleNo = String.valueOf(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
				case Cell.CELL_TYPE_BLANK:
				case Cell.CELL_TYPE_ERROR:
				default:
					break;
			}



			if(!StringUtils.isEmpty(sampleNo)){
				sampleNoList.add(sampleNo);
			}
		}

		return sampleNoList;
	}

}
