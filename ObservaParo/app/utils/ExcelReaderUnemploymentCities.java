package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.AutonomousCommunity;
import models.City;
import models.Indicator;
import models.Observation;
import models.Province;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

@SuppressWarnings("deprecation")
public class ExcelReaderUnemploymentCities implements Reader {

	public List<Observation> read(String xlsFile) throws IOException,
			InvalidFormatException {
		List<Observation> obsList = new ArrayList<Observation>();
		InputStream input = new FileInputStream(new File(xlsFile));
		Workbook workbook = WorkbookFactory.create(input);
		Sheet sheet = workbook.getSheetAt(1);
		if(!workbook.getSheetName(1).contains("PARO"))
			sheet = workbook.getSheetAt(0);
		Province province = ProvinceGenerator.createProvince(xlsFile);
		AutonomousCommunity autonomousCommunity = AutonomousCommunityGenerator
				.createComunityByProvince(province);
		AutonomousCommunity.create(autonomousCommunity);
		Province.create(province);
		Date date = new Date(getYear(xlsFile), getMonth(xlsFile), 1);
		for (Row row : sheet) {
			if (row.getRowNum() > 7
					&& row.getRowNum() < sheet.getLastRowNum()-1) {
				String cityName = "";
				String indicatorName = "";
				Double value = 0.0;
				City city = null;
				for (Cell cell : row) {
					switch (cell.getColumnIndex()) {
					case 1:
						cityName = cell.getStringCellValue();
						city = new City(cityName, province);
						City.create(city);
						break;
					case 2:
						indicatorName = "TOTAL";
						value = getCellValue(cell,workbook);
						break;
					case 3:
						indicatorName = "HOMBRES<25";
						value = getCellValue(cell,workbook);
						break;
					case 4:
						indicatorName = "HOMBRES25-44";
						value = getCellValue(cell,workbook);
						break;
					case 5:
						indicatorName = "HOMBRES>=45";
						value = getCellValue(cell,workbook);
						break;
					case 6:
						indicatorName = "MUJERES<25";
						value = getCellValue(cell,workbook);
						break;
					case 7:
						indicatorName = "MUJERES25-44";
						value = getCellValue(cell,workbook);
						break;
					case 8:
						indicatorName = "MUJERES>=45";
						value = getCellValue(cell,workbook);
						break;
					case 9:
						indicatorName = "SECTOR_AGRICULTURA";
						value = getCellValue(cell,workbook);
						break;
					case 10:
						indicatorName = "SECTOR_INDUSTRIA";
						value = getCellValue(cell,workbook);
						break;
					case 11:
						indicatorName = "SECTOR_CONSTRUCCION";
						value = getCellValue(cell,workbook);
						break;
					case 12:
						indicatorName = "SECTOR_SERVICIOS";
						value = getCellValue(cell,workbook);
						break;
					case 13:
						indicatorName = "SIN_EMPLEO_ANTERIOR";
						value = getCellValue(cell,workbook);
						break;
					}
					if (!cityName.equals("") && cell.getColumnIndex() != 1 && cell.getColumnIndex() < 14) {
						Indicator indicator = new Indicator(indicatorName);
						Indicator.create(indicator);
						Observation ob = new Observation(city, indicator, value, date);
						ob.save();
						obsList.add(ob);

					}
				}
			}
		}
		return obsList;
	}

	private Double getCellValue(Cell cell, Workbook workbook) {
		Double value;
		//FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
		if(cell.getCellType() == Cell.CELL_TYPE_STRING)
			value = 0.0;
		else
			value = cell.getNumericCellValue();
		return value;
	}

	private int getYear(String xlsFile) {
		String[] pieces = xlsFile.split("_");
		String year = pieces[pieces.length-1].split("\\.")[0].substring(2, 4);
		return 100 + Integer.parseInt(year);
	}

	private int getMonth(String xlsFile) {
		String[] pieces = xlsFile.split("_");
		String month = pieces[pieces.length-1].split("\\.")[0].substring(0, 2);
		return Integer.parseInt(month);
	}
	
	public String getTheLastMonth(){
		Date currentDate = new Date();
		if(currentDate.getMonth()>1){
			if(currentDate.getMonth()>9)
				return String.valueOf(currentDate.getMonth()-1) + String.valueOf(currentDate.getYear()).substring(1,3);
			else
				return "0" + (currentDate.getMonth()-1) + String.valueOf(currentDate.getYear()).substring(1,3);
		}else if(currentDate.getMonth()==1)
			return "12" + String.valueOf(currentDate.getYear()-1).substring(1,3);
		else
			return "11" + String.valueOf(currentDate.getYear()-1).substring(1,3);
	}
	
	public static ExcelReaderUnemploymentCities getInstance(){
		return new ExcelReaderUnemploymentCities();
	}
	
	public void createObservationsBydate(String date) throws InvalidFormatException, IOException{
		File file = new File("public/data");
		for(File each : file.listFiles()){
			if(each.getName().contains(date) && each.getName().contains("MADRID")){
				read("public/data/"+each.getName());
			}
		}
	}
}