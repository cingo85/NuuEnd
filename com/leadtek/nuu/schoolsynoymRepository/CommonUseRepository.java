package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.schoolsynoymEntity.CommonUse;

@Repository
public interface CommonUseRepository extends JpaRepository<CommonUse, String>,PagingAndSortingRepository<CommonUse, String> , QuerydslPredicateExecutor<CommonUse>{
	
	public List<CommonUse> findBytableengname(String SchoolCode);
	

}
