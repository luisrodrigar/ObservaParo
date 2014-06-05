package utils;

import static org.assertj.core.api.Assertions.assertThat;
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
		
		String xlsFile = "public/data/MUNI_BADAJOZ_0414.xls";
		ExcelReaderUnemploymentCities excelReader = new ExcelReaderUnemploymentCities();
		excelReader.read(xlsFile);
		assertThat((Observation.average(Observation.all(), "TOTAL")))
				.isEqualTo(556.1393939393939);
		assertThat((Observation.count(Observation.all(), "TOTAL")))
			.isEqualTo(165);
		assertThat((Observation.count(Observation.all(), null)))
			.isEqualTo(1980);
		Observation.deleteAll();
		
		String date = excelReader.getTheLastMonth();
		excelReader.createObservationsBydate(date);
		
		assertThat(Observation.sum(Observation.all(), "TOTAL"))
			.isEqualTo(4684301);
	}

	@AfterClass
	public static void stopApp() {
		Helpers.stop(app);
	}
}
