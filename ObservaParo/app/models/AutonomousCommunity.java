package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.JsonNode;

import play.cache.Cache;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.libs.Json;

@Entity
public class AutonomousCommunity extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public String code;
	@Required
	public String name;


	public AutonomousCommunity(String code, String nombre) {
		this.code = code;
		this.name = nombre;
	}
	
	public String getSrcImg(){
		return "images/communities/" + code + ".png";
	}
	
	public String getCode(){
		return code;
	}
	
	public String getName(){
		return name;
	}

	public static Finder<String, AutonomousCommunity> find = new Finder<String, AutonomousCommunity>(
			String.class, AutonomousCommunity.class);

	public static List<AutonomousCommunity> all() {
		return find.all();
	}
	
	public static List<AutonomousCommunity> allOrderByCode(){
		return find.where().orderBy("code asc").findList();
	}

	public static void create(AutonomousCommunity aCommunity) {
		if (AutonomousCommunity.findByName(aCommunity.code) == null) {
			aCommunity.save();
		}
	}

	public static void remove(String code) {
		find.ref(code).delete();
	}

	public static void deleteAll() {
		for (AutonomousCommunity a : all())
			a.delete();
	}

	public static AutonomousCommunity findByName(String code) {
		return find.where().eq("code", code).findUnique();
	}

	public static JsonNode toJson(AutonomousCommunity aCommunity) {
		return Json.toJson(aCommunity);
	}
	
	public static void cachedDataAboutTotalUnemploymentAC() {
		Map<String, Long> map = new HashMap<String, Long>();
		for (AutonomousCommunity eachAC : AutonomousCommunity.all()) {
			if (map.get(eachAC.code) == null) {
				Long sum = 0L;
				for (Province eachP : Province
						.findByAutonomousCommunity(eachAC)) {
					for(Zone eachC: Zone.findAllByProvince(eachP)){
						for (Observation ob : Observation.findByZoneAndIndicator(eachC, "TOTAL")) {
							sum += ob.obsValue;
						}
					}
				}
				Cache.set(eachAC.code, sum);
			}
		}
	}

	@Override
	public String toString() {
		return "AutonomousCommunity [code=" + code + ", name=" + name + "]";
	}

}
