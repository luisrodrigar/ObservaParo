package controllers;

import java.util.HashMap;
import java.util.Map;

import play.mvc.*;
import views.html.*;

public class Application extends Controller {

	private static Map<String, Object> map = new HashMap<String, Object>();
	
	public static void setValue(String key, Object value) {
	    map.put(key, value);
	}
	
	public static void setLong(String key, Long value) {
	    map.put(key, value);
	}
	
	public static Long getLong(String key) {
		if(key!=null && map.get(key)!=null)
			return Long.valueOf(map.get(key).toString());
		else
			return 0L;
	}

	public static void setError(String key, Object value){
		map.put(key, value);
	}
	
	public static Object getValue(String key) {
	    return map.get(key);
	}
	
    public static Result index() {
        return ok(index.render());
    }

}
