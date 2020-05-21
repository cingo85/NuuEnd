package com.leadtek.nuu.schoolsynoymRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.schoolsynoymEntity.AllSyn;

@Repository
public interface AllSynRepostiory extends JpaRepository<AllSyn, String>,PagingAndSortingRepository<AllSyn, String> , QuerydslPredicateExecutor<AllSyn>{
	
}
