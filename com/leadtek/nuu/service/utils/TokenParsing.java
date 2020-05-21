package com.leadtek.nuu.service.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenParsing {

	
	public Map<String,String> result(String token) throws JsonParseException, JsonMappingException, IOException{
		
		Map<String,String> result = new HashMap<String, String>();
		
		String[] split_string = token.split("\\.");
		String base64EncodedBody = split_string[1];
		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(base64EncodedBody));

		JSONObject map = new JSONObject(body);
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> fmap = mapper.readValue(body, Map.class);
		String rolename = fmap.get("rolename");
		String sub = fmap.get("sub");
		final Base64 base64 = new Base64();
		String user = new String(base64.decode(sub), "UTF-8");
		String userrole = new String(base64.decode(rolename), "UTF-8");
		
		result.put("user", user);
		result.put("userrole", userrole);
		
		
		
		
		return result;
		
	}
	
	
}
