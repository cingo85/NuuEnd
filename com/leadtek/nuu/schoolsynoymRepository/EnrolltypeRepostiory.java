package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.Enrolltype_syn;

@Repository
public interface EnrolltypeRepostiory extends JpaRepository<Enrolltype_syn, String>,PagingAndSortingRepository<Enrolltype_syn, String> , QuerydslPredicateExecutor<Enrolltype_syn>{
	
	
	List<Enrolltype_syn> findByEnrolltypeid(String enrolltypeid);
	
	@Modifying
	@Transactional
	void deleteByenrolltypeid(String id);
}
