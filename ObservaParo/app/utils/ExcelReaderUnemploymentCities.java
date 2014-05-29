package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import models.AutonomousCommunity;
import models.City;
import models.Indicator;
import models.Observation;
import models.Province;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReaderUnemploymentCities implements Reader {

	public List<Observation> read(String xlsFile) throws IOException,
			InvalidFormatException {
		List<Observation> obsList = new ArrayList<Observation>();
		InputStream input = new FileInputStream(new File(xlsFile));
		Workbook workbook = WorkbookFactory.create(input);
		Sheet sheet = workbook.getSheetAt(1);
		Province province = ProvinceGenerator.createProvince(xlsFile);
		AutonomousCommunity autonomousCommunity = AutonomousCommunityGenerator
				.createComunityByProvince(province);
		AutonomousCommunity.create(autonomousCommunity);
		Province.create(province);
		for (Row row : sheet) {
			if (row.getRowNum() > 7
					&& row.getRowNum() != sheet.getPhysicalNumberOfRows() - 1) {
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
						value = cell.getNumericCellValue();
						break;
					case 3:
						indicatorName = "HOMBRES<25";
						value = cell.getNumericCellValue();
						break;
					case 4:
						indicatorName = "HOMBRES25-44";
						value = cell.getNumericCellValue();
						break;
					case 5:
						indicatorName = "HOMBRES>=45";
						value = cell.getNumericCellValue();
						break;
					case 6:
						indicatorName = "MUJERES<25";
						value = cell.getNumericCellValue();
						break;
					case 7:
						indicatorName = "MUJERES25-44";
						value = cell.getNumericCellValue();
						break;
					case 8:
						indicatorName = "MUJERES>=45";
						value = cell.getNumericCellValue();
						break;
					case 9:
						indicatorName = "SECTOR_AGRICULTURA";
						value = cell.getNumericCellValue();
						break;
					case 10:
						indicatorName = "SECTOR_INDUSTRIA";
						value = cell.getNumericCellValue();
						break;
					case 11:
						indicatorName = "SECTOR_CONSTRUCCION";
						value = cell.getNumericCellValue();
						break;
					case 12:
						indicatorName = "SECTOR_SERVICIOS";
						value = cell.getNumericCellValue();
						break;
					case 13:
						indicatorName = "SIN_EMPLEO_ANTERIOR";
						value = cell.getNumericCellValue();
						break;
					}
					if (!cityName.equals("") && cell.getColumnIndex() != 1 && cell.getColumnIndex() < 14) {
						Indicator indicator = new Indicator(indicatorName);
						Indicator.create(indicator);
						Observation ob = new Observation(city, indicator, value);
						ob.save();
						obsList.add(ob);

					}
				}
			}
		}

		return obsList;
	}
}