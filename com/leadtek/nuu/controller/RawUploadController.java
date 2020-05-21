package com.leadtek.nuu.controller;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadtek.nuu.entity.Users;
import com.leadtek.nuu.rawuploadEntity.MainColumn;
import com.leadtek.nuu.rawuploadEntity.MainTable;
import com.leadtek.nuu.rawuploadEntity.NuuColumnValue;
import com.leadtek.nuu.rawuploadService.ColumnValueService;
import com.leadtek.nuu.rawuploadService.MainColumnService;
import com.leadtek.nuu.rawuploadService.MainTableService;
import com.leadtek.nuu.repository.UsersRepository;
import com.leadtek.nuu.service.UsersService;
import com.leadtek.nuu.service.utils.TokenParsing;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "原始資料上傳系統")
@RestController
@RequestMapping(value = "/api/rawdataUpload")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class RawUploadController {

	@Autowired
	MainTableService mts;

	@Autowired
	MainColumnService mcs;

	@Autowired
	ColumnValueService cvs;

	@Autowired
	UsersService us;

	@Autowired
	UsersRepository usersRepository;

	@ApiOperation(value = "上傳表單設定檔用", notes = "上傳表單設定檔用")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/getSynoymExcel", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> getSynoymExcel(@RequestHeader("Authorization") String token,@RequestParam("file") MultipartFile file, HttpServletRequest request)
			throws Exception {

		Map<String, Object> result = null;
		String fileName = file.getOriginalFilename();
		if (request instanceof MultipartHttpServletRequest) {
			result = mts.batchImport(fileName, file);
		}

		return result;
	}


	@ApiOperation(value = "修改表單", notes = "送入uuid跟version")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/updatetable", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void updatetable(@RequestHeader("Authorization") String token,@RequestBody MainTable item) throws Exception {

		mts.tablesave(item);

	}

	@ApiOperation(value = "查詢所有表單", notes = "查詢全部的單")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/queryalltable", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Page<MainTable> queryalltable(@RequestHeader("Authorization") String token,@RequestBody Map<String, Object> itemqu) throws Exception {

		TokenParsing tp = new TokenParsing();

		Map<String, String> tokenparse = tp.result(token);

		String user = tokenparse.get("user");


		Integer pageno = 0;
		Integer pagesize = 0;
		List<Map<String, String>> sort = null;
		List<Map<String, String>> filter = null;
		Map<String,Object> qu = (Map<String,Object>)itemqu.get("queryParams");
		pageno = (Integer) qu.get("page");
		pagesize = (Integer) qu.get("per_page");
		sort = (List<Map<String,String>>)qu.get("sort");
		filter = (List<Map<String,String>>)qu.get("filters");
		
		
		Page<MainTable> table = null;

		Users useritem = usersRepository.findByAccount(user);

		String role = useritem.getRole();
		System.out.println(role);
		if("系統管理者".equals(role)) {
			table = mts.findall(pageno-1,pagesize,sort,role,filter);
		}else {
			table = mts.findall(pageno-1,pagesize,sort,role,filter);
	
		}
				
		return table;
	}

	@ApiOperation(value = "查詢單一表單欄位", notes = "用表單uuid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/querytablecolumn", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<MainColumn> querytablecolumn(@RequestHeader("Authorization") String token,@RequestBody MainTable item) throws Exception {

		return mcs.findbyuuit(item.getTableuuid());
	}

	@ApiOperation(value = "上傳資料表", notes = "{\"tableuuid\":\"7A1510C6-10FF-4492-A0A7-F6F3BCD5F194\",\"40BE0884-1B62-4FC2-B03E-C78908148446\":\"107\"}")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String uploadExcel(@RequestHeader("Authorization") String token,@RequestParam(value = "json") String json, @RequestParam("file") MultipartFile file,
			HttpServletRequest request) throws Exception {
		
		TokenParsing tp = new TokenParsing();

		Map<String, String> tokenparse = tp.result(token);

		String user = tokenparse.get("user");

		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();

		if (!("").equals(json) && !StringUtils.isEmpty(json)) {
			try {
				map = mapper.readValue(json, new TypeReference<HashMap<String, String>>() {
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String tableuuid = map.get("tableuuid");

		List<MainColumn> columnlist = mcs.findbyuuit(tableuuid);

		String result = null;
		String fileName = file.getOriginalFilename();
		if (request instanceof MultipartHttpServletRequest) {
			result = mts.upload(tableuuid, map, columnlist, fileName, file,user);
		}

		return result;
	}

	@ApiOperation(value = "查詢表單資料", notes = "用表單uuid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/querytablevalue", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String,Object> querytablevalue(@RequestHeader("Authorization") String token,@RequestBody Map<String, Object> item) throws Exception {

		String tableuuid = (String) item.get("tableuuid");
		
		Integer pageno = 0;
		Integer pagesize = 0;
		List<Map<String, String>> sort = null;
		List<Map<String, String>> filter = null;
		Map<String,Object> qu = (Map<String,Object>)item.get("queryParams");
		pageno = (Integer) qu.get("page");
		pagesize = (Integer) qu.get("per_page");
		sort = (List<Map<String,String>>)qu.get("sort");
		filter = (List<Map<String,String>>)qu.get("filters");

		return cvs.queryalldata(tableuuid,pageno-1,pagesize,sort,filter);
	}

	@ApiOperation(value = "新增一筆資料", notes = "傳送物件 不用含valueuuid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/insert", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void insertData(@RequestHeader("Authorization") String token,@RequestBody Map<String, Object> item) throws Exception {
		NuuColumnValue setv = new NuuColumnValue();

		UUID valueuuid = UUID.randomUUID();
		String coluuidsave = valueuuid.toString();
		coluuidsave = coluuidsave.toUpperCase();

		String json = (String) item.get("columnvalue");
		String change = URLDecoder.decode(json, "UTF-8");
		String tableuuid = (String) item.get("tableuuid");

		setv.setValueuuid(coluuidsave);
		setv.setTableuuid(tableuuid);
		setv.setColumnvalue(change);
		
		
		
		TokenParsing tp = new TokenParsing();

		Map<String, String> tokenparse = tp.result(token);

		String user = tokenparse.get("user");
		
		
		cvs.save(setv,user);
		
	}

	@ApiOperation(value = "更新一筆資料", notes = "傳送物件 含valueuuid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void updateData(@RequestHeader("Authorization") String token,@RequestBody Map<String, Object> item) throws Exception {
		NuuColumnValue setv = new NuuColumnValue();

		String json = (String) item.get("columnvalue");
		String change = URLDecoder.decode(json, "UTF-8");
		String valueuuid = (String) item.get("valueuuid");
		String tableuuid = (String) item.get("tableuuid");

		setv.setValueuuid(valueuuid);
		setv.setTableuuid(tableuuid);
		setv.setColumnvalue(change);
		
		
		TokenParsing tp = new TokenParsing();

		Map<String, String> tokenparse = tp.result(token);

		String user = tokenparse.get("user");

		cvs.save(setv,user);
	}

	@ApiOperation(value = "刪除一筆資料", notes = "使用valueuuid刪除")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public void deleteData(@RequestHeader("Authorization") String token,@RequestBody NuuColumnValue item) throws Exception {
		
		TokenParsing tp = new TokenParsing();

		Map<String, String> tokenparse = tp.result(token);

		String user = tokenparse.get("user");
		
		
		cvs.delete(item.getValueuuid(),user);
	}

	@ApiOperation(value = "下載空白檔案", notes = "送入tableid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/download", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String downloadFile(@RequestHeader("Authorization") String token,@RequestBody MainTable mt, HttpServletResponse response) throws Exception {

		MainTable item = mts.findByTableuuid(mt.getTableuuid());

		// 如果檔名不為空，則進行下載
		if (item.getTablename() != null) {
			// 設定檔案路徑
			String filePath = item.getFilepath() + item.getTablename() + "." + item.getFileextension();
			FileInputStream input = new FileInputStream(filePath);
			response.setHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(item.getTablename() + "." + item.getFileextension(), "UTF-8"));
			response.setContentType("application/vnd.ms-excel");

			OutputStream out = response.getOutputStream();
			byte[] buffer = new byte[2048];
			int len;
			while ((len = input.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			response.setHeader("Content-Length", String.valueOf(input.available()));
			input.close();

		}
		return "download";

	}

	@ApiOperation(value = "查詢單一表單", notes = "使用tableuuid")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/querysinglemt", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public MainTable querysinglemt(@RequestHeader("Authorization") String token,@RequestBody Map<String, String> item) {

		String tableuuid = (String) item.get("tableuuid");

		return mts.findByTableuuid(tableuuid);
	}

}
