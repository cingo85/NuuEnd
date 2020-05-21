package com.leadtek.nuu.aspect;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadtek.nuu.entity.Logs;
import com.leadtek.nuu.repository.LogsRepository;
import com.leadtek.nuu.service.utils.TokenParsing;

import eu.bitwalker.useragentutils.UserAgent;

@Aspect
@Component
public class WebLogAspect {

	private Log log = LogFactory.getLog(getClass());

	@Autowired
	LogsRepository logsRepository;

	@Pointcut("execution(* com.leadtek.nuu.controller.*Controller.*(..))")
	public void webLog() {
		System.out.println("webLog......");
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		// 接收到请求，记录请求内容
		Logs logs = new Logs();
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));

		String user = "";
		String token = request.getHeader("Authorization");

		TokenParsing tp = new TokenParsing();
		

		if (token != null) {
			if (!"".equals(token)) {
				Map<String, String> tokenparse = tp.result(token);

				user = tokenparse.get("user");
				logs.setUserName(user);
			}
		}

//        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
		// 记录下请求内容

		logs.setStartTime(new Date());
		logs.setExeTime(new Date());
		logs.setBrowser(userAgent.getBrowser().getName());
		logs.setDevice(userAgent.getOperatingSystem().getName());
		log.info("URL : " + request.getRequestURL().toString());
		log.info("Browser : " + userAgent.getBrowser().getName());
		log.info("Device : " + userAgent.getOperatingSystem().getName());
		logs.setUrl(request.getRequestURL().toString());

		log.info("HTTP_METHOD : " + request.getMethod());

		log.info("IP : " + request.getRemoteAddr());
		logs.setIpAddress(request.getRemoteAddr());

		log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName());
		logs.setObjectName(joinPoint.getSignature().getDeclaringTypeName());
		logs.setOperation(joinPoint.getSignature().getName());

		logs.setSession(request.getSession().getId());
		log.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
//        System.out.println(request.);
//        log.info("USER_DETSILS : " + userDetails.getName() );
//        logs.setUserName( userDetails.getName() );
		logsRepository.save(logs);
	}

	public Map<String, String> result(String token) throws JsonParseException, JsonMappingException, IOException {

		Map<String, String> result = new HashMap<String, String>();

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
