package com.leadtek.nuu.schoolsynoymService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.leadtek.nuu.schoolsynoymEntity.CommonUse;
import com.leadtek.nuu.schoolsynoymRepository.CommonUseRepository;

@Service
public class CommonService {
	
	@Autowired
	CommonUseRepository commonUseRepository;
	
	public List<CommonUse> findTableByName(String tableName){
		return commonUseRepository.findBytableengname(tableName);
	}
	
	public Set<String> findAllTable() {
		
		List<CommonUse> item = commonUseRepository.findAll();
		
		Set<String> result = new HashSet();
		
		
		for(CommonUse re : item) {
			result.add(re.getTableengname());
		}
		return result;
	}

}
