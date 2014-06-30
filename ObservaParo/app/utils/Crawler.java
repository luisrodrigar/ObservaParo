package utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Crawler {
	public final static String url = "https://www.sepe.es/contenido/estadisticas/datos_estadisticos/municipios/";
	public final static String[] month = { "enero", "febrero", "marzo",
			"abril", "mayo", "junio", "julio", "agosto", "septiembre",
			"octubre", "noviembre", "diciembre" };
	public final static String[] year = { "2005", "2006", "2007", "2008",
			"2009", "2010", "2011", "2012", "2013", "2014" };
	public final static String[] province = { "A_CORUNA", "ALAVA", "ARABA", "ALBACETE",
			"ALICANTE", "ALMERIA", "ASTURIAS", "AVILA", "BADAJOZ", "BARCELONA",
			"BURGOS", "CACERES", "CADIZ", "CANTABRIA", "CASTELLON", "CEUTA",
			"CIUDAD_REAL", "CORDOBA", "CUENCA", "GIRONA", "GRANADA",
			"GUADALAJARA", "GUIPUZCOA", "GIPUZKOA", "HUELVA", "HUESCA", "BALEARES", "JAEN",
			"LA_RIOJA", "LAS_PALMAS", "LEON", "LLEIDA", "LUGO", "MADRID",
			"MALAGA", "MELILLA", "MURCIA", "NAVARRA", "OURENSE", "PALENCIA",
			"PONTEVEDRA", "SALAMANCA", "TENERIFE", "SEGOVIA", "SEVILLA",
			"SORIA", "TARRAGONA", "TERUEL", "TOLEDO", "VALENCIA", "VALLADOLID",
			"VIZCAYA", "BIZKAIA", "ZAMORA", "ZARAGOZA" };
	public final static String storeFiles = "public/data";

	public static Crawler getInstance(){
		return new Crawler();
	}
	
	public void download() throws IOException {
		File file = new File("public/data");
		for (int i = 0; i < year.length; i++) {
			for (int j = 0; j < month.length; j++) {
				if (i != 0 || j > 3) {
					for (int k = 0; k < province.length; k++) {
						int iMasUno = i + 5;
						int jMasUno = j + 1;
						String year_st = (i < 5) ? "0" + iMasUno : String
								.valueOf(iMasUno);
						String month_st = (j < 9) ? "0" + jMasUno : String
								.valueOf(jMasUno);
						String storeDir = "MUNI_" + province[k]
								+ "_" + month_st + year_st + ".xls";
						if(!toListString(file).contains(storeDir))
							storeFile(url + month[j] + "_" + year[i] + "/MUNI_"
								+ province[k] + "_" + month_st + year_st
								+ ".xls", storeFiles + "/" + storeDir);
					}
				}
			}
			System.out.println("Completed successfully year " + year[i]);
		}
	}

	private List<String> toListString(File file){
		List<File> files = Arrays.asList(file.listFiles());
		List<String> names = new ArrayList<String>();
		for(File each: files){
			names.add(each.getName());
		}
		return names;
	}
	
	private void storeFile(String resource, String storeDir) throws IOException {
		BufferedInputStream in = null;
		FileOutputStream storeFile = null;
		try {
			in = new BufferedInputStream(new URL(resource).openStream());
			storeFile = new FileOutputStream(storeDir);

			final byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				storeFile.write(data, 0, count);
			}
		} catch (IOException io) {
			// NOTHINGG
		} finally {
			if (in != null) {
				in.close();
			}
			if (storeFile != null) {
				storeFile.close();
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public void checkFiles() throws IOException{
		File file = new File("public/data");
		if(file.listFiles().length==0)
			download();
		if(new Date().getDate()>14){
			//TODO
		}
	}

}
