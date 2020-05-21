package com.leadtek.nuu.schoolsynoymService;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.leadtek.nuu.etlEntity.ColumnList;
import com.leadtek.nuu.schoolsynoymEntity.AllSyn;
import com.leadtek.nuu.schoolsynoymEntity.GraSurvey;
import com.leadtek.nuu.schoolsynoymEntity.SchoolDetail;
import com.leadtek.nuu.schoolsynoymEntity.SchoolLog;
import com.leadtek.nuu.schoolsynoymEntity.SchoolMaster;
import com.leadtek.nuu.schoolsynoymRepository.AllSynRepostiory;
import com.leadtek.nuu.schoolsynoymRepository.GraSurveyRepository;
import com.leadtek.nuu.schoolsynoymRepository.SchoolLogRepository;
import com.leadtek.nuu.schoolsynoymRepository.SynoymDetailRepository;
import com.leadtek.nuu.schoolsynoymRepository.SynoymMasterRepository;

import lombok.NonNull;

@Service
public class SynoymService {
	
	@Autowired
	AllSynRepostiory AllSynRepostiory;

	@Autowired
	SynoymMasterRepository SynoymMasterRepository;

	@Autowired
	SynoymDetailRepository SynoymDetailRepository;

	@Autowired
	SchoolLogRepository SchoolLogRepository;

	@Autowired
	GraSurveyRepository GraSurveyRepository;
	
	public void saveallsyno(AllSyn entity) {
		Date now = new Date();
		entity.setUpdatedate(now);
		AllSynRepostiory.save(entity);
	}

	public List<SchoolLog> findAllLOG(String synonymType) {

		List<SchoolLog> item = SchoolLogRepository.findBysynoymtypeOrderByIdDesc(synonymType);

		if (item.size() > 0) {
			return item;
		} else {
			return null;
		}

	}

	public List<GraSurvey> findAllGraSurvey() {
		return GraSurveyRepository.findAll();
	}

	public List<SchoolMaster> findAllSchool() {
		return SynoymMasterRepository.findAll();
	}

	public List<GraSurvey> findGraSurveyByqoption(String id) {
		return GraSurveyRepository.findBygraduateschoolcode(id);
	}

	public List<SchoolMaster> findMasterBySchoolCode(String SchoolCode) {
		return SynoymMasterRepository.findBygraduateschoolcode(SchoolCode);
	}

	public List<SchoolDetail> findBySchoolCode(String SchoolCode) {
		return SynoymDetailRepository.findBygraduateschoolcode(SchoolCode);
	}

	public void savaMaster(SchoolMaster item) {
		List<SchoolMaster> ritem = SynoymMasterRepository.findBygraduateschoolcode(item.getGraduateschoolcode());

		
		//新增
		if(!(ritem.size() > 0) && !(ritem!= null) && !(item.getVersion() != null)) {
			SynoymMasterRepository.save(item);
		}else {
		//修改
			SchoolMaster updateitem = new SchoolMaster();
			updateitem.setGraduateschoolcode(item.getGraduateschoolcode());
			updateitem.setGraduateschoolname(item.getGraduateschoolname());
			updateitem.setGraduateschoolsystem(item.getGraduateschoolsystem());
			updateitem.setGraduateschoolpublicprivate(item.getGraduateschoolpublicprivate());
			updateitem.setGraduateschoollevel(item.getGraduateschoollevel());
			updateitem.setGraduateschoolcitymarked(item.getGraduateschoolcitymarked());
			updateitem.setGraduateschoolregion(item.getGraduateschoolregion());
			updateitem.setGraduateschoolcity(item.getGraduateschoolcity());
			updateitem.setGraduateschooldistrict(item.getGraduateschooldistrict());
			updateitem.setGraduateschooladdress(item.getGraduateschooladdress());
			updateitem.setGraduateschooltranscode(item.getGraduateschooltranscode());
			updateitem.setLat(item.getLat());
			updateitem.setLng(item.getLng());
			updateitem.setVersion(item.getVersion());
			SynoymMasterRepository.save(updateitem);
		}
		
		
		

		
		
		AllSyn AllSynitem = new AllSyn();
		AllSynitem.setId("Schoolsynonym");
		AllSynitem.setTablename("大專校院同義詞表");
		saveallsyno(AllSynitem);
		
	
	}

	public String savegra(GraSurvey item) {

		List<GraSurvey> rtemp = GraSurveyRepository.findBygraduateschoolcode(item.getGraduateschoolcode());
		String temp = "";

		if (rtemp.size() > 0) {
			GraSurveyRepository.save(item);
			temp = "修改成功";
		}

		if (rtemp.size() == 0) {
			GraSurveyRepository.save(item);
			temp = "新增成功";
		}
		AllSyn AllSynitem = new AllSyn();
		AllSynitem.setId("GraSurveysynonym");
		AllSynitem.setTablename("畢業生流向");
		saveallsyno(AllSynitem);
		
		return temp;
	}

	public String savaDetail(SchoolDetail item) throws AppException {

		try {
			AllSyn AllSynitem = new AllSyn();
			AllSynitem.setId("Schoolsynonym");
			AllSynitem.setTablename("大專校院同義詞表");
			saveallsyno(AllSynitem);
			SynoymDetailRepository.save(item);
		} catch (Exception e) {
			throw new AppException("DB exception", e);
		}
		return "新增成功";

	}

	public void delete(Integer id) {
		AllSyn AllSynitem = new AllSyn();
		AllSynitem.setId("Schoolsynonym");
		AllSynitem.setTablename("大專校院同義詞表");
		saveallsyno(AllSynitem);
		SynoymDetailRepository.deleteById(id);
	}

	public void deleteDetail(String id) {
		AllSyn AllSynitem = new AllSyn();
		AllSynitem.setId("Schoolsynonym");
		AllSynitem.setTablename("大專校院同義詞表");
		saveallsyno(AllSynitem);
		SynoymDetailRepository.deleteBygraduateschoolcode(id);
	}

	public void deleteMaster(String id) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Schoolsynonym");
		syn.setTablename("大專校院同義詞表");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		SynoymMasterRepository.deleteById(id);
	}

	public void deleteGra(String schoolCode) {
		GraSurveyRepository.deleteBygraduateschoolcode(schoolCode);
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("GraSurveysynonym");
		syn.setTablename("畢業生流向");
		syn.setUpdatedate(now);
		saveallsyno(syn);
	}

	public Map<String, Object> batchImport(String fileName, MultipartFile file) {
//		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
//			throw new Exception("上傳文件格式不正確");
//		}
		Map<String, Object> result = new LinkedHashMap<>();
		Map<String, Object> LOGresult = new LinkedHashMap<>();
		Map<String, Object> LOGDetail = new LinkedHashMap<>();

		List<SchoolMaster> excelItem = new ArrayList<>();

		List<Object> MasterResult = new ArrayList<>();
		List<Object> DetailResult = new ArrayList<>();

		List<Object> MastererrorResult = new ArrayList<>();
		List<Object> DetailerrorResult = new ArrayList<>();
		try {

			int main = 0;
			int detail = 0;
			int mutimain = 0;
			int mutidetail = 0;

			InputStream is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);

			int sheets = workbook.getNumberOfSheets();
			Map<String, String> tempSu = new LinkedHashMap<>();
			Map<String, String> tempFa = new LinkedHashMap<>();
			for (int i = 0; i < sheets; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				int rows = sheet.getPhysicalNumberOfRows();
				SchoolMaster schoolmaster = null;

				for (int j = 1; j < rows; j++) {
					schoolmaster = new SchoolMaster();

					XSSFRow row = sheet.getRow(j);
					if (transToString(row, 0) != null) {
						schoolmaster.setGraduateschoolcode(transToString(row, 0).getStringCellValue());
					}
					if (transToString(row, 1) != null) {
						schoolmaster.setGraduateschoolname(transToString(row, 1).getStringCellValue());
					}
					if (transToString(row, 2) != null) {
						schoolmaster.setGraduateschoolsystem(transToString(row, 2).getStringCellValue());
					}
					if (transToString(row, 3) != null) {
						schoolmaster.setGraduateschoolpublicprivate(transToString(row, 3).getStringCellValue());
					}
					if (transToString(row, 4) != null) {
						schoolmaster.setGraduateschoollevel(transToString(row, 4).getStringCellValue());
					}
					if (transToString(row, 5) != null) {
						schoolmaster.setGraduateschoolcitymarked(transToString(row, 5).getStringCellValue());
					}
					if (transToString(row, 6) != null) {
						schoolmaster.setGraduateschoolregion(transToString(row, 6).getStringCellValue());
					}
					if (transToString(row, 7) != null) {
						schoolmaster.setGraduateschoolcity(transToString(row, 7).getStringCellValue());
					}
					if (transToString(row, 8) != null) {
						schoolmaster.setGraduateschooldistrict(transToString(row, 8).getStringCellValue());
					}
					if (transToString(row, 9) != null) {
						schoolmaster.setGraduateschooladdress(transToString(row, 9).getStringCellValue());
					}
					if (transToString(row, 10) != null) {
						schoolmaster.setGraduateschooltranscode(transToString(row, 10).getStringCellValue());
					}

					if (transToString(row, 11) != null) {
						String detail_temp[] = transToString(row, 11).getStringCellValue().split("@");
						for (String item : detail_temp) {
							SchoolDetail schooldetail = new SchoolDetail();
							schooldetail.setGraduateschoolcode(transToString(row, 0).getStringCellValue());
							schooldetail.setGraduateschoolsynonymsnames(item);
							try {
								SynoymDetailRepository.save(schooldetail);
								if (!StringUtils.isEmpty(schoolmaster.getGraduateschoolcode())
										&& !"".equals(schoolmaster.getGraduateschoolcode())) {
									if (tempSu.get(schoolmaster.getGraduateschoolcode()) == null) {
										tempSu.put(schoolmaster.getGraduateschoolcode(), item);
									} else {
										String detemp = tempSu.get(schoolmaster.getGraduateschoolcode());
										detemp += "@" + item;
										tempSu.put(schoolmaster.getGraduateschoolcode(), detemp);
									}
								}

								detail++;
							} catch (Exception e) {
								if (!StringUtils.isEmpty(schoolmaster.getGraduateschoolcode())
										&& !"".equals(schoolmaster.getGraduateschoolcode())) {
									if (tempFa.get(schoolmaster.getGraduateschoolcode()) == null) {
										tempFa.put(schoolmaster.getGraduateschoolcode(), item);
									} else {
										String detemp = tempFa.get(schoolmaster.getGraduateschoolcode());
										detemp += "@" + item;
										tempFa.put(schoolmaster.getGraduateschoolcode(), detemp);
									}
								}

								mutidetail++;
							}

						}

					}
					if (transToString(row, 12) != null) {
						schoolmaster.setLat(transToString(row, 12).getStringCellValue());
					}
					if (transToString(row, 13) != null) {
						schoolmaster.setLng(transToString(row, 13).getStringCellValue());
					}

					schoolmaster.setFlag("1");

					excelItem.add(schoolmaster);
					try {
						Map<String, Object> result2 = new LinkedHashMap<>();
						SynoymMasterRepository.save(schoolmaster);

						result2.put("ID", schoolmaster.getGraduateschoolcode());
						result2.put("Name", schoolmaster.getGraduateschoolname());

						MasterResult.add(result2);

						main++;
					} catch (Exception e) {
						String resultS = null;
						resultS = schoolmaster.getGraduateschoolcode();
						MastererrorResult.add(resultS);
						mutimain++;
					}

				}

			}

			DetailerrorResult.add(tempFa);
			DetailResult.add(tempSu);
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date date = new Date();
			String strDate = sdFormat.format(date);
			result.put("main", main);
			result.put("mainInfo", MasterResult);
			result.put("mutimain", mutimain);
//			result.put("mutimainInfo", MastererrorResult);
			result.put("detail", detail);
			result.put("detailInfo", DetailResult);
			result.put("mutidetail", mutidetail);
//			result.put("mutidetailInfo", DetailerrorResult);
			result.put("modifyTime", strDate);

			LOGresult.put("main", main);
			LOGresult.put("mutimain", mutimain);
			LOGresult.put("detail", detail);
			LOGresult.put("mutidetail", mutidetail);

			LOGDetail.put("mainInfo", MasterResult);
			LOGDetail.put("mutimainInfo", MastererrorResult);
			LOGDetail.put("detailInfo", DetailResult);
			LOGDetail.put("mutidetailInfo", DetailerrorResult);
			LOGDetail.put("modifyTime", strDate);

			JSONObject json = new JSONObject(LOGDetail);

			String result_temp = LOGresult.toString();

			SchoolLog log = new SchoolLog();
			log.setLog(result_temp);
			log.setSynoymtype("Schoolsynonym");
			SchoolLogRepository.save(log);
			AllSyn AllSynitem = new AllSyn();
			AllSynitem.setId("Schoolsynonym");
			AllSynitem.setTablename("大專校院同義詞表");
			saveallsyno(AllSynitem);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Map<String, Object> batchImportGra(String fileName, MultipartFile file) {
		Map<String, Object> result = new LinkedHashMap<>();
		Map<String, Object> LOGresult = new LinkedHashMap<>();
		Map<String, Object> LOGDetail = new LinkedHashMap<>();

		List<GraSurvey> excelItem = new ArrayList<>();

		List<Object> MasterResult = new ArrayList<>();
		List<Object> DetailResult = new ArrayList<>();

		List<Object> MastererrorResult = new ArrayList<>();
		List<Object> DetailerrorResult = new ArrayList<>();

		try {

			int main = 0;
			int detail = 0;
			int mutimain = 0;
			int mutidetail = 0;

			InputStream is = file.getInputStream();
			XSSFWorkbook workbook = new XSSFWorkbook(is);

			int sheets = workbook.getNumberOfSheets();
			Map<String, String> tempSu = new LinkedHashMap<>();
			Map<String, String> tempFa = new LinkedHashMap<>();
			for (int i = 0; i < sheets; i++) {
				XSSFSheet sheet = workbook.getSheetAt(i);
				int rows = sheet.getPhysicalNumberOfRows();
				GraSurvey schoolmaster = null;

				for (int j = 1; j < rows; j++) {
					schoolmaster = new GraSurvey();

					XSSFRow row = sheet.getRow(j);
					if (transToString(row, 0) != null) {
						schoolmaster.setYqn(transToString(row, 0).getStringCellValue());
					}
					if (transToString(row, 1) != null) {
						schoolmaster.setQn(transToString(row, 1).getStringCellValue());
					}
					if (transToString(row, 2) != null) {
						schoolmaster.setQuestionid(transToString(row, 2).getStringCellValue());
					}
					if (transToString(row, 3) != null) {
						schoolmaster.setGraduateschoolcode(transToString(row, 3).getStringCellValue());
					}
					if (transToString(row, 4) != null) {
						schoolmaster.setSequence(transToString(row, 4).getStringCellValue());
					}
					if (transToString(row, 5) != null) {
						schoolmaster.setDescription(transToString(row, 5).getStringCellValue());
					}
					if (transToString(row, 6) != null) {
						schoolmaster.setDescription2(transToString(row, 6).getStringCellValue());
					}

					excelItem.add(schoolmaster);
					try {
						Map<String, Object> result2 = new LinkedHashMap<>();
						GraSurveyRepository.save(schoolmaster);

						result2.put("ID", schoolmaster.getGraduateschoolcode());

						MasterResult.add(result2);

						main++;
					} catch (Exception e) {
						String resultS = null;
						resultS = schoolmaster.getGraduateschoolcode();
						MastererrorResult.add(resultS);
						mutimain++;
					}

				}

			}

			DetailerrorResult.add(tempFa);
			DetailResult.add(tempSu);
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			Date date = new Date();
			String strDate = sdFormat.format(date);
			result.put("main", main);
			result.put("mainInfo", MasterResult);
			result.put("mutimain", mutimain);
//			result.put("mutimainInfo", MastererrorResult);
			result.put("detail", detail);
			result.put("detailInfo", DetailResult);
			result.put("mutidetail", mutidetail);
//			result.put("mutidetailInfo", DetailerrorResult);
			result.put("modifyTime", strDate);

			LOGresult.put("main", main);
			LOGresult.put("mutimain", mutimain);
			LOGresult.put("detail", detail);
			LOGresult.put("mutidetail", mutidetail);

			LOGDetail.put("mainInfo", MasterResult);
			LOGDetail.put("mutimainInfo", MastererrorResult);
			LOGDetail.put("detailInfo", DetailResult);
			LOGDetail.put("mutidetailInfo", DetailerrorResult);
			LOGDetail.put("modifyTime", strDate);

			JSONObject json = new JSONObject(LOGDetail);

			String result_temp = LOGresult.toString();
			AllSyn AllSynitem = new AllSyn();
			AllSynitem.setId("GraSurveysynonym");
			AllSynitem.setTablename("畢業生流向");
			saveallsyno(AllSynitem);
			
			SchoolLog log = new SchoolLog();
			log.setLog(result_temp);
			log.setSynoymtype("GraSurveysynonym");
			SchoolLogRepository.save(log);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

		return result;
	}

	public XSSFCell transToString(XSSFRow row, Integer num) {
		if (row != null) {
			XSSFCell cell = row.getCell(num);
			if (cell != null) {
				cell.setCellType(CellType.STRING);
				return cell;
			} else {
				return null;
			}
		}
		return null;
	}

	

}
