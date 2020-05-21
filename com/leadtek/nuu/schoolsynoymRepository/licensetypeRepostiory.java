package com.leadtek.nuu.schoolsynoymRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.licensetype_syn;

@Repository
public interface licensetypeRepostiory extends JpaRepository<licensetype_syn, String>,PagingAndSortingRepository<licensetype_syn, String> , QuerydslPredicateExecutor<licensetype_syn>{

	licensetype_syn findByLicenseid(String licenseid);
	
	@Transactional
	void deleteBylicenseid(String item);
}
