package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.GraSurvey;
import com.leadtek.nuu.schoolsynoymEntity.SchoolMaster;

public interface GraSurveyRepository extends JpaRepository<GraSurvey, String>,PagingAndSortingRepository<GraSurvey, String> , QuerydslPredicateExecutor<GraSurvey>{
	List<GraSurvey> findBygraduateschoolcode(String schoolCode);
	
	@Modifying
	@Transactional
	void deleteBygraduateschoolcode(String schoolCode);
}
