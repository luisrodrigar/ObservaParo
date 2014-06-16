package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.cache.Cache;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.libs.Json;
import utils.AutonomousCommunityGenerator;

import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class Province extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public String code;

	@Required
	public String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	public AutonomousCommunity autonomousCommunity;

	public Province(String code, String name) {
		this.code = code;
		this.name = name;
		this.autonomousCommunity = AutonomousCommunityGenerator.createComunityByProvince(this);
	}

	public String getSrcImg(){
		return "images/provinces/" + code + ".png";
	}
	
	public static Finder<String, Province> find = new Finder<String, Province>(String.class,
			Province.class);

	public static List<Province> all() {
		return find.all();
	}

	public static void create(Province province) {
		if (Province.findByCode(province.code) == null) {
			province.save();
		}
	}

	public static void remove(String code) {
		find.ref(code).delete();
	}

	public static void deleteAll() {
		for (Province c : all())
			c.delete();
	}

	public static Province findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}

	public static Province findByCode(String code) {
		return find.byId(code);
	}
	
	public static List<Province> findByAutonomousCommunity(AutonomousCommunity aCommunity){
		return find.where().eq("autonomousCommunity", aCommunity).orderBy("code asc").findList();
	}

	public static JsonNode toJson(Province country) {
		return Json.toJson(country);
	}
	
	public static List<Province> allOrderByCode(){
		return find.where().orderBy("code asc").findList();
	}
	
	public static void cachedDataAboutTotalUnemploymentProvinces() {
		Map<String, Long> map = new HashMap<String, Long>();
		for (Province eachP : Province.all()) {
			if (map.get(eachP.code) == null) {
				Long sum = 0L;
				for(Zone eachC: Zone.findAllByProvince(eachP)){
					for (Observation ob : Observation.findByZoneAndIndicator(eachC, "TOTAL")) {
						sum += ob.obsValue;
					}
				}
				Cache.set(eachP.code, sum);
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Province other = (Province) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Province [code=" + code + ", name=" + name
				+ ", autonomousCommunity=" + autonomousCommunity + "]";
	}
	
	

}
