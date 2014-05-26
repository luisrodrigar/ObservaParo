package models;

import java.util.List;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.databind.JsonNode;
import play.db.ebean.Model;
import play.libs.Json;

public class AutonomousCommunity extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public String code;

	public String nombre;

	@OneToMany(mappedBy = "autonomousCommnunity")
	public List<Province> provinces;

	public AutonomousCommunity(String code, String nombre, Province province) {
		this.code = code;
		this.nombre = nombre;
	}

	public void addProvince(Province province) {
		if (!provinces.contains(province))
			provinces.add(province);
	}

	public static Finder<String, AutonomousCommunity> find = new Finder<String, AutonomousCommunity>(
			String.class, AutonomousCommunity.class);

	public static List<AutonomousCommunity> all() {
		return find.all();
	}

	public static void create(AutonomousCommunity aCommunity) {
		if (Province.findByName(aCommunity.code) == null) {
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

}
