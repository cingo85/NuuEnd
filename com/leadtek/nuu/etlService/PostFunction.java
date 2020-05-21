package com.leadtek.nuu.etlService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.springframework.scheduling.annotation.Async;

public class PostFunction {

	private final String USER_AGENT = "Mozilla/5.0";
	private static HttpURLConnection con;
	final Base64.Encoder encoder = Base64.getEncoder();
	
	@Async
	public String sendPost(String connName) throws Exception {
//		System.out.println("RAW:"+connName);
//		String conName = trans64(connName);
//		System.out.println("DECODE:"+conName);
		
		String url = "http://203.64.173.61:9021/api/schooletl/"+connName;
		//String url = "http://localhost:9021/api/schooletl/queryAllSchool";
//		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		StringBuilder content = null;
		try {
			
			System.out.println("已對接ETL:"+url);
			URL dataclean = new URL(url);
			con = (HttpURLConnection) dataclean.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Java client");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			

			

			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

				String line;
				content = new StringBuilder();

				while ((line = br.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}

			return content.toString().trim();

		} catch (Exception e) {

		} finally {

			con.disconnect();
		}
		return content.toString().trim();

	}
	
	public String trans64(String encode) throws UnsupportedEncodingException {
		byte[] textByte = encode.getBytes("UTF-8");
		String encode64 = encoder.encodeToString(textByte);
		return encode64;
	}
}
