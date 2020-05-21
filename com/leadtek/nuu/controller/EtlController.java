package com.leadtek.nuu.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.leadtek.nuu.entity.Users;
import com.leadtek.nuu.etlEntity.ColumnList;
import com.leadtek.nuu.etlEntity.ETlMaster;
import com.leadtek.nuu.etlService.EtlService;
import com.leadtek.nuu.repository.UsersRepository;
import com.leadtek.nuu.service.utils.TokenParsing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import reactor.netty.http.server.HttpServerRequest;

@Api(tags = "倉儲資料管理")
@RestController
@RequestMapping(value = "/api/etlcontroller")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class EtlController {

	@Autowired
	EtlService etlService;
	
	@Autowired
	UsersRepository usersRepository;

//	@Autowired
//	private FileService fileService;

	@ApiOperation(value = "表單年度", notes = "表單年度")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/queryetlyear", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, List<String>> queryetlyear(@RequestHeader("Authorization") String token) {
		List<String> stusportlistyear = new ArrayList<String>();
		stusportlistyear.add("107");
		stusportlistyear.add("106");
		List<String> other = new ArrayList<String>();
		other.add("98");
		other.add("99");
		other.add("100");
		other.add("101");
		other.add("102");
		other.add("103");
		other.add("104");
		other.add("105");
		other.add("106");
		other.add("107");
		other.add("108");
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		result.put("stusportlist", stusportlistyear);
		result.put("courseinfo", other);
		result.put("stuterm", other);
		result.put("stuscore", other);
		result.put("stuinfo", other);
		return result;
	}

	@ApiOperation(value = "查詢所有表單", notes = "查詢所有表單")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/queryAllTable", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<ETlMaster> queryAllTable(@RequestHeader("Authorization") String token)
			throws JsonParseException, JsonMappingException, IOException {

		List<ETlMaster> result = new ArrayList<>();

		TokenParsing tp = new TokenParsing();

		Map<String, String> tokenparse = tp.result(token);
		String role = tokenparse.get("userrole");
		System.out.println(role);// 系統管理者

		List<ETlMaster> item = etlService.findAllTable();

		for (ETlMaster dbu : item) {
			String[] autharray = dbu.getAuthName();
//			for (String auth : autharray) {
//				if (auth.equals(role)) {
					result.add(dbu);
//				}
//			}

		}

		return result;
	}

	@ApiOperation(value = "查詢表單群組", notes = "查詢表單群組")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/queryTableColumn/{tablename}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, List<Map<String, Object>>> queryTableColumn(@RequestHeader("Authorization") String token,
			@PathVariable String tablename) throws JsonParseException, JsonMappingException, IOException {
		TokenParsing tp = new TokenParsing();

		Map<String, String> tokenparse = tp.result(token);

		String user = tokenparse.get("user");
		Users useritem = usersRepository.findByAccount(user);

		String role = useritem.getRole();
		
		
		return etlService.findTableColumn(tablename,role);
	}

	@ApiOperation(value = "儲存表單群組權限", notes = "儲存表單群組權限")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/saveauth", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void savetablecolumn(@RequestHeader("Authorization") String token,
			@RequestBody List<Map<String, Object>> savemap) {
		etlService.saveetldetail(savemap);
	}

	@ApiOperation(value = "輸出欄位", notes = "使用欄位群組;表單名稱")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/export", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void export(@RequestHeader("Authorization") String token, @RequestBody ColumnList ColumnList,
			HttpServletResponse response, HttpServerRequest req) throws Exception {

		TokenParsing tp = new TokenParsing();

		Map<String, String> tokenparse = tp.result(token);

		String role = tokenparse.get("userrole");
		System.out.println(role);// 系統管理者

		response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=Output.xlsx");
		ByteArrayInputStream stream = etlService.contactListToExcelFileByView(ColumnList, role);

		IOUtils.copy(stream, response.getOutputStream());

//		response.flushBuffer();
	}

//	public ResponseEntity<Resource> downloadFile(@PathVariable String filename,HttpServerRequest req){
//		
////		Resource resource = fileS
//		
//	}

	@ApiOperation(value = "資料清洗表單", notes = "資料清洗表單")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/dataclean/{tablename}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<ETlMaster> dataclean(@RequestHeader("Authorization") String token, @PathVariable String tablename)
			throws Exception {

		String tablenamedecode = URLDecoder.decode(tablename, "utf-8");
		List<ETlMaster> item = etlService.dataclean(tablenamedecode);

		return item;

	}

//	@ApiOperation(value = "資料權限查找", notes = "送入authname 與 tablename")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
//	@ResponseStatus(HttpStatus.OK)
//	@PostMapping(value = "/Encryptiongroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public List<EtlAuth> changeEncryption(@RequestBody EtlAuth etlAuth) {
//		return etlService.findalletlauth(etlAuth.getAuthName(), etlAuth.getTablename());
//	}
//
//	@ApiOperation(value = "資料權限修改", notes = "送入id ,version,authname,tablename修改etlauth")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
//	@ResponseStatus(HttpStatus.OK)
//	@PostMapping(value = "/updateEncryptiongroup", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public void updateEncryptiongroup(@RequestBody List<EtlAuth> etlAuth) {
//
//		for (EtlAuth item : etlAuth) {
//			etlService.saveetlauth(item);
//		}
//
//	}

	@ApiOperation(value = "排程設定", notes = "除了cleandatefirst,reday,cleandatecal,endsend之外的值都是原值")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/updateTime", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<ETlMaster> updateTime(@RequestHeader("Authorization") String token, @RequestBody ETlMaster item) {
		ETlMaster objitem = etlService.findbyid(item.getId());
		objitem.setCleandatecal(item.getCleandatecal());
		objitem.setCleandatefirst(item.getCleandatefirst());
		objitem.setEndsend(item.getEndsend());
		objitem.setReday(item.getReday());
		objitem.setRedaytype(item.getRedaytype());
		objitem.setStartcount(item.getStartcount());
		objitem.setStatus(item.getStatus());
		objitem.setTablecount(item.getTablecount());
		objitem.setTableengname(item.getTableengname());
		objitem.setTablename(item.getTablename());
		objitem.setCleanyn(item.getCleanyn());

		return etlService.updateSchedule(objitem);

	}

}
