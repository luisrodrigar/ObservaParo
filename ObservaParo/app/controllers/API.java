package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import models.*;
import play.Logger;
import play.data.*;
import play.i18n.Messages;
import play.mvc.*;
import play.libs.Json;
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import utils.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;

public class API extends Controller{
	
	// All Json files are HATEOAS
	
	public static Result provinces(){
		JsonNodeFactory factory = JsonNodeFactory.instance;
	    ArrayNode result = new ArrayNode(factory);
	    for(Province province : Province.allOrderByCode()){
	    	 ObjectNode provinceJson = Json.newObject();
	         provinceJson.put("code", province.code);
	         provinceJson.put("name", province.name);
	         ArrayNode links = new ArrayNode(factory);
	         ObjectNode self = Json.newObject();
	         self.put("rel", "self");
	         self.put("href", routes.API.province(province.code).absoluteURL(request()));
	         
	         links.add(self);
	         provinceJson.put("links", links);
	         result.add(provinceJson);
	    }
	    return ok(result);
	}
	
	public static Result province(String code){
JsonNodeFactory factory = JsonNodeFactory.instance;
    	Province province = Province.find.byId(code);
        ObjectNode provinceJson = Json.newObject();
        provinceJson.put("code", province.code);
        provinceJson.put("name", province.name);
        ArrayNode community = new ArrayNode(factory);
        ObjectNode ac = Json.newObject();
        ac.put("code", province.autonomousCommunity.code);
        ac.put("name", province.autonomousCommunity.name);
        ac.put("uri", routes.API.community(province.autonomousCommunity.code).absoluteURL(request()));
        community.add(ac);
        ArrayNode links = new ArrayNode(factory);
        ObjectNode self = Json.newObject();
        self.put("rel", "self");
        self.put("href", routes.API.province(province.code).absoluteURL(request()));
        links.add(self);
         
        ObjectNode parent = Json.newObject();
        parent.put("rel", "parent");
        parent.put("href", routes.API.provinces().absoluteURL(request()));
        links.add(parent);

        provinceJson.put("links", links);
    	return ok(provinceJson);
	}
}
