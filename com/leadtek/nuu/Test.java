package com.leadtek.nuu;

import org.apache.commons.codec.binary.Base64;
import com.leadtek.nuu.componet.JwtToken;

public class Test {
	private static String secret = "Leadtek";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String txt = "BearereyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJOREV4ZEdWemRERXciLCJyb2xlcyI6IjVyaXM2S21tNWJpejZKbWYiLCJyb2xlbmFtZSI6IjU3Tzc1N1d4NTY2aDU1Q0c2SUNGIiwiZXhwIjoxNTg1NTMyMTMxLCJjcmVhdGVkIjoxNTg1NTMwMzMxNDExLCJhdXRocyI6Ik1URXNNU3d4TWl3eUxETXNOQ3cxTERZc055dzRMRGtzTVRBPSJ9.wXUaBtsByEV4lRE-HG68HDTX_WQuCgzzYV9DKbNmY3drFE7ap3mkoJr0kZ1a2vBtFHmjmu8UuAMhD8Q0IwM7-w";

		
	
		
		System.out.println("------------ Decode JWT ------------");
		String[] split_string = txt.split("\\.");
		String base64EncodedBody = split_string[1];
		Base64 base64Url = new Base64(true);
		String body = new String(base64Url.decode(base64EncodedBody));
	

//		System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
//		String header = new String(base64Url.decode(base64EncodedHeader));
//		System.out.println("JWT Header : " + header);

		System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
		System.out.println("JWT Body : " + body);

	}

}
