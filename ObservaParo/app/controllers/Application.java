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
	
	@SuppressWarnings("deprecation")
	public static Result showComparePeople() throws InvalidFormatException, IOException{
		String peopleA, peopleB, communityA, communityB, provinceA, provinceB, month, year;
		DynamicForm form = Form.form().bindFromRequest();
		peopleA = form.get("people_A");
		peopleB = form.get("people_B");
		communityA = form.get("community_A");
		provinceA = form.get("province_A");
		communityB = form.get("community_B");
		provinceB = form.get("province_B");
		month = form.get("month");
		year = form.get("year");
		Observation obHombresA, obHombresB, obMujeresA, obMujeresB, obA = null, obB=null;
		boolean isCommunity = communityA!=null && communityB!=null;
		boolean isProvince = provinceA!=null && provinceB!=null;
		if(isCommunity){
			AutonomousCommunity acA = AutonomousCommunity.findByCode(communityA);
			AutonomousCommunity acB = AutonomousCommunity.findByCode(communityB);
			if(peopleA.equals("HOMBRES")){
				Observation obHMENO25 = calculateObservationCommunity(acA, year, month, "HOMBRES<25");
				Observation obH2544 = calculateObservationCommunity(acA, year, month, "HOMBRES25-44");
				Observation obHMAY45 = calculateObservationCommunity(acA, year, month, "HOMBRES>=45");
				obHombresA = new Observation(new Zone("COMMUNITY"+acA.code, null, TypeZone.AUTONOMOUS_COMMUNITY), new Indicator("HOMBRES"), 0L, new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, 1));
				obHombresA.obsValue = obHMENO25.obsValue + obH2544.obsValue + obHMAY45.obsValue;
				obA = obHombresA;
			}else if(peopleA.equals("MUJERES")){
				Observation obMMENO25 = calculateObservationCommunity(acA, year, month, "MUJERES<25");
				Observation obM2544 = calculateObservationCommunity(acA, year, month, "MUJERES25-44");
				Observation obMMAY45 = calculateObservationCommunity(acA, year, month, "MUJERES>=45");
				obMujeresA = new Observation(new Zone("COMMUNITY"+acA.code, null, TypeZone.AUTONOMOUS_COMMUNITY), new Indicator("MUJERES"), 0L, new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, 1));
				obMujeresA.obsValue = obMMENO25.obsValue + obM2544.obsValue + obMMAY45.obsValue;
				obA = obMujeresA;
			}
			if(peopleB.equals("HOMBRES")){
				Observation obHMENO25 = calculateObservationCommunity(acB, year, month, "HOMBRES<25");
				Observation obH2544 = calculateObservationCommunity(acB, year, month, "HOMBRES25-44");
				Observation obHMAY45 = calculateObservationCommunity(acB, year, month, "HOMBRES>=45");
				obHombresB = new Observation(new Zone("COMMUNITY"+acB.code, null, TypeZone.AUTONOMOUS_COMMUNITY), new Indicator("HOMBRES"), 0L, new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, 1));
				obHombresB.obsValue = obHMENO25.obsValue + obH2544.obsValue + obHMAY45.obsValue;
				obB = obHombresB;
			}else if(peopleB.equals("MUJERES")){
				Observation obMMENO25 = calculateObservationCommunity(acB, year, month, "MUJERES<25");
				Observation obM2544 = calculateObservationCommunity(acB, year, month, "MUJERES25-44");
				Observation obMMAY45 = calculateObservationCommunity(acB, year, month, "MUJERES>=45");
				obMujeresB = new Observation(new Zone("COMMUNITY"+acB.code, null, TypeZone.AUTONOMOUS_COMMUNITY), new Indicator("MUJERES"), 0L, new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, 1));
				obMujeresB.obsValue = obMMENO25.obsValue + obM2544.obsValue + obMMAY45.obsValue;
				obB = obMujeresB;
			}if(obA!=null && obB!=null){
				return ok(comparisonPeople.render(obA, obB, acA, acB, null,null));
			}else if(obA!=null && obB==null){
				obB = calculateObservationCommunity(acB, year, month, peopleB);
				return ok(comparisonPeople.render(obA, obB, acA, acB, null, null));
			}else if(obA==null && obB!=null){
				obA = calculateObservationCommunity(acA, year, month, peopleA);
				return ok(comparisonPeople.render(obA, obB, acA, acB, null, null));
			}else{
				obA = calculateObservationCommunity(acA, year, month, peopleA);
				obB = calculateObservationCommunity(acB, year, month, peopleB);
				return ok(comparisonPeople.render(obA, obB, acA, acB, null, null));
			}
		}else if(isProvince){
			Province pA = Province.findByCode(provinceA);
			Province pB = Province.findByCode(provinceB);
			if(peopleA.equals("HOMBRES")){
				Observation obHMENO25 = calculateObservationProvince(pA, year, month, "HOMBRES<25");
				Observation obH2544 = calculateObservationProvince(pA, year, month, "HOMBRES25-44");
				Observation obHMAY45 = calculateObservationProvince(pA, year, month, "HOMBRES>=45");
				obHombresA = new Observation(new Zone("PROVINCE"+pA.code, Province.findByCode(pA.code), TypeZone.PROVINCE), new Indicator("HOMBRES"), 0L, new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, 1));
				obHombresA.obsValue = obHMENO25.obsValue + obH2544.obsValue + obHMAY45.obsValue;
				obA = obHombresA;
			}else if(peopleA.equals("MUJERES")){
				Observation obMMENO25 = calculateObservationProvince(pA, year, month, "MUJERES<25");
				Observation obM2544 = calculateObservationProvince(pA, year, month, "MUJERES25-44");
				Observation obMMAY45 = calculateObservationProvince(pA, year, month, "MUJERES>=45");
				obMujeresA = new Observation(new Zone("PROVINCE"+pA.code, Province.findByCode(pA.code), TypeZone.PROVINCE), new Indicator("MUJERES"), 0L, new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, 1));
				obMujeresA.obsValue = obMMENO25.obsValue + obM2544.obsValue + obMMAY45.obsValue;
				obA = obMujeresA;
			}
			if(peopleB.equals("HOMBRES")){
				Observation obHMENO25 = calculateObservationProvince(pB, year, month, "HOMBRES<25");
				Observation obH2544 = calculateObservationProvince(pB, year, month, "HOMBRES25-44");
				Observation obHMAY45 = calculateObservationProvince(pB, year, month, "HOMBRES>=45");
				obHombresB = new Observation(new Zone("PROVINCE"+pB.code, Province.findByCode(pB.code), TypeZone.PROVINCE), new Indicator("HOMBRES"), 0L, new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, 1));
				obHombresB.obsValue = obHMENO25.obsValue + obH2544.obsValue + obHMAY45.obsValue;
				obB = obHombresB;
			}else if(peopleB.equals("MUJERES")){
				Observation obMMENO25 = calculateObservationProvince(pB, year, month, "MUJERES<25");
				Observation obM2544 = calculateObservationProvince(pB, year, month, "MUJERES25-44");
				Observation obMMAY45 = calculateObservationProvince(pB, year, month, "MUJERES>=45");
				obMujeresB = new Observation(new Zone("PROVINCE"+pB.code, Province.findByCode(pB.code), TypeZone.PROVINCE), new Indicator("MUJERES"), 0L, new Date(Integer.parseInt(year)-1900, Integer.parseInt(month)-1, 1));
				obMujeresB.obsValue = obMMENO25.obsValue + obM2544.obsValue + obMMAY45.obsValue;
				obB = obMujeresB;
			}if(obA!=null && obB!=null){
				return ok(comparisonPeople.render(obA, obB, null, null, pA, pB));
			}else if(obA!=null && obB==null){
				obB = calculateObservationProvince(pB, year, month, peopleB);
				return ok(comparisonPeople.render(obA, obB, null, null, pA, pB));
			}else if(obA==null && obB!=null){
				obA = calculateObservationProvince(pA, year, month, peopleA);
				return ok(comparisonPeople.render(obA, obB, null, null, pA, pB));
			}else{
				obA = calculateObservationProvince(pA, year, month, peopleA);
				obB = calculateObservationProvince(pB, year, month, peopleB);
				return ok(comparisonPeople.render(obA, obB, null, null, pA, pB));
			}
		}
		return ok();
	}
	
	public static Result showCompareSector() throws InvalidFormatException, IOException{
		String sectorA, sectorB, communityA, communityB, provinceA, provinceB, month, year;
		DynamicForm form = Form.form().bindFromRequest();
		sectorA = form.get("sector_A");
		sectorB = form.get("sector_B");
		communityA = form.get("community_A");
		provinceA = form.get("province_A");
		communityB = form.get("community_B");
		provinceB = form.get("province_B");
		month = form.get("month");
		year = form.get("year");
		boolean isCommunity = communityA!=null && communityB!=null;
		boolean isProvince = provinceA!=null && provinceB!=null;
		if(isCommunity){
			AutonomousCommunity acA = AutonomousCommunity.findByCode(communityA);
			AutonomousCommunity acB = AutonomousCommunity.findByCode(communityB);
			Province p = null;
			Observation obSectorA = calculateObservationCommunity(acA, year, month, sectorA);
			Observation obSectorB = calculateObservationCommunity(acB, year, month, sectorB);
			return ok(comparisonSector.render(obSectorA,obSectorB,acA, acB,p,p));
		}else if(isProvince){
			Province pA = Province.findByCode(provinceA);
			Province pB = Province.findByCode(provinceB);
			AutonomousCommunity ac = null;
			Observation obSectorA = calculateObservationProvince(pA, year, month, sectorA);
			Observation obSectorB = calculateObservationProvince(pB, year, month, sectorB);
			return ok(comparisonSector.render(obSectorA, obSectorB, ac, ac, pA, pB));
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
		
		if (Integer.parseInt(month) <= 9)
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
			if (Integer.parseInt(month) <= 9)
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
