package utils;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import models.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.FakeApplication;
import play.test.Helpers;

public class ExcelReaderTest {

	public static FakeApplication app;

	@BeforeClass
	public static void startApp() {
		app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
		Helpers.start(app);
		Observation.deleteAll();
	}

	@Test
	public void readExcelFile() throws Throwable {
		
		String xlsFile = "public/data/MUNI_A_CORUNA_0106.xls";
		ExcelReaderUnemploymentCities excelReader = new ExcelReaderUnemploymentCities();
		excelReader.read(xlsFile);
		assertThat((Observation.average(Observation.all(), "TOTAL")))
				.isEqualTo(769.6914893617021);
		assertThat((Observation.count(Observation.all(), "TOTAL")))
			.isEqualTo(94);
		assertThat((Observation.count(Observation.all(), null)))
			.isEqualTo(1128);
		Observation.deleteAll();
		String date = excelReader.getTheLastMonth();
		excelReader.createObservationsBydate(date);
		double sum = 0;
		for(Observation ob: Observation.all()){
			if(ob.indicator.name.equals("TOTAL"))
				sum += ob.obsValue;
		}
		assertThat(sum)
		.isEqualTo(535914.0);
	}

	@AfterClass
	public static void stopApp() {
		Helpers.stop(app);
	}
}
