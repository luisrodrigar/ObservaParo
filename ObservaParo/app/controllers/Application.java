package controllers;

import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
    public static Result index() {
        return ok(index.render());
    }

    public static Result community() {
        return ok(index.render());
    }
    
    public static Result province() {
        return ok(index.render());
    }
    
    public static Result sector() {
        return ok(index.render());
    }
    
    public static Result people() {
        return ok(index.render());
    }
}
