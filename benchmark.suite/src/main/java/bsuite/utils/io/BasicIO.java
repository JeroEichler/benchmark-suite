package bsuite.utils.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bsuite.utils.MapperFactory;

public class BasicIO {
	static ObjectMapper mapper = MapperFactory.initMapper(); 	

	public static void saveEntity(String folder, String fileName, Object entity) {		
		try {
			mapper.writeValue(new File(
					FoldersNFiles.root + 
					folder + "//" +
					fileName +".json"), entity);			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	

	public static List<String> readList(String folder, String fileName) {
		List<String> list = new ArrayList<String>();
		
		try {
			String[] tempRead = mapper.readValue(new File(
					FoldersNFiles.root + 
					folder + "//" +
					fileName +".json"), String[].class);
			list.addAll(Arrays.asList(tempRead));
		} 
		catch (JsonParseException e) {
			e.printStackTrace();
		} 
		catch (JsonMappingException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Erro de leitura : " + FoldersNFiles.root + 
					folder + "//" +
					fileName +".json");
		}
		return list;
	}
}
