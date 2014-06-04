package utils;

import models.AutonomousCommunity;
import models.Province;

public class AutonomousCommunityGenerator {
	
	/**
	 * Linking autonomous community with their own code.
	 * List of Autonomous Communities and Cities and their corresponding codes
	 * have been got from http://www.ine.es/en/daco/daco42/codmun/cod_ccaa_en.htm website
	 * @param province
	 * @return autonomous community
	 */
	public static AutonomousCommunity createComunityByProvince(Province province){
		switch(province.code){
			case "15": case "27": case "32": case "36":
				return new AutonomousCommunity("12", "GALICIA");
			case "33":
				return new AutonomousCommunity("03", "PRINCIPADO DE ASTURIAS");
			case "39":
				return new AutonomousCommunity("06", "CANTABRIA");
			case "01": case "48": case "20":
				return new AutonomousCommunity("16", "PAIS VASCO");
			case "26":
				return new AutonomousCommunity("17", "LA RIOJA");
			case "31":
				return new AutonomousCommunity("15", "COMUNIDAD FORAL DE NAVARRA");
			case "51":
				return new AutonomousCommunity("18", "CEUTA");
			case "52":
				return new AutonomousCommunity("19", "MELILLA");
			case "30":
				return new AutonomousCommunity("14", "MURCIA");
			case "28":
				return new AutonomousCommunity("13", "COMUNIDAD DE MADRID");
			case "10": case "06":
				return new AutonomousCommunity("11", "EXTREMADURA");
			case "12": case "03": case "46":
				return new AutonomousCommunity("10", "COMUNIDAD VALENCIANA");
			case "25": case "43": case "17": case "08":
				return new AutonomousCommunity("09", "CATALUNA");
			case "13": case "02": case "45": case "19": case "16":
				return new AutonomousCommunity("08", "CASTILLA - LA MANCHA");
			case "24": case "40": case "37": case "09": case "34": case "42": case "47": case "05": case "49":
				return new AutonomousCommunity("07", "CASTILLA Y LEON");
			case "38": case "35":
				return new AutonomousCommunity("05", "CANARIAS");
			case "07":
				return new AutonomousCommunity("04", "ISLAS BALEARES");
			case "50": case "44": case "22":
				return new AutonomousCommunity("02", "ARAGON");
			case "04": case "11": case "14": case "18": case "21": case "23": case "29": case "41":
				return new AutonomousCommunity("01", "ANDALUCIA");
		}
		return null;
	}
}
