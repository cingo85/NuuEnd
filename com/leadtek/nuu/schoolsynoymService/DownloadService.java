package com.leadtek.nuu.schoolsynoymService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadtek.nuu.schoolsynoymEntity.Dropstu_syn;
import com.leadtek.nuu.schoolsynoymEntity.Enrolltype_syn;
import com.leadtek.nuu.schoolsynoymEntity.GraSurvey;
import com.leadtek.nuu.schoolsynoymEntity.Graeng_syn;
import com.leadtek.nuu.schoolsynoymEntity.Oversea_syn;
import com.leadtek.nuu.schoolsynoymEntity.SchoolDetail;
import com.leadtek.nuu.schoolsynoymEntity.SchoolMaster;
import com.leadtek.nuu.schoolsynoymEntity.Suspend_syn;
import com.leadtek.nuu.schoolsynoymRepository.DropstuRepostiory;
import com.leadtek.nuu.schoolsynoymRepository.EnrolltypeRepostiory;
import com.leadtek.nuu.schoolsynoymRepository.GraSurveyRepository;
import com.leadtek.nuu.schoolsynoymRepository.LanguageRepository;
import com.leadtek.nuu.schoolsynoymRepository.OverseaRepository;
import com.leadtek.nuu.schoolsynoymRepository.SuspendRepository;
import com.leadtek.nuu.schoolsynoymRepository.SynoymDetailRepository;
import com.leadtek.nuu.schoolsynoymRepository.SynoymMasterRepository;

@Service
public class DownloadService {

	@Autowired
	SynoymMasterRepository SynoymMasterRepository;

	@Autowired
	SynoymDetailRepository SynoymDetailRepository;

	@Autowired
	GraSurveyRepository GraSurveyRepository;

	@Autowired
	EnrolltypeRepostiory EnrolltypeRepostiory;

	@Autowired
	SuspendRepository SuspendRepository;

	@Autowired
	OverseaRepository OverseaRepository;

	@Autowired
	DropstuRepostiory DropstuRepostiory;

	@Autowired
	LanguageRepository LanguageRepository;

	public ByteArrayInputStream exportSchool() throws SQLException, IOException {
		Workbook workbook = new SXSSFWorkbook(1000000); // 建立檔案
		Sheet sheet = workbook.createSheet("表單");// 建立工作表

		List<LinkedHashMap<String, String>> rstemp = new ArrayList<LinkedHashMap<String, String>>();
		List<SchoolMaster> sm = SynoymMasterRepository.findAll();
		List<SchoolDetail> sd = SynoymDetailRepository.findAll();
		for (SchoolMaster it : sm) {
			LinkedHashMap<String, String> sybtemp = new LinkedHashMap<>();
			String synName = "";
			sybtemp.put("學校代碼", it.getGraduateschoolcode());
			sybtemp.put("學校名稱", it.getGraduateschoolname());
			sybtemp.put("體系", it.getGraduateschoolsystem());
			sybtemp.put("公私立", it.getGraduateschoolpublicprivate());
			sybtemp.put("層級", it.getGraduateschoollevel());
			sybtemp.put("是否為都會型高中", it.getGraduateschoolcitymarked());
			sybtemp.put("地區", it.getGraduateschoolregion());
			sybtemp.put("縣市別", it.getGraduateschoolcity());
			sybtemp.put("鄉鎮區", it.getGraduateschooldistrict());
			sybtemp.put("地址", it.getGraduateschooladdress());
			sybtemp.put("代碼異動紀錄", it.getGraduateschooltranscode());
			sybtemp.put("lat", it.getLat());
			sybtemp.put("lng", it.getLng());
			for (SchoolDetail id : sd) {
				if (id.getGraduateschoolcode().equals(it.getGraduateschoolcode())) {
					if (sybtemp.get("同義詞") == null) {
						synName = id.getGraduateschoolsynonymsnames();
						sybtemp.put("同義詞", synName);
					} else {
						String temp = sybtemp.get("同義詞");
						temp += "@" + id.getGraduateschoolsynonymsnames();
						sybtemp.put("同義詞", temp);
					}
				}
			}
			rstemp.add(sybtemp);
		}

		createrow(rstemp, sheet);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	public ByteArrayInputStream exportGraSurvey() throws SQLException, IOException {
		Workbook workbook = new SXSSFWorkbook(1000000); // 建立檔案
		Sheet sheet = workbook.createSheet("表單");// 建立工作表

		List<LinkedHashMap<String, String>> rstemp = new ArrayList<LinkedHashMap<String, String>>();
		List<GraSurvey> sm = GraSurveyRepository.findAll();
		for (GraSurvey it : sm) {
			LinkedHashMap<String, String> sybtemp = new LinkedHashMap<>();
			sybtemp.put("問卷答題代號", it.getGraduateschoolcode());
			sybtemp.put("畢業滿年度", it.getYqn());
			sybtemp.put("畢業學年滿年度", it.getQn());
			sybtemp.put("問卷題號", it.getQuestionid());
			sybtemp.put("問卷答題選項", it.getSequence());
			sybtemp.put("問卷答題選項描述", it.getDescription());
			sybtemp.put("問卷答題代碼轉換", it.getDescription2());

			rstemp.add(sybtemp);
		}

		createrow(rstemp, sheet);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	public ByteArrayInputStream exportEnrollyear() throws SQLException, IOException {
		Workbook workbook = new SXSSFWorkbook(1000000); // 建立檔案
		Sheet sheet = workbook.createSheet("表單");// 建立工作表

		List<LinkedHashMap<String, String>> rstemp = new ArrayList<LinkedHashMap<String, String>>();
		List<Enrolltype_syn> sm = EnrolltypeRepostiory.findAll();
		for (Enrolltype_syn it : sm) {
			LinkedHashMap<String, String> sybtemp = new LinkedHashMap<>();
			sybtemp.put("入學管道代碼", it.getEnrolltypeid());
			sybtemp.put("入學管道名稱", it.getEnrolltypename());
			sybtemp.put("NUUCODE", it.getNuucode());
			sybtemp.put("NUUNAME", it.getNuuname());

			rstemp.add(sybtemp);
		}

		createrow(rstemp, sheet);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	public ByteArrayInputStream exportSuspend() throws SQLException, IOException {
		Workbook workbook = new SXSSFWorkbook(1000000); // 建立檔案
		Sheet sheet = workbook.createSheet("表單");// 建立工作表

		List<LinkedHashMap<String, String>> rstemp = new ArrayList<LinkedHashMap<String, String>>();
		List<Suspend_syn> sm = SuspendRepository.findAll();
		for (Suspend_syn it : sm) {
			LinkedHashMap<String, String> sybtemp = new LinkedHashMap<>();
			sybtemp.put("休學代碼", it.getSuspendremarkid());
			sybtemp.put("休學名稱", it.getSuspendremark());
			sybtemp.put("NUUCODE", it.getNuucode());
			sybtemp.put("NUUNAME", it.getNuuname());

			rstemp.add(sybtemp);
		}

		createrow(rstemp, sheet);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	public ByteArrayInputStream exportOversea() throws IOException {
		Workbook workbook = new SXSSFWorkbook(1000000); // 建立檔案
		Sheet sheet = workbook.createSheet("表單");// 建立工作表

		List<LinkedHashMap<String, String>> rstemp = new ArrayList<LinkedHashMap<String, String>>();
		List<Oversea_syn> sm = OverseaRepository.findAll();
		for (Oversea_syn it : sm) {
			LinkedHashMap<String, String> sybtemp = new LinkedHashMap<>();
			sybtemp.put("赴海外類別", it.getOverseatype());

			if (sybtemp.get("同義詞") == null) {
				sybtemp.put("同義詞", it.getNuuname());
			} else {
				String temp = sybtemp.get("同義詞");
				temp += "@" + it.getNuuname();
				sybtemp.put("同義詞", temp);
			}

			rstemp.add(sybtemp);
		}

		createrow(rstemp, sheet);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	public ByteArrayInputStream exportDropstu() throws IOException {
		Workbook workbook = new SXSSFWorkbook(1000000); // 建立檔案
		Sheet sheet = workbook.createSheet("表單");// 建立工作表

		List<LinkedHashMap<String, String>> rstemp = new ArrayList<LinkedHashMap<String, String>>();
		List<Dropstu_syn> sm = DropstuRepostiory.findAll();
		for (Dropstu_syn it : sm) {
			LinkedHashMap<String, String> sybtemp = new LinkedHashMap<>();
			sybtemp.put("退學代碼", it.getDropremarkid());
			sybtemp.put("退學名稱", it.getDropremark());
			sybtemp.put("NUUCODE", it.getNuucode());
			sybtemp.put("NUUNAME", it.getNuuname());

			rstemp.add(sybtemp);
		}

		createrow(rstemp, sheet);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	public ByteArrayInputStream exportLanguage() throws IOException {
		Workbook workbook = new SXSSFWorkbook(1000000); // 建立檔案
		Sheet sheet = workbook.createSheet("表單");// 建立工作表

		List<LinkedHashMap<String, String>> rstemp = new ArrayList<LinkedHashMap<String, String>>();
		List<Graeng_syn> sm = LanguageRepository.findAll();
		for (Graeng_syn it : sm) {
			LinkedHashMap<String, String> sybtemp = new LinkedHashMap<>();
			sybtemp.put("語言證照類別", it.getLangType());

			if (sybtemp.get("NUUNAME") == null) {
				sybtemp.put("NUUNAME", it.getNuuname());
			} else {
				String temp = sybtemp.get("NUUNAME");
				temp += "@" + it.getNuuname();
				sybtemp.put("NUUNAME", temp);
			}

			rstemp.add(sybtemp);
		}

		createrow(rstemp, sheet);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		workbook.write(outputStream);

		return new ByteArrayInputStream(outputStream.toByteArray());
	}

	public void createrow(List<LinkedHashMap<String, String>> rstemp, Sheet sheet) {

		int rowNum = 1;
		Map<String, Map<String, String>> temp = new LinkedHashMap<>();

		for (Map<String, String> titlevalue : rstemp) {
			Row row = sheet.createRow(0);
			int i = 0;
			for (Entry<String, String> entry : titlevalue.entrySet()) {
				row.createCell(i).setCellValue(entry.getKey());
				i += 1;
			}
		}

		for (Map<String, String> itemvalue : rstemp) {
			Row row = sheet.createRow(rowNum);
			int j = 0;
			for (Entry<String, String> entry : itemvalue.entrySet()) {
				if (sheet.getRow(0) != null) {
					if (sheet.getRow(0).getCell(j) != null) {
						String title = sheet.getRow(0).getCell(j).getStringCellValue();
						if (entry.getKey().equals(title)) {
							row.createCell(j).setCellValue(entry.getValue());
							j += 1;
						}
					}
				}

			}
			rowNum++;
		}
	}

}
