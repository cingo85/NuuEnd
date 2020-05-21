package com.leadtek.nuu.rawuploadService;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadtek.nuu.rawuploadEntity.MainColumn;
import com.leadtek.nuu.rawuploadEntity.MainTable;
import com.leadtek.nuu.rawuploadEntity.UploadSyn;
import com.leadtek.nuu.rawuploadRepository.MainColumnRepository;
import com.leadtek.nuu.rawuploadRepository.MainTableRepository;
import com.leadtek.nuu.rawuploadRepository.UploadSynRepository;
import com.leadtek.nuu.schoolsynoymEntity.Transschool_syn;
import com.leadtek.nuu.schoolsynoymRepository.DeptcodeRepository;
import com.leadtek.nuu.schoolsynoymRepository.TransschoolRepository;
import com.leadtek.nuu.schoolsynoymRepository.licensetypeRepostiory;

@Service
public class MainColumnService {

	@Autowired
	MainTableRepository maintable;

	@Autowired
	MainColumnRepository maincolumn;

	@Autowired
	UploadSynRepository uploadSynRepository;

	@Autowired
	DeptcodeRepository DeptcodeRepository;

	@Autowired
	licensetypeRepostiory licensetypeRepostiory;

	@Autowired
	TransschoolRepository TransschoolRepository;

	public List<MainColumn> findbyuuit(String uuid) {

		MainTable mt = maintable.findByTableuuid(uuid);

		List<MainColumn> res = maincolumn.findByTableuuid(uuid);
		for (MainColumn item : res) {
			Set<Object> optionio = new HashSet<>();

			Set<Object> exchangeSchool_set = new HashSet<Object>();
			Map<String, Object> exchangeSchool = new LinkedHashMap<>();

			Set<Object> exchangeCollege_set = new HashSet<Object>();
			Map<String, Object> exchangeCollege = new LinkedHashMap<>();

			Set<Object> exchangeDept_list = new HashSet<>();
			Map<String, Object> exchangeDept = new LinkedHashMap<>();

			Map<String, String> temp = new LinkedHashMap<>();

			if ("RAW021".equals(mt.getTablecode())) {
				licensetypeRepostiory.findAll().forEach(e -> temp.put(e.getLicenseid(),
						e.getLicenselevel() + ";" + e.getLicensename() + ";" + e.getLicenserank()));
				optionio.add(temp);
			}

			if ("RAW004".equals(mt.getTablecode())) {

				DeptcodeRepository.findAll().forEach(e -> optionio.add(e.getUntname()));
//						optionio.add(spl[1]);
			}

			if ("RAW012".equals(mt.getTablecode())) {
				List<Transschool_syn> rsitem = TransschoolRepository.findAll();

				for (Transschool_syn r : rsitem) {
					Map<String, Object> exchangeSchool_temp = new LinkedHashMap<>();
					Map<String, Object> exchangeCollege_temp = new LinkedHashMap<>();
					Map<String, Object> temp_obj = new LinkedHashMap<String, Object>();

					exchangeSchool_temp.put("id", r.getExchangeschool());
					exchangeSchool_temp.put("name", r.getExchangeschool());
					exchangeSchool_temp.put("upLayer", "");
					exchangeSchool_set.add(exchangeSchool_temp);
					exchangeSchool.put("exchangeSchool", exchangeSchool_set);

					exchangeCollege_temp.put("id", r.getExchangeschool() + "_" + r.getExchangecollege());
					exchangeCollege_temp.put("name", r.getExchangecollege());
					exchangeCollege_temp.put("upLayer", r.getExchangeschool());
					exchangeCollege_set.add(exchangeCollege_temp);
					exchangeCollege.put("exchangeCollege", exchangeCollege_set);

					temp_obj.put("id", r.getExchangecollege() + "_" + r.getExchangedept());
					temp_obj.put("name", r.getExchangedept());
					temp_obj.put("upLayer", r.getExchangecollege());
					exchangeDept_list.add(temp_obj);
					exchangeDept.put("exchangeDept", exchangeDept_list);
				}
				optionio.add(exchangeSchool);
				optionio.add(exchangeCollege);
				optionio.add(exchangeDept);

			}

			if ("RAW007".equals(mt.getTablecode())) {
				optionio.add("前測");
				optionio.add("後測");
			}

			if ("RAW004".equals(mt.getTablecode()) && "Unt_name".equals(item.getColumnename())) {
				item.setOption(optionio);
			}

			if ("RAW012".equals(mt.getTablecode()) && "ExchangeSchool".equals(item.getColumnename())) {
				item.setOption(optionio);
			}

			if ("RAW021".equals(mt.getTablecode()) && "LicenseID".equals(item.getColumnename())) {
				item.setOption(optionio);
			}

		}

		return res;
	}

}
