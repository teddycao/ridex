package com.inwiss.apps.fee.persistence.impl;

import java.io.IOException;
import java.util.*;

import org.codehaus.jackson.*;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JsonMapTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new JsonMapTest().readJson2Map();
	}

	
	public void readJson2Map() {  
		JsonFactory factory = new JsonFactory();
		ObjectMapper objectMapper = new ObjectMapper(factory);
		TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>(){}; 
		//String json = "{\"success\":true,\"status\":-1}}";
		String json = "{\"success\":true,\"status\":-1,\"msg\":\"无漫游记录\",\"atm\":10.01}";
		try {
			
			//Map<String, Map<String, Object>> maps = objectMapper.readValue(json, typeRef);
			Map<String, Map<String, Object>> maps = objectMapper.readValue(json, Map.class);
			System.out.println(maps.size());
			Set<String> key = maps.keySet();
			Iterator<String> iter = key.iterator();
			while (iter.hasNext()) {
				String field = iter.next();
				System.out.println(field + ":" + maps.get(field));
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		} 
}
