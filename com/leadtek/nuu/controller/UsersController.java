package com.leadtek.nuu.controller;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leadtek.nuu.entity.Users;
import com.leadtek.nuu.service.RoleAuthService;
import com.leadtek.nuu.service.UsersService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "使用者模組")
@RestController
@RequestMapping(value = "/api/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class UsersController {

	@Autowired
	UsersService usersService;
	@Autowired
	RoleAuthService roleAuthService;

	@ApiOperation(value = "登出", notes = "送入帳號名稱")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void LogOut(@RequestBody Map<String,String> account,@RequestHeader("Authorization") String token) throws Exception {
		usersService.logout(account.get("account"));
	}

	@ApiOperation(value = "聯合大學驗證token", notes = "聯合大學驗證token")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/checktoken", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	void checktoken(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "token", required = true) String name) throws Exception {
		String result = usersService.findByAccountName(name);
		
		try {
			URLEncoder.encode(result, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		pw.println("This is web application 2.");

		pw.println("<script> " + "window.onload=function(){" + "if(window.localStorage){"
				+ "document.cookie=\"leadtektoken=" + result + ";path=/\";"
				+ "window.location.href = 'http://ir.nuu.edu.tw:9018/#/ETLPlatform';" + "}else{"
				+ "alert('NOT SUPPORT');" + "}}" + "</script>");

	}

	@ApiOperation(value = "使用者列表", notes = "使用者列表")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/findUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Iterable<Users> findUser(@RequestHeader("Authorization") String token) {
		return usersService.findAll();
	}

	@ApiOperation(value = "新增使用者", notes = "僅限開發時期使用")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/createUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, String> createUser(@RequestHeader("Authorization") String token,@ApiParam(required = true, value = "細項") @RequestBody Users users) {

		Map<String, String> result = new LinkedHashMap<>();

		String temp = usersService.save(users);
		if ("UK".equals(temp)) {
			result.put("code", "500");
		} else {
			result.put("code", "200");
		}

		return result;
	}

	@ApiOperation(value = "修改使用者權限", notes = "僅限開發時期使用")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/updateUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Iterable<Users> updateUser(@RequestHeader("Authorization") String token,@ApiParam(required = true, value = "細項") @RequestBody Users users) {
		usersService.save(users);
		return usersService.findAll();
	}

	@ApiOperation(value = "刪除使用者", notes = "刪除使用者")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/deleteUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Iterable<Users> deleteUser(@RequestHeader("Authorization") String token,@ApiParam(required = true, value = "細項") @RequestBody Users users) {

		usersService.delete(users);
		return usersService.findAll();
	}

}
