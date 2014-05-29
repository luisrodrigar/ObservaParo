package utils;

import java.io.IOException;
import java.util.List;

import models.Observation;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public interface Reader {

	public List<Observation> read(String file) throws IOException,
		InvalidFormatException ;
	
}
