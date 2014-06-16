package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

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

	public static Result showCompareCommunity() throws NumberFormatException,
			InvalidFormatException, IOException {
		String communityId, year1, year2, month1, month2, year, communityA, communityB, month;
		AutonomousCommunity ac;
		DynamicForm form = Form.form().bindFromRequest();
		year1 = form.get("year_1");
		year2 = form.get("year_2");
		month1 = form.get("month_1");
		month2 = form.get("month_2");
		communityId = form.get("community");
		communityA = form.get("community_A");
		communityB = form.get("community_B");
		boolean isYears = year1 != null && year2 != null;
		boolean isMonth = month1!=null && month2!=null;
		boolean isComunities = communityA!=null && communityB!=null;
		if (isYears) {
			ac = AutonomousCommunity.findByCode(communityId);
			Observation obYear1 = calculateObservationCommunity(ac, year1, "12", "TOTAL");
			Observation obYear2 = calculateObservationCommunity(ac, year2, "12", "TOTAL");
			return ok(comparisonCommunity.render(obYear1, obYear2, ac, ac));
		} else if (isMonth) {
			year = form.get("year");
			ac = AutonomousCommunity.findByCode(communityId);
			Observation obMonth1 = calculateObservationCommunity(ac, year, month1, "TOTAL");
			Observation obMonth2 = calculateObservationCommunity(ac, year, month2, "TOTAL");
			return ok(comparisonCommunity.render(obMonth1, obMonth2, ac, ac));
		} else if(isComunities){
			AutonomousCommunity acA, acB;
			month = form.get("month");
			year = form.get("year");
			acA = AutonomousCommunity.findByCode(communityA);
			acB = AutonomousCommunity.findByCode(communityB);
			
			Observation obCommunityA = calculateObservationCommunity(acA, year, month, "TOTAL");
			Observation obCommunityB = calculateObservationCommunity(acB, year, month, "TOTAL");
			
			return ok(comparisonCommunity.render(obCommunityA, obCommunityB, acA, acB));
		}
		return ok();
	}
	
	public static Result showCompareProvince() throws NumberFormatException,
	InvalidFormatException, IOException {
		String provinceId, year1, year2, month1, month2, year, provinceA, provinceB, month;
		Province p;
		DynamicForm form = Form.form().bindFromRequest();
		year1 = form.get("year_1");
		year2 = form.get("year_2");
		month1 = form.get("month_1");
		month2 = form.get("month_2");
		provinceId = form.get("province");
		provinceA = form.get("province_A");
		provinceB = form.get("province_B");
		boolean isYears = year1 != null && year2 != null;
		boolean isMonth = month1!=null && month2!=null;
		boolean isProvinces = provinceA!=null && provinceB!=null;
		if (isYears) {
			p = Province.findByCode(provinceId);
			Observation obYear1 = calculateObservationProvince(p, year1, "12", "TOTAL");
			Observation obYear2 = calculateObservationProvince(p, year2, "12", "TOTAL");
			return ok(comparisonProvince.render(obYear1, obYear2, p, p));
		} else if (isMonth) {
			year = form.get("year");
			p = Province.findByCode(provinceId);
			Observation obMonth1 = calculateObservationProvince(p, year, month1, "TOTAL");
			Observation obMonth2 = calculateObservationProvince(p, year, month2, "TOTAL");
			return ok(comparisonProvince.render(obMonth1, obMonth2, p, p));
		} else if(isProvinces){
			Province pA, pB;
			month = form.get("month");
			year = form.get("year");
			pA = Province.findByCode(provinceA);
			pB = Province.findByCode(provinceB);
			
			Observation obProvinceA = calculateObservationProvince(pA, year, month, "TOTAL");
			Observation obProvinceB = calculateObservationProvince(pB, year, month, "TOTAL");
			
			return ok(comparisonProvince.render(obProvinceA, obProvinceB, pA, pB));
		}
		return ok();
	}
	
	public static Result showCompareSector() throws InvalidFormatException, IOException{
		String sectorA, sectorB, community, province, month, year;
		DynamicForm form = Form.form().bindFromRequest();
		sectorA = form.get("sector_A");
		sectorB = form.get("sector_B");
		community = form.get("community");
		province = form.get("province");
		month = form.get("month");
		year = form.get("year");
		boolean isCommunity = community!=null;
		boolean isProvince = province!=null;
		if(isCommunity){
			AutonomousCommunity ac = AutonomousCommunity.findByCode(community);
			Observation obSectorA = calculateObservationCommunity(ac, year, month, sectorA);
			Observation obSectorB = calculateObservationCommunity(ac, year, month, sectorB);
			return ok(comparisonSector(obSectorA, obSectorB, ac, null));
		}else if(isProvince){
			Province p = Province.findByCode(province);
			Observation obSectorA = calculateObservationProvince(p, year, month, sectorA);
			Observation obSectorB = calculateObservationProvince(p, year, month, sectorB);
			return ok(comparisonSector(obSectorA, obSectorB, null, p));
		}
		return ok();
	}
	
	private static Observation calculateObservationProvince(Province province, String year, String month, String indicatorName) throws NumberFormatException, InvalidFormatException, IOException{
		List<Observation> observations = new ArrayList<Observation>();
		ExcelReaderUnemploymentCities ex = new ExcelReaderUnemploymentCities();
		@SuppressWarnings("deprecation")
		Date date = new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, 1);
		Observation obProvince = new Observation(new Zone("PROVINCE"
				+ province.code, Province.findByCode(province.code), TypeZone.PROVINCE),
				Indicator.findByName(indicatorName), 0L, date);
		
		if (Integer.parseInt(month) < 9)
			for (Observation ob : ex.readProvince("public/data/MUNI_"
					+ ProvinceGenerator.getNameProvince(province.code,
					Integer.parseInt(year)) + "_0" + month
					+ year.substring(2, 4) + ".xls"))
				observations.add(ob);
		else
			for (Observation ob : ex.readProvince("public/data/MUNI_"
					+ ProvinceGenerator.getNameProvince(province.code,
					Integer.parseInt(year)) + "_" + month
					+ year.substring(2, 4) + ".xls"))
				observations.add(ob);
		for(Observation ob: observations)
			if(ob.indicator.name.equals(indicatorName))
				return ob;
		return obProvince;
	}

	private static Observation calculateObservationCommunity(AutonomousCommunity ac
			, String year, String month, String indicatorName) throws IOException,
			InvalidFormatException {
		
		List<Observation> observations = new ArrayList<Observation>();
		ExcelReaderUnemploymentCities ex = new ExcelReaderUnemploymentCities();
		@SuppressWarnings("deprecation")
		Date date = new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, 1);
		Observation obCommunity = new Observation(new Zone("COMMUNITY"
				+ ac.code, null, TypeZone.AUTONOMOUS_COMMUNITY),
				Indicator.findByName(indicatorName), 0L, date);
		
		for (Province province : Province.findByAutonomousCommunity(ac)) {
			if (Integer.parseInt(month) < 9)
				for (Observation ob : ex.readProvince("public/data/MUNI_"
						+ ProvinceGenerator.getNameProvince(province.code,
								Integer.parseInt(year)) + "_0" + month
						+ year.substring(2, 4) + ".xls"))
					observations.add(ob);
			else
				for (Observation ob : ex.readProvince("public/data/MUNI_"
						+ ProvinceGenerator.getNameProvince(province.code,
								Integer.parseInt(year)) + "_" + month
						+ year.substring(2, 4) + ".xls"))
					observations.add(ob);
		}
		
		for(Observation each: observations){
			if(each.indicator.name.equals(indicatorName)){
				obCommunity.obsValue += each.obsValue;
			}
		}
		return obCommunity;
		
	}
}
