package com.leadtek.nuu.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leadtek.nuu.entity.Logs;
import com.leadtek.nuu.entity.RoleAuth;
import com.leadtek.nuu.entity.UnitManage;
import com.leadtek.nuu.service.LogsService;
import com.leadtek.nuu.service.RoleAuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "系統單位管理")
@RestController
@RequestMapping(value = "/api/roleauth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class RoleAuthController {

	@Autowired
	RoleAuthService roleAuthService;

	@Autowired
	LogsService logsService;

	@ApiOperation(value = "查詢所有規則", notes = "查詢所有規則")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/roleAuth")
	public Iterable<UnitManage> roleAuth(@RequestHeader("Authorization") String token) {
		return roleAuthService.findAllUM();
	}

	@ApiOperation(value = "查找所有單位", notes = "查找所有單位")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/roleAuth/find")
	public Iterable<RoleAuth> find(@RequestHeader("Authorization") String token) {
		return roleAuthService.findAll();
	}

	@ApiOperation(value = "查找所有記錄檔", notes = "查找所有記錄檔")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/log/find")
	public Iterable<Logs> findLog(@RequestHeader("Authorization") String token, @RequestBody Map<String, Object> item) {

		Integer pageno = 0;
		Integer pagesize = 0;
		List<Map<String, String>> sort = null;
		Map<String, Object> qu = (Map<String, Object>) item.get("queryParams");
		pageno = (Integer) qu.get("page");
		pagesize = (Integer) qu.get("per_page");
		sort = (List<Map<String, String>>) qu.get("sort");

		return logsService.findAll(pageno - 1, pagesize, sort);
	}

	@ApiOperation(value = "查找指定單位", notes = "查找指定單位")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/roleAuth/findone")
	public List<RoleAuth> findOne(@ApiParam(required = true, value = "細項") @RequestBody RoleAuth roleAuth,
			@RequestHeader("Authorization") String token) {
		return roleAuthService.findById(roleAuth.getCode());
	}

	@ApiOperation(value = "新增單位", notes = "新增單位不用填CODE,auth,authName必填")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/roleAuth/save")
	RoleAuth save(@ApiParam(required = true, value = "細項") @RequestBody RoleAuth roleAuth,
			@RequestHeader("Authorization") String token) {
		roleAuth.setCreationDate(new Date());
		return roleAuthService.save(roleAuth);
	}

	@ApiOperation(value = "刪除單位", notes = "code,version必填")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/roleAuth/delete")
	Iterable<RoleAuth> delete(@ApiParam(required = true, value = "細項") @RequestBody RoleAuth roleAuth,
			@RequestHeader("Authorization") String token) {
		roleAuthService.delete(roleAuth);
		return roleAuthService.findAll();
	}
}
