package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import play.db.ebean.Model;
import play.libs.Json;
import com.fasterxml.jackson.databind.JsonNode;

@Entity
public class Province extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public String code;

	public String name;

	@OneToMany(mappedBy = "province")
	public List<City> cities;
	
	@ManyToOne
	public AutonomousCommunity autonomousCommunity;

	public Province(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static Finder<String, Province> find = new Finder<String, Province>(String.class,
			Province.class);

	public static List<Province> all() {
		return find.all();
	}

	public static void create(Province country) {
		if (Province.findByName(country.name) == null) {
			country.save();
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

	public static JsonNode toJson(Province country) {
		return Json.toJson(country);
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

}
