package com.leadtek.nuu.schoolsynoymService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadtek.nuu.schoolsynoymEntity.AllSyn;
import com.leadtek.nuu.schoolsynoymEntity.Dropstu_syn;
import com.leadtek.nuu.schoolsynoymEntity.Enrolltype_syn;
import com.leadtek.nuu.schoolsynoymEntity.Graeng_syn;
import com.leadtek.nuu.schoolsynoymEntity.Oversea_syn;
import com.leadtek.nuu.schoolsynoymEntity.S90unit;
import com.leadtek.nuu.schoolsynoymEntity.Suspend_syn;
import com.leadtek.nuu.schoolsynoymEntity.Transschool_syn;
import com.leadtek.nuu.schoolsynoymEntity.licensetype_syn;
import com.leadtek.nuu.schoolsynoymRepository.AllSynRepostiory;
import com.leadtek.nuu.schoolsynoymRepository.DeptcodeRepository;
import com.leadtek.nuu.schoolsynoymRepository.DropstuRepostiory;
import com.leadtek.nuu.schoolsynoymRepository.EnrolltypeRepostiory;
import com.leadtek.nuu.schoolsynoymRepository.LanguageRepository;
import com.leadtek.nuu.schoolsynoymRepository.OverseaRepository;
import com.leadtek.nuu.schoolsynoymRepository.SuspendRepository;
import com.leadtek.nuu.schoolsynoymRepository.TransschoolRepository;
import com.leadtek.nuu.schoolsynoymRepository.licensetypeRepostiory;

@Service
public class SynoymService2 {

	@Autowired
	AllSynRepostiory AllSynRepostiory;
	@Autowired
	OverseaRepository OverseaRepository;
	@Autowired
	SuspendRepository SuspendRepository;
	@Autowired
	TransschoolRepository TransschoolRepository;
	@Autowired
	EnrolltypeRepostiory EnrolltypeRepostiory;
	@Autowired
	DropstuRepostiory DropstuRepostiory;
	@Autowired
	licensetypeRepostiory licensetypeRepostiory;

	@Autowired
	DeptcodeRepository DeptcodeRepository;

	@Autowired
	LanguageRepository LanguageRepository;

	public List<AllSyn> findAllSyn() {
		return AllSynRepostiory.findAll();
	}

	public void saveallsyno(AllSyn entity) {
		Date now = new Date();
		entity.setUpdatedate(now);
		AllSynRepostiory.save(entity);
	}

	public List<Map<String, Object>> findAllOversea() {
		List<Map<String, Object>> result = new ArrayList<>();
		List<Oversea_syn> item = OverseaRepository.findAll();

		for (Oversea_syn temp : item) {
			Map<String, Object> tempr = new LinkedHashMap<>();

			tempr.put("id", temp.getId());
			tempr.put("overseatype", temp.getOverseatype());

			String codesp[] = temp.getNuuname().split("@");
			List<String> codevaluelist = new ArrayList<>();
			for (String code : codesp) {
				codevaluelist.add(code);
			}

			List<Map<String, String>> tt = new ArrayList<>();
			for (int i = 0; i < codevaluelist.size(); i++) {
				Map<String, String> codenametemp = new LinkedHashMap<>();
				codenametemp.put("id", String.valueOf(i));
				codenametemp.put("nuuname", codevaluelist.get(i));
				tt.add(codenametemp);
			}
			tempr.put("codename", tt);
			result.add(tempr);
		}
		return result;
	}

	public List<Map<String, Object>> findAllSuspend() {
		List<Map<String, Object>> result = new ArrayList<>();

		List<Suspend_syn> item = SuspendRepository.findAll();
		for (Suspend_syn temp : item) {

			Map<String, Object> tempr = new LinkedHashMap<>();

			tempr.put("suspendremarkid", temp.getSuspendremarkid());
			tempr.put("suspendremark", temp.getSuspendremark());
			tempr.put("version", temp.getVersion());

			String codesp[] = temp.getNuucode().split("@");
			List<String> codevaluelist = new ArrayList<>();
			for (String code : codesp) {
				codevaluelist.add(code);
			}

			List<String> namevaluelist = new ArrayList<>();
			String namesp[] = temp.getNuuname().split("@");
			for (String name : namesp) {
				namevaluelist.add(name);
			}

			List<Map<String, String>> tt = new ArrayList<>();
			for (int i = 0; i < codevaluelist.size(); i++) {
				Map<String, String> codenametemp = new LinkedHashMap<>();
				codenametemp.put("id", String.valueOf(i));
				codenametemp.put("nuucode", codevaluelist.get(i));
				codenametemp.put("nuuname", namevaluelist.get(i));
				tt.add(codenametemp);
			}
			tempr.put("codename", tt);
			result.add(tempr);
		}

		return result;

	}

	public List<Transschool_syn> findAllTransschool() {
		return TransschoolRepository.findAll();
	}

	public List<Map<String, Object>> findAllEnrolltype() {
		List<Map<String, Object>> result = new ArrayList<>();

		List<Enrolltype_syn> item = EnrolltypeRepostiory.findAll();
		for (Enrolltype_syn temp : item) {

			Map<String, Object> tempr = new LinkedHashMap<>();

			tempr.put("enrolltypeid", temp.getEnrolltypeid());
			tempr.put("enrolltypename", temp.getEnrolltypename());
			tempr.put("version", temp.getVersion());

			String codesp[] = temp.getNuucode().split("@");
			List<String> codevaluelist = new ArrayList<>();
			for (String code : codesp) {
				codevaluelist.add(code);
			}

			List<String> namevaluelist = new ArrayList<>();
			String namesp[] = temp.getNuuname().split("@");
			for (String name : namesp) {
				namevaluelist.add(name);
			}

			List<Map<String, String>> tt = new ArrayList<>();
			for (int i = 0; i < codevaluelist.size(); i++) {
				Map<String, String> codenametemp = new LinkedHashMap<>();
				codenametemp.put("id", String.valueOf(i));
				codenametemp.put("nuucode", codevaluelist.get(i));
				codenametemp.put("nuuname", namevaluelist.get(i));
				tt.add(codenametemp);
			}
			tempr.put("codename", tt);
			result.add(tempr);
		}

		return result;
	}

	public List<Map<String, Object>> findAllDropstu() {
		List<Map<String, Object>> result = new ArrayList<>();

		List<Dropstu_syn> item = DropstuRepostiory.findAll();

		for (Dropstu_syn temp : item) {

			Map<String, Object> tempr = new LinkedHashMap<>();

			tempr.put("dropremarkid", temp.getDropremarkid());
			tempr.put("dropremark", temp.getDropremark());
			tempr.put("version", temp.getVersion());

			String codesp[] = temp.getNuucode().split("@");
			List<String> codevaluelist = new ArrayList<>();
			for (String code : codesp) {
				codevaluelist.add(code);
			}

			List<String> namevaluelist = new ArrayList<>();
			String namesp[] = temp.getNuuname().split("@");
			for (String name : namesp) {
				namevaluelist.add(name);
			}
			List<Map<String, String>> tt = new ArrayList<>();
			for (int i = 0; i < codevaluelist.size(); i++) {
				Map<String, String> codenametemp = new LinkedHashMap<>();
				codenametemp.put("id", String.valueOf(i));
				codenametemp.put("nuucode", codevaluelist.get(i));
				codenametemp.put("nuuname", namevaluelist.get(i));
				tt.add(codenametemp);
			}
			tempr.put("codename", tt);
			result.add(tempr);
		}

		return result;
	}

	public List<licensetype_syn> findAlllicensetype() {
		return licensetypeRepostiory.findAll();
	}

	public List<S90unit> findAllS90unit() {
		return DeptcodeRepository.findAll();
	}

	public List<Map<String, Object>> findAllLanguage() {
		List<Map<String, Object>> result = new ArrayList<>();
		List<Graeng_syn> item = LanguageRepository.findAll();
		for (Graeng_syn temp : item) {
			Map<String, Object> tempr = new LinkedHashMap<>();
			tempr.put("id", temp.getId());
			tempr.put("langtype", temp.getLangType());
			tempr.put("version", temp.getVersion());
			String codesp[] = temp.getNuuname().split("@");
			List<String> codevaluelist = new ArrayList<>();
			for (String code : codesp) {
				codevaluelist.add(code);
			}

			List<Map<String, String>> tt = new ArrayList<>();
			for (int i = 0; i < codevaluelist.size(); i++) {
				Map<String, String> codenametemp = new LinkedHashMap<>();
				codenametemp.put("id", String.valueOf(i));
				codenametemp.put("nuuname", codevaluelist.get(i));
				tt.add(codenametemp);
			}
			tempr.put("codename", tt);
			result.add(tempr);
		}

		return result;
	}

	public List<Oversea_syn> saveOversea(Object item) {

		Oversea_syn result = new Oversea_syn();

		Map<Object, Object> temp = (Map<Object, Object>) item;

		if ((Integer) temp.get("id") != null) {
			result.setId((Integer) temp.get("id"));
			Oversea_syn db = OverseaRepository.findById((Integer) temp.get("id"));
			if (db != null) {
				result.setVersion(db.getVersion());
			}
			result.setOverseatype((String) temp.get("overseatype"));
			String recode = "";
			List<Map<String, String>> codename = (List<Map<String, String>>) temp.get("codename");
			if (codename != null) {
				for (Map<String, String> items : codename) {

					if (("").equals(recode)) {
						recode = items.get("nuuname");
					} else {
						recode += "@" + items.get("nuuname");
					}

				}
			}

			result.setNuuname(recode);
		}else {
			result.setOverseatype((String) temp.get("overseatype"));
			String recode = "";
			List<Map<String, String>> codename = (List<Map<String, String>>) temp.get("codename");
			if (codename != null) {
				for (Map<String, String> items : codename) {

					if (("").equals(recode)) {
						recode = items.get("nuuname");
					} else {
						recode += "@" + items.get("nuuname");
					}

				}
			}

			result.setNuuname(recode);
		}

		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Oversea");
		syn.setTablename("海外名單");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		OverseaRepository.save(result);
		return OverseaRepository.findByOverseatype((String)temp.get("overseatype"));
	}

	public List<Suspend_syn> saveSuspend(Object item) {

		Suspend_syn result = new Suspend_syn();
		Map<Object, Object> temp = (Map<Object, Object>) item;
		result.setSuspendremarkid((String) temp.get("suspendremarkid"));
		result.setSuspendremark((String) temp.get("suspendremark"));
		result.setVersion((Integer) temp.get("version"));
		String recode = "";
		String rename = "";

		List<Map<String, String>> codename = (List<Map<String, String>>) temp.get("codename");

		for (Map<String, String> items : codename) {

			if (("").equals(recode)) {
				recode = items.get("nuucode");
			} else {
				recode += "@" + items.get("nuucode");
			}

			if (("").equals(rename)) {
				rename = items.get("nuuname");
			} else {
				rename += "@" + items.get("nuuname");
			}

		}

		result.setNuucode(recode);
		result.setNuuname(rename);

//		SuspendRepository.deleteBysuspendremarkid(result.getSuspendremarkid());
		SuspendRepository.save(result);
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Suspend");
		syn.setTablename("休學代碼");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		return SuspendRepository.findBysuspendremarkid((String) temp.get("suspendremarkid"));

	}

	public void saveTransschool(Transschool_syn item) {

		if (item.getId() != null) {
			Transschool_syn rsitem = TransschoolRepository.findByid(item.getId());			
			
			Transschool_syn rs = new Transschool_syn();
			rs.setId(item.getId());
			rs.setExchangedepteduid(item.getExchangedepteduid());
			rs.setExchangeschool(item.getExchangeschool());
			rs.setExchangecollege(item.getExchangecollege());
			rs.setExchangedept(item.getExchangedept());
			rs.setExchangetype(item.getExchangetype());
			rs.setVersion(rsitem.getVersion());
			TransschoolRepository.save(rs);
		} else {
			TransschoolRepository.save(item);
		}

		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Transschool");
		syn.setTablename("交換校系");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		
	}

	public List<Enrolltype_syn> saveEnrolltype(Object item) {
		Enrolltype_syn result = new Enrolltype_syn();
		Map<Object, Object> temp = (Map<Object, Object>) item;
		result.setEnrolltypeid((String) temp.get("enrolltypeid"));
		result.setEnrolltypename((String) temp.get("enrolltypename"));
		result.setVersion((Integer) temp.get("version"));
		String recode = "";
		String rename = "";

		List<Map<String, String>> codename = (List<Map<String, String>>) temp.get("codename");

		if (codename != null)
			for (Map<String, String> items : codename) {

				if (("").equals(recode)) {
					recode = items.get("nuucode");
				} else {
					recode += "@" + items.get("nuucode");
				}

				if (("").equals(rename)) {
					rename = items.get("nuuname");
				} else {
					rename += "@" + items.get("nuuname");
				}

			}

		result.setNuucode(recode);
		result.setNuuname(rename);

		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Enrolltype");
		syn.setTablename("入學管道");
		syn.setUpdatedate(now);
		saveallsyno(syn);

		EnrolltypeRepostiory.save(result);
		return EnrolltypeRepostiory.findByEnrolltypeid(result.getEnrolltypeid());
	}

	public List<Dropstu_syn> saveDropstu(Object item) {

		Dropstu_syn result = new Dropstu_syn();

		Map<Object, Object> temp = (Map<Object, Object>) item;

		result.setDropremarkid((String) temp.get("dropremarkid"));
		result.setDropremark((String) temp.get("dropremark"));
		result.setVersion((Integer) temp.get("version"));
		String recode = "";
		String rename = "";

		List<Map<String, String>> codename = (List<Map<String, String>>) temp.get("codename");

		for (Map<String, String> items : codename) {

			if (("").equals(recode)) {
				recode = items.get("nuucode");
			} else {
				recode += "@" + items.get("nuucode");
			}

			if (("").equals(rename)) {
				rename = items.get("nuuname");
			} else {
				rename += "@" + items.get("nuuname");
			}

		}

		result.setNuucode(recode);
		result.setNuuname(rename);
		AllSyn AllSynitem = new AllSyn();
		AllSynitem.setId("Dropstu");
		AllSynitem.setTablename("退學代碼");
		saveallsyno(AllSynitem);
		DropstuRepostiory.save(result);
		return DropstuRepostiory.findBydropremarkid((String) temp.get("dropremarkid"));
	}

	public void savelicensetype(licensetype_syn item) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("licensetype");
		syn.setTablename("證照分級登錄表");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		licensetypeRepostiory.save(item);
	}

	public void saveS90unit(S90unit item) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Deptcode");
		syn.setTablename("系所代碼");
		syn.setUpdatedate(now);
		saveallsyno(syn);
//		DeptcodeRepository.deleteByuntid(item.getUntid());
		DeptcodeRepository.save(item);
	}

	public List<Graeng_syn> saveLanguage(Object item) {
		Graeng_syn result = new Graeng_syn();

		Map<Object, Object> temp = (Map<Object, Object>) item;

		if ((Integer) temp.get("id") != null) {
			result.setId((Integer) temp.get("id"));
			Graeng_syn db = LanguageRepository.findById((Integer) temp.get("id"));
			if (db != null) {
				result.setVersion(db.getVersion());
			}

			result.setLangType((String) temp.get("langtype"));
			String recode = "";
			List<Map<String, String>> codename = (List<Map<String, String>>) temp.get("codename");
			if (codename != null) {
				for (Map<String, String> items : codename) {

					if (("").equals(recode)) {
						recode = items.get("nuuname");
					} else {
						recode += "@" + items.get("nuuname");
					}

				}
			}

			result.setNuuname(recode);
		} else {
			result.setLangType((String) temp.get("langtype"));
			String recode = "";
			List<Map<String, String>> codename = (List<Map<String, String>>) temp.get("codename");
			if (codename != null) {
				for (Map<String, String> items : codename) {

					if (("").equals(recode)) {
						recode = items.get("nuuname");
					} else {
						recode += "@" + items.get("nuuname");
					}

				}
			}

			result.setNuuname(recode);
		}

		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Language");
		syn.setTablename("語言能力畢業門檻");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		LanguageRepository.save(result);
		return LanguageRepository.findByLangType((String) temp.get("langtype"));
	}

	public void deleteDrop(String item) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Dropstu");
		syn.setTablename("退學代碼");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		DropstuRepostiory.deleteBydropremarkid(item);
	}

	public void deleteSus(String item) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Suspend");
		syn.setTablename("休學代碼");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		SuspendRepository.deleteBysuspendremarkid(item);
	}

	public void deleteEnro(String item) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Enrolltype");
		syn.setTablename("入學管道");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		EnrolltypeRepostiory.deleteByenrolltypeid(item);
	}

	public void deleteTrans(Integer item) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Transschool");
		syn.setTablename("交換校系");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		TransschoolRepository.deleteByid(item);
	}

	public void deleteOver(Integer item) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Oversea");
		syn.setTablename("海外名單");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		OverseaRepository.deleteByid(item);
	}

	public void deleteLicen(String item) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("licensetype");
		syn.setTablename("證照分級登錄表");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		licensetypeRepostiory.deleteBylicenseid(item);
	}

	public void deleteLanguage(Integer item) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Language");
		syn.setTablename("語言能力畢業門檻");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		LanguageRepository.deleteByid(item);
	}

	public void deleteDept(String item) {
		Date now = new Date();
		AllSyn syn = new AllSyn();
		syn.setId("Deptcode");
		syn.setTablename("系所代碼");
		syn.setUpdatedate(now);
		saveallsyno(syn);
		DeptcodeRepository.deleteByuntid(item);
	}

}
