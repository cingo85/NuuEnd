package com.leadtek.nuu.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leadtek.nuu.etlService.EtlService;
import com.leadtek.nuu.schoolsynoymEntity.AllSyn;
import com.leadtek.nuu.schoolsynoymEntity.CommonUse;
import com.leadtek.nuu.schoolsynoymEntity.Dropstu_syn;
import com.leadtek.nuu.schoolsynoymEntity.Enrolltype_syn;
import com.leadtek.nuu.schoolsynoymEntity.GraSurvey;
import com.leadtek.nuu.schoolsynoymEntity.Graeng_syn;
import com.leadtek.nuu.schoolsynoymEntity.Oversea_syn;
import com.leadtek.nuu.schoolsynoymEntity.S90unit;
import com.leadtek.nuu.schoolsynoymEntity.SchoolDetail;
import com.leadtek.nuu.schoolsynoymEntity.SchoolLog;
import com.leadtek.nuu.schoolsynoymEntity.SchoolMaster;
import com.leadtek.nuu.schoolsynoymEntity.Suspend_syn;
import com.leadtek.nuu.schoolsynoymEntity.Transschool_syn;
import com.leadtek.nuu.schoolsynoymEntity.licensetype_syn;
import com.leadtek.nuu.schoolsynoymService.AppException;
import com.leadtek.nuu.schoolsynoymService.CommonService;
import com.leadtek.nuu.schoolsynoymService.SynoymService;
import com.leadtek.nuu.schoolsynoymService.SynoymService2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;

@Api(tags = "學校同義詞模組")
@RestController
@RequestMapping(value = "/api/schoolSynony")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class SchoolsynonymController {

	@Autowired
	SynoymService SynoymService;

	@Autowired
	SynoymService2 SynoymService2;

	@Autowired
	CommonService CommonService;

	@Autowired
	EtlService etlService;

	@ApiOperation(value = "查詢所有同義詞", notes = "查詢所有同義詞")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/queryAllSyn", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<AllSyn> queryAllSyn(@RequestHeader("Authorization") String token) {
		return SynoymService2.findAllSyn();
	}

	@ApiOperation(value = "查詢指定同義詞表單", notes = "查詢指定同義詞表單")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/queryAllSchool", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Object> queryAllSchool(@RequestBody @NonNull Map<String, String> tableName,
			@RequestHeader("Authorization") String token) {

		String SynonymType = "";

		SynonymType = tableName.get("SynonymType");

		List<Object> result = new ArrayList<Object>();
		if (!StringUtils.isEmpty(SynonymType) && !("").equals(SynonymType)) {
			if ("Schoolsynonym".equals(SynonymType)) {
				List<SchoolMaster> temp = SynoymService.findAllSchool();
				result.addAll(temp);
			} else if ("GraSurveysynonym".equals(SynonymType)) {
				List<GraSurvey> temp = SynoymService.findAllGraSurvey();
				result.addAll(temp);
			} else if ("Oversea".equals(SynonymType)) {
				List<Map<String, Object>> temp = SynoymService2.findAllOversea();
				result.addAll(temp);
			} else if ("Suspend".equals(SynonymType)) {
				List<Map<String, Object>> temp = SynoymService2.findAllSuspend();
				result.addAll(temp);
			} else if ("Transschool".equals(SynonymType)) {
				List<Transschool_syn> temp = SynoymService2.findAllTransschool();
				result.addAll(temp);
			} else if ("Enrolltype".equals(SynonymType)) {
				List<Map<String, Object>> temp = SynoymService2.findAllEnrolltype();
				result.addAll(temp);
			} else if ("Dropstu".equals(SynonymType)) {
				List<Map<String, Object>> temp = SynoymService2.findAllDropstu();
				result.addAll(temp);
			} else if ("licensetype".equals(SynonymType)) {
				List<licensetype_syn> temp = SynoymService2.findAlllicensetype();
				result.addAll(temp);
			} else if ("Language".equals(SynonymType)) {
				List<Map<String, Object>> temp = SynoymService2.findAllLanguage();
				result.addAll(temp);
			} else if ("Deptcode".equals(SynonymType)) {
				List<S90unit> temp = SynoymService2.findAllS90unit();
				result.addAll(temp);
			}

		} else {

		}

		return result;
	}

	@ApiOperation(value = "使用同義詞ID查詢細項", notes = "使用同義詞ID查詢細項")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/querySchoolMasterBySchoolCode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Object> querySchoolMasterBySchoolCode(
			@RequestParam(value = "graduateSchoolCode") String graduateSchoolCode,
			@RequestParam(value = "SynonymType") @NonNull String SynonymType,
			@RequestHeader("Authorization") String token) {
		SynonymType = SynonymType.trim().replace(" ", "");
		List<Object> result = new ArrayList<Object>();
		if (!StringUtils.isEmpty(SynonymType) && !("").equals(SynonymType)) {
			if ("Schoolsynonym".equals(SynonymType)) {
				List<SchoolMaster> temp = SynoymService.findMasterBySchoolCode(graduateSchoolCode);
				result.addAll(temp);
			} else if ("GraSurveysynonym".equals(SynonymType)) {
				List<GraSurvey> temp = SynoymService.findGraSurveyByqoption(graduateSchoolCode);
				result.addAll(temp);
			}

		} else {

		}

		return result;

	}

	@ApiOperation(value = "查詢單一學校(D)", notes = "查詢單一學校")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/querySchoolDetailBySchoolCode", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Object> querySchoolDetailBySchoolCode(@RequestBody Map<String, String> path,
			@RequestHeader("Authorization") String token) {

		String graduateSchoolCode = path.get("graduateSchoolCode");
		String SynonymType = path.get("SynonymType");

		SynonymType = SynonymType.trim().replace(" ", "");
		List<Object> result = new ArrayList<Object>();

		if (!StringUtils.isEmpty(SynonymType) && !("").equals(SynonymType)) {
			if ("Schoolsynonym".equals(SynonymType)) {
				List<SchoolDetail> temp = SynoymService.findBySchoolCode(graduateSchoolCode);
				result.addAll(temp);
			} else if ("GraSurveysynonym".equals(SynonymType)) {
				List<GraSurvey> temp = SynoymService.findGraSurveyByqoption(graduateSchoolCode);
				result.addAll(temp);
			}

		} else {

		}

		return result;
	}

	@ApiOperation(value = "新增修改同義詞(M)", notes = "新增修改同義詞")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/insertSchoolSynoymMaster/{SynonymType}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> insertSchoolSynoymMaster(
			@ApiParam(required = true, value = "主表") @RequestBody Object schoolmaster, @PathVariable String SynonymType,
			@RequestHeader("Authorization") String token) {

		SynonymType = SynonymType.trim().replace(" ", "");
		Map<String, Object> result = new HashMap<String, Object>();
		if (!StringUtils.isEmpty(SynonymType) && !("").equals(SynonymType)) {
			if ("Schoolsynonym".equals(SynonymType)) {
				try {
					SchoolMaster item = new SchoolMaster();
					ObjectMapper oMapper = new ObjectMapper();
					item = oMapper.convertValue(schoolmaster, SchoolMaster.class);
					SynoymService.savaMaster(item);

					Date now = new Date();
					AllSyn syn = new AllSyn();
					syn.setId("Schoolsynonym");
					syn.setTablename("大專校院同義詞表");
					syn.setUpdatedate(now);
					SynoymService2.saveallsyno(syn);
					result.put("res", "成功");
				} catch (Exception e) {
					result.put("res", "存檔失敗，因資料庫已有相同的值");
				}
			} else if ("GraSurveysynonym".equals(SynonymType)) {
				try {
					GraSurvey item = new GraSurvey();
					ObjectMapper oMapper = new ObjectMapper();
					item = oMapper.convertValue(schoolmaster, GraSurvey.class);
					String temp = SynoymService.savegra(item);

					Date now = new Date();
					AllSyn syn = new AllSyn();
					syn.setId("GraSurveysynonym");
					syn.setTablename("畢業生流向");
					syn.setUpdatedate(now);
					SynoymService2.saveallsyno(syn);
					result.put("res", temp);
				} catch (Exception e) {
					result.put("res", "存檔失敗，因資料庫已有相同的值");
				}
			} else if ("Oversea".equals(SynonymType)) {
				try {
					List<Oversea_syn> item = SynoymService2.saveOversea(schoolmaster);
					result.put("newvalue", item);
					result.put("res", "存檔成功");
				} catch (Exception e) {
					result.put("res", "存檔失敗，因資料庫已有相同的值");
				}
			} else if ("Suspend".equals(SynonymType)) {
				try {
					List<Suspend_syn> item = SynoymService2.saveSuspend(schoolmaster);
					result.put("newvalue", item);
					result.put("res", "存檔成功");
				} catch (Exception e) {
					result.put("res", "存檔失敗，因資料庫已有相同的值");
				}

			} else if ("Transschool".equals(SynonymType)) {
				try {
					Transschool_syn item = new Transschool_syn();
					ObjectMapper oMapper = new ObjectMapper();
					item = oMapper.convertValue(schoolmaster, Transschool_syn.class);
					SynoymService2.saveTransschool(item);
					result.put("res", "存檔成功");
				} catch (Exception e) {
					result.put("res", "存檔失敗，因資料庫已有相同的值");
				}
			} else if ("Enrolltype".equals(SynonymType)) {
				try {
					List<Enrolltype_syn> item = SynoymService2.saveEnrolltype(schoolmaster);
					result.put("newvalue", item);
					result.put("res", "存檔成功");
				} catch (Exception e) {
					result.put("res", "存檔失敗，因資料庫已有相同的值");
				}
			} else if ("Dropstu".equals(SynonymType)) {
				try {
					List<Dropstu_syn> item = SynoymService2.saveDropstu(schoolmaster);
					result.put("newvalue", item);
					result.put("res", "存檔成功");
				} catch (Exception e) {
					result.put("res", "存檔失敗，因資料庫已有相同的值");
				}
			} else if ("licensetype".equals(SynonymType)) {
				try {
					licensetype_syn item = new licensetype_syn();
					ObjectMapper oMapper = new ObjectMapper();
					item = oMapper.convertValue(schoolmaster, licensetype_syn.class);
					SynoymService2.savelicensetype(item);
					result.put("res", "存檔成功");
				} catch (Exception e) {
					result.put("res", "存檔失敗，因資料庫已有相同的值");
				}
			} else if ("Deptcode".equals(SynonymType)) {
				try {
					S90unit item = new S90unit();
					ObjectMapper oMapper = new ObjectMapper();
					item = oMapper.convertValue(schoolmaster, S90unit.class);
					SynoymService2.saveS90unit(item);
					result.put("res", "存檔成功");
				} catch (Exception e) {
					result.put("res", "存檔失敗，因資料庫已有相同的值");
				}
			} else if ("Language".equals(SynonymType)) {
				try {
					List<Graeng_syn> item = SynoymService2.saveLanguage(schoolmaster);
					result.put("newvalue", item);
					result.put("code", "200");
				} catch (Exception e) {
					result.put("res", "存檔失敗，因資料庫已有相同的值");
				}
			}

		}

		return result;
	}

	@ApiOperation(value = "新增修改同義詞(D)", notes = "新增修改同義詞")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/insertSchoolSynoymDetail/{SynonymType}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, String> insertSchoolSynoymDetail(
			@ApiParam(required = true, value = "細項") @RequestBody Object schoolmaster, @PathVariable String SynonymType,
			@RequestHeader("Authorization") String token) {

		SynonymType = SynonymType.trim().replace(" ", "");
		Map<String, String> result = new HashMap<String, String>();

		if (!StringUtils.isEmpty(SynonymType) && !("").equals(SynonymType)) {
			if ("Schoolsynonym".equals(SynonymType)) {
				SchoolDetail item = new SchoolDetail();
				ObjectMapper oMapper = new ObjectMapper();
				item = oMapper.convertValue(schoolmaster, SchoolDetail.class);

				if (!("").equals(item.getGraduateschoolcode()) && !StringUtils.isEmpty(item.getGraduateschoolcode())) {
					try {
						Date now = new Date();
						AllSyn syn = new AllSyn();
						syn.setId("Schoolsynonym");
						syn.setTablename("大專校院同義詞表");
						syn.setUpdatedate(now);
						SynoymService2.saveallsyno(syn);
						String temp = SynoymService.savaDetail(item);
						result.put("status", "success");
					} catch (AppException e) {
						result.put("status", "UK repeat");
					}
				} else {
					result.put("status", "GraduateSchoolCode empty");
				}

			} else if ("GraSurveysynonym".equals(SynonymType)) {

			}

		}
		return result;
	}

	@ApiOperation(value = "刪除同義詞", notes = "刪除同義詞")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/deleteSchoolSynoymDetail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, String> deleteSchoolSynoym(@RequestBody @NonNull Map<String, String> tableName,
			@RequestHeader("Authorization") String token) {
		String schoolCode = tableName.get("schoolCode");
		String SynonymType = tableName.get("SynonymType");
		Map<String, String> result = new HashMap<String, String>();
		if (!StringUtils.isEmpty(SynonymType) && !("").equals(SynonymType)) {
			if ("Schoolsynonym".equals(SynonymType)) {
				Date now = new Date();
				AllSyn syn = new AllSyn();
				syn.setId("Schoolsynonym");
				syn.setTablename("大專校院同義詞表");
				syn.setUpdatedate(now);
				SynoymService2.saveallsyno(syn);
				SynoymService.delete(Integer.valueOf(schoolCode));
				result.put("code", "200");
			} else if ("GraSurveysynonym".equals(SynonymType)) {

			}

		} else {
			result.put("code", "403");
		}

		return result;
	}

	@ApiOperation(value = "刪除同義詞(M)", notes = "刪除同義詞(M)")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/deleteSchoolSynoymMaster", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, String> deleteSchoolSynoymMaster(@RequestBody @NonNull Map<String, String> tableName,
			@RequestHeader("Authorization") String token) {

		String schoolCode = tableName.get("schoolCode");
		String SynonymType = tableName.get("SynonymType");

		Map<String, String> result = new HashMap<String, String>();

		if (!StringUtils.isEmpty(SynonymType) && !("").equals(SynonymType)) {
			if ("Schoolsynonym".equals(SynonymType)) {
				SynoymService.deleteMaster(schoolCode);
				SynoymService.deleteDetail(schoolCode);
				result.put("code", "200");
			} else if ("GraSurveysynonym".equals(SynonymType)) {
				SynoymService.deleteGra(schoolCode);
				result.put("code", "200");
			} else if ("Oversea".equals(SynonymType)) {
				SynoymService2.deleteOver(Integer.valueOf(schoolCode));
				result.put("code", "200");
			} else if ("Suspend".equals(SynonymType)) {
				SynoymService2.deleteSus(schoolCode);
				result.put("code", "200");
			} else if ("Transschool".equals(SynonymType)) {
				SynoymService2.deleteTrans(Integer.valueOf(schoolCode));
				result.put("code", "200");
			} else if ("Enrolltype".equals(SynonymType)) {
				SynoymService2.deleteEnro(schoolCode);
				result.put("code", "200");
			} else if ("Dropstu".equals(SynonymType)) {
				SynoymService2.deleteDrop(schoolCode);
				result.put("code", "200");
			} else if ("licensetype".equals(SynonymType)) {
				SynoymService2.deleteLicen(schoolCode);
				result.put("code", "200");
			} else if ("Language".equals(SynonymType)) {
				SynoymService2.deleteLanguage(Integer.valueOf(schoolCode));
				result.put("code", "200");
			} else if ("Deptcode".equals(SynonymType)) {
				SynoymService2.deleteDept(schoolCode);
				result.put("code", "200");
			}

		} else {
			result.put("code", "403");
		}

		return result;

	}

	@ApiOperation(value = "查詢同義詞LOG", notes = "查詢同義詞LOG")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/querySchoolSynoymLog/{SynonymType}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, String> querySchoolSynoymLog(@PathVariable String SynonymType,
			@RequestHeader("Authorization") String token) {

		Map<String, String> resultMap = new HashMap<String, String>();
		SchoolLog item = SynoymService.findAllLOG(SynonymType).get(0);

		if (item != null) {
			String temp = item.getLog().replace("{", "").replace("}", "");
			String sptemp[] = temp.split(",");

			for (String s : sptemp) {
				String spstring[] = s.split("=");
				resultMap.put(spstring[0].trim(), spstring[1].trim());
			}

		}

		return resultMap;

	}

	@ApiOperation(value = "上傳同義詞Excel", notes = "取得同義詞Excel")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/getSynoymExcel/{SynonymType}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public Map<String, Object> getSynoymExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request,
			@PathVariable String SynonymType, @RequestHeader("Authorization") String token) throws Exception {

		Map<String, Object> result = null;
		String fileName = file.getOriginalFilename();
		if (request instanceof MultipartHttpServletRequest && "Schoolsynonym".equals(SynonymType)) {
			result = SynoymService.batchImport(fileName, file);
		} else if (request instanceof MultipartHttpServletRequest && "GraSurveysynonym".equals(SynonymType)) {
			result = SynoymService.batchImportGra(fileName, file);
		}

		return result;
	}

	@ApiOperation(value = "使用表名查詢欄位", notes = "使用表名查詢欄位")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK") })
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/queryColumn", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<CommonUse> queryColumn(@RequestBody @NonNull CommonUse tableName,
			@RequestHeader("Authorization") String token) {
		return CommonService.findTableByName(tableName.getTableengname());
	}

}
