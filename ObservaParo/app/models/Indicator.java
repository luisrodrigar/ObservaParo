package models;

import java.util.List;
import play.db.ebean.*;

import javax.persistence.*;

@Entity
public class Indicator extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public String name;

	public Indicator(String name) {
		this.name = name;
	}

	public static Finder<String, Indicator> find = new Finder<String, Indicator>(
			String.class, Indicator.class);

	public static List<Indicator> all() {
		return find.all();
	}

	public static void create(Indicator indicator) {
		if (Indicator.findByName(indicator.name) == null) {
			indicator.save();
		}
	}

	public static void remove(String id) {
		find.ref(id).delete();
	}

	public static void deleteAll() {
		for (Indicator ind : all())
			ind.delete();
	}

	public static Indicator findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}

	@Override
	public String toString() {
		return "Indicator [name=" + name + "]";
	}

	
	
}