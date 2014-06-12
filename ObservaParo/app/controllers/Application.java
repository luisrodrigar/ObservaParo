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
		String type = form.get("type");
		switch (type) {
		case "community_year":
			communityId = Form.form().bindFromRequest().get("community");
			year1 = Form.form().bindFromRequest().get("year_1");
			year2 = Form.form().bindFromRequest().get("year_2");
			observations = new ArrayList<Observation>();
			ac = AutonomousCommunity.findByCode(communityId);
			for (Province province : Province.findByAutonomousCommunity(ac)) {
				for (int i = 1; i < 10; i++)
					for (Observation ob : ex.readProvince("MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year1)) + "_0" + i
							+ year1.substring(2, 4) + ".xls"))
						observations.add(ob);
				for (int i = 10; i < 13; i++)
					for (Observation ob : ex.readProvince("MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year1)) + "_" + i
							+ year1.substring(2, 4) + ".xls"))
						observations.add(ob);
				for (int i = 1; i < 10; i++)
					for (Observation ob : ex.readProvince("MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year2)) + "_0" + i
							+ year2.substring(2, 4) + ".xls"))
						observations.add(ob);
				for (int i = 10; i < 13; i++)
					for (Observation ob : ex.readProvince("MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year2)) + "_" + i
							+ year2.substring(2, 4) + ".xls"))
						observations.add(ob);
			}
			Observation obYear1 = new Observation(new Zone("COMMUNITY"
					+ ac.code, null, TypeZone.AUTONOMOUS_COMMUNITY),
					Indicator.findByName("TOTAL"), 0L, new Date(
							Integer.parseInt(year1) - 1900, 0, 1));
			Observation obYear2 = new Observation(new Zone("COMMUNITY"
					+ ac.code, null, TypeZone.AUTONOMOUS_COMMUNITY),
					Indicator.findByName("TOTAL"), 0L, new Date(
							Integer.parseInt(year2) - 1900, 0, 1));
			for (Observation each : observations) {
				if (each.date.getYear() + 1900 == Integer.parseInt(year1)
						&& each.indicator.equals("TOTAL"))
					obYear1.obsValue += each.obsValue;
				else if (each.date.getYear() + 1900 == Integer.parseInt(year2)
						&& each.indicator.equals("TOTAL"))
					obYear2.obsValue += each.obsValue;
			}
			return ok(comparison.render(obYear1, obYear2));
		case "month":
			communityId = Form.form().bindFromRequest().get("community");
			month1 = Form.form().bindFromRequest().get("month_1");
			month2 = Form.form().bindFromRequest().get("month_2");
			year = Form.form().bindFromRequest().get("year");
			observations = new ArrayList<Observation>();
			ac = AutonomousCommunity.findByCode(communityId);
			for (Province province : Province.findByAutonomousCommunity(ac)) {
				if (Integer.parseInt(month1) < 9)
					for (Observation ob : ex.readProvince("MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year)) + "_0" + month1
							+ year.substring(2, 4) + ".xls"))
						observations.add(ob);
				else
					for (Observation ob : ex.readProvince("MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year)) + "_" + month1
							+ year.substring(2, 4) + ".xls"))
						observations.add(ob);
				if (Integer.parseInt(month2) < 9)
					for (Observation ob : ex.readProvince("MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year)) + "_0" + month2
							+ year.substring(2, 4) + ".xls"))
						observations.add(ob);
				else
					for (Observation ob : ex.readProvince("MUNI_"
							+ ProvinceGenerator.getNameProvince(province.code,
									Integer.parseInt(year)) + "_" + month2
							+ year.substring(2, 4) + ".xls"))
						observations.add(ob);
			}
			return ok(comparison.render(null, null));
		case "province":
			break;
		case "city":
			break;
		}

		return ok(index.render());
	}
}
