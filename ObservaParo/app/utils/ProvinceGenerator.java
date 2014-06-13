package utils;

import models.Province;

public class ProvinceGenerator {
	public static Province createProvince(String file){
		String provinceName = file.split("/")[2];
		provinceName = provinceName.substring(5,provinceName.length()-8);
		if(provinceName.charAt(provinceName.length()-1)!='0')
			provinceName = provinceName.split("1")[0];
		provinceName = provinceName.substring(0, provinceName.length() - 1);
		String code = getCode(provinceName);
		return new Province(code, provinceName);
	}

	/**
	 * List of Provinces and their corresponding codes.
	 * Codes have been got from http://www.ine.es/en/daco/daco42/codmun/cod_provincia_en.htm website 
	 * @param provinceName
	 * @return provinceCode
	 */
	private static String getCode(String provinceName) {
		switch(provinceName){
			case "ALBACETE":
				return "02";
			case "ALICANTE":
				return "03";
			case "ALMERIA":
				return "04";
			case "ALAVA": case "ARABA":
				return "01";
			case "ASTURIAS":
				return "33";
			case "AVILA":
				return "05";
			case "BADAJOZ":
				return "06";
			case "BALEARES":
				return "07";
			case "BARCELONA":
				return "08";
			case "VIZCAYA": case "BIZKAIA":
				return "48";
			case "BURGOS":
				return "09";
			case "CACERES":
				return "10";
			case "CADIZ":
				return "11";
			case "CANTABRIA":
				return "39";
			case "CASTELLON":
				return "12";
			case "CIUDAD_REAL":
				return "13";
			case "CORDOBA":
				return "14";
			case "A_CORUNA":
				return "15";
			case "CUENCA":
				return "16";
			case "GUIPUZCOA": case "GIPUZKOA":
				return "20";
			case "GIRONA":
				return "17";
			case "GRANADA":
				return "18";
			case "GUADALAJARA":
				return "19";
			case "HUELVA":
				return "21";
			case "HUESCA":
				return "22";
			case "JAEN":
				return "23";
			case "LEON":
				return "24";
			case "LLEIDA":
				return "25";
			case "LUGO":
				return "27";
			case "MADRID":
				return "28";
			case "MALAGA":
				return "29";
			case "MURCIA":
				return "30";
			case "NAVARRA":
				return "31";
			case "OURENSE":
				return "32";
			case "PALENCIA":
				return "34";
			case "LAS_PALMAS":
				return "35";
			case "PONTEVEDRA":
				return "36";
			case "LA_RIOJA":
				return "26";
			case "SALAMANCA":
				return "37";
			case "TENERIFE":
				return "38";
			case "SEGOVIA":
				return "40";
			case "SEVILLA":
				return "41";
			case "SORIA":
				return "42";
			case "TARRAGONA":
				return "43";
			case "TERUEL":
				return "44";
			case "TOLEDO":
				return "45";
			case "VALENCIA":
				return "46";
			case "VALLADOLID":
				return "47";
			case "ZAMORA":
				return "49";
			case "ZARAGOZA":
				return "50";
			case "CEUTA":
				return "51";
			case "MELILLA":
				return "52";
		}
		return null;
	}
	
	public static String getNameProvince(String provinceCode, int year) {
		switch(provinceCode){
			case "02":
				return "ALBACETE";
			case "03":
				return "ALICANTE";
			case "04":
				return "ALMERIA";
			case "01":
				if(year>2011)
					return "ARABA";
				else
					return "ALAVA";
			case "33":
				return "ASTURIAS";
			case "05":
				return "AVILA";
			case "06":
				return "BADAJOZ";
			case "07":
				return "BALEARES";
			case "08":
				return "BARCELONA";
			case "48":
				if(year>2011)
					return "BIZKAIA";
				else
					return "VIZCAYA" ;
			case "09":
				return "BURGOS";
			case "10":
				return "CACERES";
			case "11":
				return "CADIZ";
			case "39":
				return "CANTABRIA";
			case "12":
				return "CASTELLON";
			case "13":
				return "CIUDAD_REAL";
			case "14":
				return "CORDOBA";
			case "15":
				return "A_CORUNA";
			case "16":
				return "CUENCA";
			case "20":
				if(year>2011)
					return "GIPUZKOA";
				else
					return "GUIPUZCOA";
			case "17":
				return "GIRONA";
			case "18":
				return "GRANADA";
			case "19":
				return "GUADALAJARA";
			case "21":
				return "HUELVA";
			case "22":
				return "HUESCA";
			case "23":
				return "JAEN";
			case "24":
				return "LEON";
			case "25":
				return "LLEIDA";
			case "27":
				return "LUGO";
			case "28":
				return "MADRID";
			case "29":
				return "MALAGA";
			case "30":
				return "MURCIA";
			case "31":
				return "NAVARRA";
			case "32":
				return "OURENSE";
			case "34":
				return "PALENCIA";
			case "35":
				return "LAS_PALMAS";
			case "36":
				return "PONTEVEDRA";
			case "26":
				return "LA_RIOJA";
			case "37":
				return "SALAMANCA";
			case "38":
				return "TENERIFE";
			case "40":
				return "SEGOVIA";
			case "41":
				return "SEVILLA";
			case "42":
				return "SORIA";
			case "43":
				return "TARRAGONA";
			case "44":
				return "TERUEL";
			case "45":
				return "TOLEDO";
			case "46":
				return "VALENCIA";
			case "47":
				return "VALLADOLID";
			case "49":
				return "ZAMORA";
			case "50":
				return "ZARAGOZA";
			case "51":
				return "CEUTA";
			case "52":
				return "MELILLA";
		}
		return null;
	}
}
