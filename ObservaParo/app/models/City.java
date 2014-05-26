package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class City extends Model{
	private static final long serialVersionUID = 1L;

	@Id
	public String name;
	
	@ManyToOne
	public Province province;
	
	public City(String name) {
		this.name = name;
	}

	public static Finder<String, City> find = new Finder<String, City>(String.class,
			City.class);

	public static List<City> all() {
		return find.all();
	}

	public static void create(City city) {
		if (Province.findByName(city.name) == null) {
			city.save();
		}
	}

	public static void remove(String code) {
		find.ref(code).delete();
	}

	public static void deleteAll() {
		for (City c : all())
			c.delete();
	}

	public static City findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}

	public static JsonNode toJson(City city) {
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
		City other = (City) obj;
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
	
}
