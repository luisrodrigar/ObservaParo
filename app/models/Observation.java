package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Observation extends Model {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	public Long id;

	@Required
	public Long obsValue;

	@ManyToOne
	public Indicator indicator;

	@ManyToOne(fetch=FetchType.EAGER)
	public Zone zone;
	
	@Temporal(TemporalType.DATE)
	public Date date;

	public static Finder<Long, Observation> find = new Finder<Long, Observation>(
			Long.class, Observation.class);

	public Observation(Zone zone, Indicator indicator, Long value, Date date) {
		this.zone = zone;
		this.indicator = indicator;
		this.obsValue = value;
		this.date = date;
	}

	public Observation(String zoneName, String indicatorCode, Long value) {
		this.zone = Zone.find.ref(zoneName);
		this.indicator = Indicator.find.ref(indicatorCode);
		this.obsValue = value;
	}

	public static List<Observation> all() {
		return find.all();
	}

	public static Observation create(String code, String indicator, Long value) {
		Observation observation = new Observation(code, indicator, value);
		observation.save();
		return observation;
	}

	public static void delete(Long id) {
		find.ref(id).delete();
	}

	public static void deleteAll() {
		for (Observation obs : all()) {
			obs.delete();
		}
	}

	public static Double average(List<Observation> observations, String indicator) {
		Double sum = 0.0;
		int count = 0;
		for (Observation obs : observations) {
			if(indicator!=null && obs.indicator.name.equals(indicator)){
				count++;
				sum += obs.obsValue;
			}else if(indicator==null){
				count++;
				sum += obs.obsValue;
			}
		}
		return sum / count;
	}
	
	public static Integer sum(List<Observation> observations, String indicator){
		int sum = 0;
		for (Observation obs : observations) {
			if(indicator!=null && obs.indicator.name.equals(indicator)){
				sum += obs.obsValue;
			}else if(indicator==null)
				sum += obs.obsValue;
		}
		return sum;
	}
	
	public static Integer count(List<Observation> observations, String indicator){
		int count = 0;
		for (Observation obs : observations) {
			if(indicator!=null && obs.indicator.name.equals(indicator)){
				count++;
			}else if(indicator==null)
				count++;
		}
		return count;
	}

	public static List<Observation> filterByIndicatorName(String indicatorName,
			List<Observation> observations) {
		List<Observation> result = new ArrayList<Observation>();
		for (Observation obs : observations) {
			if (obs.indicator.name == indicatorName)
				result.add(obs);
		}
		return result;
	}

	public static List<Observation> findByIndicatorName(String indicatorCode) {
		Indicator ind = Indicator.findByName(indicatorCode);
		List<Observation> result = find.where().eq("indicator", ind).findList();
		return result;
	}

	public static List<Observation> findByZoneAndIndicator(Zone zone, String indicator) {
		Indicator i = Indicator.findByName(indicator);
		List<Observation> result = find.where().eq("zone", zone).eq("indicator", i).findList();
		return result;
	}
	
	public static List<Observation> findByZoneAndIndicatorAndDate(Zone zone, String indicator, Date date) {
		Indicator i = Indicator.findByName(indicator);
		List<Observation> result = find.where().eq("zone", zone).eq("indicator", i).eq("date", date).findList();
		return result;
	}

	@Override
	public String toString() {
		return "Observation [id=" + id + ", obsValue=" + obsValue
				+ ", indicator=" + indicator + ", zone=" + zone + "]";
	}

}