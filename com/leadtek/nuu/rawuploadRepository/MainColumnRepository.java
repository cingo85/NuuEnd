package com.leadtek.nuu.rawuploadRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.rawuploadEntity.MainColumn;

@Repository
public interface MainColumnRepository extends JpaRepository<MainColumn, String>,PagingAndSortingRepository<MainColumn, String> , QuerydslPredicateExecutor<MainColumn>{

	List<MainColumn> findByTableuuid(String uuid);
	
	
}
