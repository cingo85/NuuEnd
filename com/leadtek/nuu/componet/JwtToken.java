package com.leadtek.nuu.componet;

import java.io.Serializable;

import org.bouncycastle.crypto.tls.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.impl.crypto.MacProvider;


@Component
public class JwtToken implements Serializable {

	private static final long serialVersionUID = -3301605591108950415L;

	private String secret = "Leadtek";
//	private Long expiration = 60l * 60 * 2; // 有效時間2小時
//	private Long expiration = (long) 30 ;
	public JwtToken() {
		//
	}

	public JwtToken(String key) {
		if (key.length() > 0) {
			secret = key;
		}
	}

	public String generateToken(String userid, String roles,String rolename ,String strings, java.util.Date now, String authModule) {
		if (roles.length() == 0) {
			roles = "ROLE_GUEST"; // ROLE_ADMIN,AUTH_WRITE
		}

		if (now == null) {
			now = new java.util.Date();
		}
		java.security.Key key = MacProvider.generateKey();
		String token = io.jsonwebtoken.Jwts.builder().setSubject(userid) // user Name
				.claim("roles", roles) // 權限 代碼(角色)
				.claim("rolename", rolename) // 權限中文名稱(角色)
				.setExpiration(generateExpirationDate(now)) // 有效期
				.claim("created", now.getTime()) // System.currentTimeMillis())
				.claim("auths", strings) // 模組權限
				.claim("module",authModule)
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret) // 簽名
				.compact();

		return (token);
	}

	public String getUserIdFromToken(String token) {
		String username;
		try {
			io.jsonwebtoken.Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	public java.util.Date getRolesFromToken(String token) {
		java.util.Date created;
		try {
			io.jsonwebtoken.Claims claims = getClaimsFromToken(token);
			created = new java.util.Date((Long) claims.get("roles"));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public java.util.Date getCreatedDateFromToken(String token) {
		java.util.Date created;
		try {
			io.jsonwebtoken.Claims claims = getClaimsFromToken(token);
			created = new java.util.Date((Long) claims.get("created"));
		} catch (Exception e) {
			created = null;
		}
		return created;
	}

	public java.util.Date getExpirationDateFromToken(String token) {
		java.util.Date expiration;
		try {
			io.jsonwebtoken.Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public String getDataFromToken(String token, String field) {
		String ret;
		try {
			io.jsonwebtoken.Claims claims = getClaimsFromToken(token);
			ret = (String) claims.get(field);
		} catch (Exception e) {
			ret = null;
		}
		return ret;
	}
	
	

	private io.jsonwebtoken.Claims getClaimsFromToken(String token) {
		io.jsonwebtoken.Claims claims;
		try {
			claims = io.jsonwebtoken.Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	private java.util.Date generateExpirationDate(java.util.Date now) {
		// return new java.util.Date(System.currentTimeMillis() + expiration * 1000);
//		return new java.util.Date(now.getTime() + expiration * 1000);
		return new java.util.Date(now.getTime() + 1000*60*30);
	}

	private Boolean isTokenExpired(String token) {
		java.util.Date expiration = getExpirationDateFromToken(token);
		java.util.Date now = new java.util.Date();
		Boolean bl = expiration.before(now);

		return (bl);
	}

	public int validateToken(String token) {
		String userId = getUserIdFromToken(token);
		String roles = getDataFromToken(token, "roles");
		String info = getDataFromToken(token, "info");
		String sys = getDataFromToken(token, "sys");
		String authModule = getDataFromToken(token, "module"); 
		java.util.Date created = getCreatedDateFromToken(token);

		int ret = 0;
		try {
			String newToken = generateToken(userId, roles, sys, info, created, authModule);
			String signature[] = token.split("\\.");
			String signature2[] = newToken.split("\\.");
			if (!signature[2].equals(signature2[2])) {
				ret = -1;
				System.out.println(" token 被改過...");
			} else if (isTokenExpired(token)) {
				ret = -2;
				java.util.Date expiration = getExpirationDateFromToken(token);
				System.out.println(" token 已過期...");
			}
		} catch (java.lang.NullPointerException e) {
			ret = -3;
			System.out.println("err : " + e.getMessage());
		}

		return (ret);
	}

}
