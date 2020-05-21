package com.leadtek.nuu.etlRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.leadtek.nuu.etlEntity.ETlMaster;
import com.leadtek.nuu.etlEntity.EtlDetail;

public interface EtlMasterRepository extends JpaRepository<ETlMaster, String>,PagingAndSortingRepository<ETlMaster, String>{
	ETlMaster findBytablename(String tablename);
	
	ETlMaster findByid(Integer id);
}
