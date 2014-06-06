package controllers;

import models.*;
import play.mvc.*;
import play.libs.Json;
import com.fasterxml.jackson.databind.node.*;

public class API extends Controller {

	// All Json files are HATEOAS

	public static Result provinces() {
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ArrayNode result = new ArrayNode(factory);
		for (Province province : Province.allOrderByCode()) {
			ObjectNode provinceJson = Json.newObject();
			provinceJson.put("code", province.code);
			provinceJson.put("name", province.name);
			ArrayNode community = new ArrayNode(factory);
			ObjectNode ac = Json.newObject();
			ac.put("code", province.autonomousCommunity.code);
			ac.put("name", province.autonomousCommunity.name);
			ac.put("uri", routes.API.community(province.autonomousCommunity.code)
					.absoluteURL(request()));
			community.add(ac);
			ArrayNode links = new ArrayNode(factory);
			ObjectNode self = Json.newObject();
			self.put("rel", "self");
			self.put("href",
					routes.API.province(province.code).absoluteURL(request()));

			links.add(self);
			provinceJson.put("community", community);
			provinceJson.put("links", links);
			result.add(provinceJson);
		}
		return ok(result);
	}

	public static Result province(String code) {
		JsonNodeFactory factory = JsonNodeFactory.instance;
		Province province = Province.find.byId(code);
		ObjectNode provinceJson = Json.newObject();
		provinceJson.put("code", province.code);
		provinceJson.put("name", province.name);
		ArrayNode community = new ArrayNode(factory);
		ObjectNode ac = Json.newObject();
		AutonomousCommunity a = AutonomousCommunity.findByName(province.autonomousCommunity.code);
		ac.put("code", a.code);
		ac.put("name", a.name);
		ac.put("uri", routes.API.community(a.code)
				.absoluteURL(request()));
		community.add(ac);
		ArrayNode links = new ArrayNode(factory);
		ObjectNode self = Json.newObject();
		self.put("rel", "self");
		self.put("href",
				routes.API.province(province.code).absoluteURL(request()));
		links.add(self);

		ObjectNode parent = Json.newObject();
		parent.put("rel", "parent");
		parent.put("href", routes.API.provinces().absoluteURL(request()));
		links.add(parent);
		
		provinceJson.put("community", community);
		provinceJson.put("links", links);
		return ok(provinceJson);
	}

	public static Result cities() {
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ArrayNode result = new ArrayNode(factory);
		for (City city : City.all()) {
			ObjectNode cityJson = Json.newObject();
			cityJson.put("name", city.name);
			ArrayNode province = new ArrayNode(factory);
			ObjectNode p = Json.newObject();
			p.put("code", city.province.code);
			p.put("name", city.province.name);
			p.put("uri",
					routes.API.province(city.province.code).absoluteURL(
							request()));
			province.add(p);
			ArrayNode links = new ArrayNode(factory);
			ObjectNode self = Json.newObject();
			self.put("rel", "self");
			self.put("href", routes.API.city(city.name).absoluteURL(request()));

			links.add(self);
			cityJson.put("province", province);
			cityJson.put("links", links);
			result.add(cityJson);
		}
		return ok(result);
	}

	public static Result city(String name) {
		JsonNodeFactory factory = JsonNodeFactory.instance;
		City city = City.find.byId(name);
		ObjectNode provinceJson = Json.newObject();
		provinceJson.put("name", city.name);
		ArrayNode p = new ArrayNode(factory);
		ObjectNode province = Json.newObject();
		province.put("code", city.province.code);
		province.put("name", city.province.name);
		province.put("uri", routes.API.province(city.province.code)
				.absoluteURL(request()));
		p.add(province);
		ArrayNode links = new ArrayNode(factory);
		ObjectNode self = Json.newObject();
		self.put("rel", "self");
		self.put("href",
				routes.API.city(city.name).absoluteURL(request()));
		links.add(self);

		ObjectNode parent = Json.newObject();
		parent.put("rel", "parent");
		parent.put("href", routes.API.cities().absoluteURL(request()));
		links.add(parent);

		provinceJson.put("province", p);
		provinceJson.put("links", links);
		return ok(provinceJson);
	}

	public static Result communities() {
		JsonNodeFactory factory = JsonNodeFactory.instance;
		ArrayNode result = new ArrayNode(factory);
		for (AutonomousCommunity autonomousCommunity : AutonomousCommunity.allOrderByCode()) {
			ObjectNode communityJson = Json.newObject();
			communityJson.put("code", autonomousCommunity.code);
			communityJson.put("name", autonomousCommunity.name);
			ArrayNode links = new ArrayNode(factory);
			ObjectNode self = Json.newObject();
			self.put("rel", "self");
			self.put("href",
					routes.API.community(autonomousCommunity.code).absoluteURL(request()));

			links.add(self);
			communityJson.put("links", links);
			result.add(communityJson);
		}
		return ok(result);
	}

	public static Result community(String code) {
		JsonNodeFactory factory = JsonNodeFactory.instance;
		AutonomousCommunity autonomousCommunity = AutonomousCommunity.find.byId(code);
		ObjectNode autonomousCommunityJson = Json.newObject();
		autonomousCommunityJson.put("code", autonomousCommunity.code);
		autonomousCommunityJson.put("name", autonomousCommunity.name);
		ArrayNode links = new ArrayNode(factory);
		ObjectNode self = Json.newObject();
		self.put("rel", "self");
		self.put("href",
				routes.API.community(autonomousCommunity.name).absoluteURL(request()));
		links.add(self);

		ObjectNode parent = Json.newObject();
		parent.put("rel", "parent");
		parent.put("href", routes.API.communities().absoluteURL(request()));
		links.add(parent);

		autonomousCommunityJson.put("links", links);
		return ok(autonomousCommunityJson);
	}

	public static Result observations() {
		return ok(views.html.community.render());
	}
}
