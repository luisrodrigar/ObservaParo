package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import models.Observation;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	public List<Observation> read(InputStream input) throws IOException {
	  List<Observation> obsList = new ArrayList<Observation>();

	  XSSFWorkbook workbook = new XSSFWorkbook(input);
	  XSSFSheet sheet = workbook.getSheetAt(1);
	  for (Row row : sheet) {
		  if (row.getRowNum()  > 8 && row.getRowNum()!=sheet.getPhysicalNumberOfRows()-1) {
			  
			String cityName = "";
			String indicatorName = "";
			Double value = 0.0;

			for (Cell cell : row) {
				switch(cell.getColumnIndex()){
					case 0:
						cityName = cell.getStringCellValue();
						break;
					case 1:
						indicatorName = "TOTAL";
						value = cell.getNumericCellValue();
						break;
					case 2:
						indicatorName = "HOMBRES<25";
						value = cell.getNumericCellValue();
						break;
					case 3:
						indicatorName = "HOMBRES25-44";
						value = cell.getNumericCellValue();
						break;
					case 4:
						indicatorName = "HOMBRES>=45";
						value = cell.getNumericCellValue();
						break;
					case 5:
						indicatorName = "MUJERES<25";
						value = cell.getNumericCellValue();
						break;
					case 6:
						indicatorName = "MUJERES25-44";
						value = cell.getNumericCellValue();
						break;
					case 7:
						indicatorName = "MUJERES>=45";
						value = cell.getNumericCellValue();
						break;
					case 8:
						indicatorName = "SECTOR_AGRICULTURA";
						value = cell.getNumericCellValue();
						break;
					case 9:
						indicatorName = "SECTOR_INDUSTRIA";
						value = cell.getNumericCellValue();
						break;
					case 10:
						indicatorName = "SECTOR_CONSTRUCCION";
						value = cell.getNumericCellValue();
						break;
					case 11:
						indicatorName = "SECTOR_SERVICIOS";
						value = cell.getNumericCellValue();
						break;
					case 12:
						indicatorName = "SIN_EMPLEO_ANTERIOR";
						value = cell.getNumericCellValue();
						break;
				}
				if(!cityName.equals("")){
					obsList.add(new Observation(cityName, indicatorName, value));
				}
			}
		  }
		}

	  return obsList;
	}
}