package com.leadtek.nuu.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadtek.nuu.componet.JwtToken;
import com.leadtek.nuu.entity.RoleAuth;
import com.leadtek.nuu.entity.Users;
import com.leadtek.nuu.form.UsersForm;
import com.leadtek.nuu.repository.RoleAuthRepository;
import com.leadtek.nuu.repository.UsersRepository;
import com.leadtek.nuu.service.utils.Utils;

import lombok.NonNull;

@Service
public class UsersService {

	@Autowired
	UsersRepository usersRepository;

	@Autowired
	RoleAuthRepository roleAuthRepository;

	final Base64.Decoder decoder = Base64.getDecoder();
	final Base64.Encoder encoder = Base64.getEncoder();

	public <S extends Users> String save(S entity) {

		String temp = "";

		try {
			usersRepository.save(entity);
			temp = "success";
		} catch (Exception e) {
			temp = "UK";
		}

		return temp;
	}

	public Iterable<Users> findAll(Sort sort) {
		return usersRepository.findAll(sort);
	}

	public <S extends Users> Iterable<S> saveAll(Iterable<S> entities) {
		return usersRepository.saveAll(entities);
	}

	public Page<Users> findAll(UsersForm form, Pageable pageable) {
		return usersRepository.findAll(pageable);
	}

//	public Page<Users> findAll(UsersForm form, Pageable pageable) {
//		
//		QUsers s = QUsers.users;
//		BooleanBuilder builder = new BooleanBuilder(); 
//		if( StringUtils.isNotEmpty( form.getUserName() ))
//			builder.and(s.userName.eq( form.getUserName() ));
//		
//		if( StringUtils.isNotEmpty( form.getName() ) )
//			builder.and(s.name.like("%" + form.getName() + "%" ));
//		
//		Page<Users> page = usersRepository.findAll(builder, pageable);
//		return page;
//	}

	public Optional<Users> findById(String id) {
		return usersRepository.findById(id);
	}

	public boolean existsById(String id) {
		return usersRepository.existsById(id);
	}

	public Iterable<Users> findAll() {
		return usersRepository.findAll();
	}

	public Iterable<Users> findAllById(Iterable<String> ids) {
		return usersRepository.findAllById(ids);
	}

	public long count() {
		return usersRepository.count();
	}

	public void deleteById(String id) {
		usersRepository.deleteById(id);
	}

	public void delete(Users entity) {
		usersRepository.delete(entity);
	}

	public void deleteAll(Iterable<? extends Users> entities) {
		usersRepository.deleteAll(entities);
	}

	public void deleteAll() {
		usersRepository.deleteAll();
	}

//	public Users findByUserName(String userName) {
//		return usersRepository.findByUserName(userName);
//	}
//
//	public Users findByEmail(String email) {
//		return usersRepository.findByEmail(email);
//	}

//	public UserSec loginUserName(String userName) {
//		Users users = usersRepository.findByUserName(userName);
//		if( users != null){
//			List<GrantedAuthority> authSet = new ArrayList<GrantedAuthority>();  
//			authSet.add(new SimpleGrantedAuthority("ROLE_USER"));
//			authSet.add(new SimpleGrantedAuthority("ROLE_HOME"));
//			RoleAuth roleAuth = roleAuthRepository.findById( StringUtils.defaultString(users.getRole()) ).orElse(null);
//			if( roleAuth != null ){
//				for( String auth : roleAuth.getAuth() ){
//					authSet.add(new SimpleGrantedAuthority("ROLE_" + auth));
//				} 
//			}
//			UserSec userSec = new UserSec(users.getUserName(), users.getPassword(), BooleanUtils.toBoolean(users.getStatus()) , authSet, users.getName() );
//			return userSec;
//		}
//		return null;
//	}

//	public Users securityByUser(){
//		Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
//		if( userDetails != null )
//			return usersRepository.findByUserName(userDetails.getName()) ;
//		return null; 
//	}

//	public boolean hasRole(String role) {
//		RoleAuth roleAuth = roleAuthRepository.findById( securityByUser().getRole() ).orElse(null);
//	    if( StringUtils.isNotEmpty( roleAuth.getAuths() ) ){
//	    	for( String r : roleAuth.getAuths().split(",") ){
//	    		if( r.equals(role)) return true;
//	    	}
//	    }
//	    return false;
//	}

//	public boolean check(String newPassword, String hisPassword) {
//		if( !StringUtils.isEmpty( hisPassword ) ) {
//			for( String oldpass : hisPassword.split("@-@")) {
//				if( new BCryptPasswordEncoder().matches(newPassword, oldpass ) )
//					return false;
//			}
//		}
//		return true;
//    }

	public String convertArrayToString(String newPassword, String hisPassword) {
		List<String> join = new ArrayList<String>();
		if (!StringUtils.isEmpty(hisPassword)) {
			for (String oldpass : hisPassword.split("@-@")) {
				if (new BCryptPasswordEncoder().matches(newPassword, oldpass))
					return hisPassword;
				join.add(oldpass);
			}
		}
		join.add(new BCryptPasswordEncoder().encode(newPassword));
		if (join.size() >= 4)
			join.remove(0);
		return String.join("@-@", join.toArray(new String[0]));
	}

//	public Map<String, Object> findByAccountNametEST(String accout, String pass) {
//		JwtToken jjwt = new JwtToken();
//		String token = jjwt.generateToken(accout, pass, "測試用", "測試權限", null, null);
//
//		Map<String, Object> mapresult = new LinkedHashMap<>();
//		mapresult.put("statusCode", "200");
//
//		mapresult.put("token", token);
//
//		return mapresult;
//
//	}

	public String findByAccountName(@NonNull String token) throws Exception {

		String systemName = "NUUIR";

		JSONObject mapresult = checkSSOLogin(token, systemName);

		String account = (String) mapresult.get("account");
		String name = (String) mapresult.get("name");
		String department = (String) mapresult.get("department");
		String position = (String) mapresult.get("position");
		String roleitem = "";
		String[] roleauth = null;

		Users useritem = usersRepository.findByAccount(account);

		if (!("").equals(useritem.getRole())) {
			roleitem = useritem.getRole();

			if (!("").equals(roleitem)) {
				RoleAuth role = roleAuthRepository.findByAuthName(roleitem);
				roleauth = role.getAuth();
			} else {
				roleauth[0] = "na";
			}
		} else {
			roleitem = "無權限";
		}

		JwtToken jjwt = new JwtToken();
		String restoken = jjwt.generateToken(trans64(account), trans64(name), trans64(roleitem),
				Utils.arrayToString(trans64array(roleauth)), null, null);

//		Map<String, Object> mapresult = new LinkedHashMap<>();
//		if (checkSSO) {
//
////			Users result = usersRepository.findByAccount(accountName);
//
//			if (true) {
//
////				String roles = result.getRole();
////
////				List<RoleAuth> roleitem = roleAuthRepository.findByAuthName(roles);
////
////				String rolename = "";
////				String auths = "";
//
////				for (RoleAuth item : roleitem) {
////					rolename = item.getAuthName();
////					auths = item.getAuths();
////				}
//
////				JwtToken jjwt = new JwtToken();
////				String userId = accountName;
////
////				String token = jjwt.generateToken(userId, roles, rolename, auths, null, null);
//
//				mapresult.put("statusCode", "200");
//
//				mapresult.put("token", token);
//
//			} else {
//				mapresult.put("statusCode", "403");
//				mapresult.put("reason", "Undefined User");
//			}
//
//			return mapresult;
//		} else {
//			mapresult.put("statusCode", "403");
//			mapresult.put("reason", "SSO Auth Fail");
//			return mapresult;
//		}
		return restoken;

	}

	public JSONObject checkSSOLogin(String token, String systemName) throws Exception {

		Map<String, String> parma = new LinkedHashMap<>();
		parma.put("system_name", systemName);
		parma.put("token", token);

		String url = "https://sso.nuu.edu.tw/api/checkToken.php";
		JSONObject temp = post(url, parma);

		return temp;
	}

	public JSONObject logout(String account) throws Exception {
		Map<String, String> parma = new LinkedHashMap<>();
		parma.put("account", account);
		String url = "https://sso.nuu.edu.tw/api/logout.php";
		JSONObject temp = logoutpost(url, parma);
		return temp;
	}

	/**
	 * 請求超時時間
	 */
	private static final int TIME_OUT = 120000;

	/**
	 * Https請求
	 */
	private static final String HTTPS = "https";

	/**
	 * 返回成功狀態碼
	 */
	private static final int OK = 200;

	/**
	 * 純字串引數post請求
	 * 
	 * @param url      請求URL地址
	 * @param paramMap 請求字串引數集合
	 * @return 伺服器返回內容
	 * @throws Exception
	 */
	public static JSONObject post(String url, Map<String, String> paramMap) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JSONObject jsonObject = new JSONObject();
		try {
//	            String json = objectMapper.writeValueAsString(paramMap);
			Response response = doPostRequest(url, paramMap);
			jsonObject = new JSONObject(response.body());
//	            System.out.println("json = " + json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	public static JSONObject logoutpost(String url, Map<String, String> paramMap) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		JSONObject jsonObject = new JSONObject();
		try {
//	            String json = objectMapper.writeValueAsString(paramMap);
			doPostRequest(url, paramMap);
//	            System.out.println("json = " + json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return jsonObject;
	}

	/**
	 * 帶上傳檔案的post請求
	 * 
	 * @param url      請求URL地址
	 * @param paramMap 請求字串引數集合
	 * @param fileMap  請求檔案引數集合
	 * @return 伺服器返回內容
	 * @throws Exception
	 */
//	public static String post(String url, Map<String, String> paramMap) throws Exception {
//		Response response = doPostRequest(url, paramMap);
//		return response.body();
//	}

	/**
	 * 執行post請求
	 * 
	 * @param url      請求URL地址
	 * @param paramMap 請求字串引數集合
	 * @param fileMap  請求檔案引數集合
	 * @return 伺服器相應物件
	 * @throws Exception
	 */
	public static Response doPostRequest(String url, Map<String, String> paramMap) throws Exception {
		if (null == url || url.isEmpty()) {
			throw new Exception("The request URL is blank.");
		}

		// 如果是Https請求
		if (url.startsWith(HTTPS)) {
			getTrust();
		}
		Connection connection = Jsoup.connect(url);
		connection.method(Connection.Method.POST);
		connection.timeout(TIME_OUT);
		connection.header("Content-Type", "multipart/form-data");
		connection.header("Accept", "*/*");
		connection.header("Host", "sso.nuu.edu.tw");
//		connection.requestBody(json);
//		connection.header("Content-Type", "text/html; charset=UTF-8");
		connection.ignoreHttpErrors(true);
		connection.ignoreContentType(true);

		// 新增字串類引數
		if (null != paramMap && !paramMap.isEmpty()) {
			connection.data(paramMap);
		}

		try {
			Response response = connection.execute();
			System.out.println(response.body());
			if (response.statusCode() != OK) {
				throw new Exception(response.statusMessage());
			}
			return response;
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 獲取伺服器信任
	 */
	private static void getTrust() {
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {

				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String trans64(String encode) throws UnsupportedEncodingException {
		byte[] textByte = encode.getBytes("UTF-8");
		String encode64 = encoder.encodeToString(textByte);
		return encode64;
	}

	public String[] trans64array(String[] encode) throws UnsupportedEncodingException {
		String temp = Utils.arrayToString(encode);

		byte[] textByte = temp.getBytes("UTF-8");
		String encode64 = encoder.encodeToString(textByte);

		String[] result = Utils.stringToArray(encode64);

		return result;
	}

}
