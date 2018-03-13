package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class Zone extends Model{
	private static final long serialVersionUID = 1L;

	public enum TypeZone{CITY, PROVINCE, AUTONOMOUS_COMMUNITY};
	
	@Id
	public String name;
	
	public TypeZone type;
	
	@ManyToOne
	public Province province;
	
	public Zone(String name, Province province, TypeZone type) {
		this.name = name;
		this.province = province;
		this.type=type;
	}

	public static Finder<String, Zone> find = new Finder<String, Zone>(String.class,
			Zone.class);

	public static List<Zone> all() {
		return find.all();
	}

	public static void create(Zone city) {
		if (Zone.findByName(city.name) == null) {
			city.save();
		}
	}

	public static void remove(String code) {
		find.ref(code).delete();
	}

	public static void deleteAll() {
		for (Zone c : all())
			c.delete();
	}

	public static Zone findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	public static List<Zone> findAllByProvince(Province province) {
		return find.where().eq("province", province).findList();
	}

	public static Zone findZoneProvince(Province province){
		return find.where().eq("province",province).eq("name", "PROVINCE"+province.code).findUnique();
	}
	
	public static JsonNode toJson(Zone city) {
		return Json.toJson(city);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((province == null) ? 0 : province.hashCode());
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
		Zone other = (Zone) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (province == null) {
			if (other.province != null)
				return false;
		} else if (!province.equals(other.province))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", province=" + province + "]";
	}
	
	
	
}
