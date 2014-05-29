package utils;

import models.Province;

public class ProvinceGenerator {
	public static Province createProvince(String file){
		String provinceName = file.split("/")[2].split("0")[0]
				.substring(5);
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
			case "ALAVA":
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
			case "VIZCAYA":
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
			case "GUIPUZCOA":
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
}
