package utils;

import models.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

public class CrawlerTest {

	public static FakeApplication app;

	@BeforeClass
	public static void startApp() {
		app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
		Helpers.start(app);
		Observation.deleteAll();
	}

	@Test
	public void readExcelFile() throws Throwable {
		Crawler cr = new Crawler();
		cr.download();
	}

	@AfterClass
	public static void stopApp() {
		Helpers.stop(app);
	}
}
