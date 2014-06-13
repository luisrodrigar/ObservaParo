package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jboss.netty.handler.codec.compression.ZlibWrapper;

import utils.*;
import models.*;
import models.Zone.TypeZone;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render());
	}

	public static Result community() {
		return ok(community.render());
	}

	public static Result province() {
		return ok(province.render());
	}

	public static Result sector() {
		return ok(sector.render());
	}

	public static Result people() {
		return ok(people.render());
	}

	@SuppressWarnings("deprecation")
	public static Result showCompareZone() throws NumberFormatException,
			InvalidFormatException, IOException {
		String communityId, year1, year2, month1, month2, year;
		ExcelReaderUnemploymentCities ex = new ExcelReaderUnemploymentCities();
		AutonomousCommunity ac;
		List<Observation> observations;
		DynamicForm form = Form.form().bindFromRequest();
		year1 = form.get("year_1");
		year2 = form.get("year_2");
		month1 = form.get("month_1");
		month2 = form.get("month_2");
		communityId = form.get("community");
		boolean isYears = month1 == null && month2 == null && year1 != null
				&& year2 != null;
		if (isYears) {
			observations = new ArrayList<Observation>();
			ac = AutonomousCommunity.findByCode(communityId);
			for (Province province : Province.findByAutonomousCommunity(ac)) {
				for (Observation ob : ex.readProvince("public/data/MUNI_"
						+ ProvinceGenerator.getNameProvince(province.code,
								Integer.parseInt(year1)) + "_12"
						+ year1.substring(2, 4) + ".xls"))
					observations.add(ob);
				for (Observation ob : ex.readProvince("public/data/MUNI_"
						+ ProvinceGenerator.getNameProvince(province.code,
								Integer.parseInt(year2)) + "_12"
						+ year2.substring(2, 4) + ".xls"))
					observations.add(ob);
			}
			Observation obYear1 = new Observation(new Zone("COMMUNITY"
					+ ac.code, null, TypeZone.AUTONOMOUS_COMMUNITY),
					Indicator.findByName("TOTAL"), 0L, new Date(
							Integer.parseInt(year1) - 1900, 11, 31));
			Observation obYear2 = new Observation(new Zone("COMMUNITY"
					+ ac.code, null, TypeZone.AUTONOMOUS_COMMUNITY),
					Indicator.findByName("TOTAL"), 0L, new Date(
							Integer.parseInt(year2) - 1900, 11, 31));
			for (Observation each : observations) {
				if (each.date.getYear() + 1900 == Integer.parseInt(year1)
						&& each.indicator.name.equals("TOTAL"))
					obYear1.obsValue += each.obsValue;
				else if (each.date.getYear() + 1900 == Integer.parseInt(year2)
						&& each.indicator.name.equals("TOTAL"))
					obYear2.obsValue += each.obsValue;
			}
			return ok(comparison.render(obYear1, obYear2, ac));
		} else if (!isYears) {
			year = form.get("year");
			observations = new ArrayList<Observation>();
			ac = AutonomousCommunity.findByCode(communityId);
			for (Province province : Province.findByAutonomousCommunity(ac)) {
				if (Integer.parseInt(month1) < 9)
					for (Observation ob : ex.readProvince("public/data/MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year)) + "_0" + month1
							+ year.substring(2, 4) + ".xls"))
						observations.add(ob);
				else
					for (Observation ob : ex.readProvince("public/data/MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year)) + "_" + month1
							+ year.substring(2, 4) + ".xls"))
						observations.add(ob);
				if (Integer.parseInt(month2) < 9)
					for (Observation ob : ex.readProvince("public/data/MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year)) + "_0" + month2
							+ year.substring(2, 4) + ".xls"))
						observations.add(ob);
				else
					for (Observation ob : ex.readProvince("public/data/MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year)) + "_" + month2
							+ year.substring(2, 4) + ".xls"))
						observations.add(ob);
			}
			return ok(comparison.render(null, null, ac));
		}
		return ok(index.render());
	}
}
