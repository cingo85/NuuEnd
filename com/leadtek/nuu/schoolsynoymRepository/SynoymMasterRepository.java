package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.SchoolDetail;
import com.leadtek.nuu.schoolsynoymEntity.SchoolMaster;

@Repository
public interface SynoymMasterRepository extends JpaRepository<SchoolMaster, String>,PagingAndSortingRepository<SchoolMaster, String> , QuerydslPredicateExecutor<SchoolMaster>{

	List<SchoolMaster> findAll();
	
	@Modifying
	@Transactional
	SchoolMaster save(SchoolMaster item);

	List<SchoolMaster> findBygraduateschoolcode(String schoolCode);
	
	@Modifying
	@Transactional
	void deleteBygraduateschoolcode(String schoolCode);
}
