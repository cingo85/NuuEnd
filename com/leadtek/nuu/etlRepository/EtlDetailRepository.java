package com.leadtek.nuu.etlRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.leadtek.nuu.etlEntity.ETlMaster;
import com.leadtek.nuu.etlEntity.EtlDetail;
import com.leadtek.nuu.schoolsynoymEntity.GraSurvey;

public interface EtlDetailRepository extends JpaRepository<EtlDetail, String>,PagingAndSortingRepository<EtlDetail, String>{

	EtlDetail findByid(Integer id);
	
	List<EtlDetail> findBytablename(String tablename);
	
	List<EtlDetail> findBycolumngroup(String groupname);
	
	List<EtlDetail> findByetltable(String etlgroup);
}
