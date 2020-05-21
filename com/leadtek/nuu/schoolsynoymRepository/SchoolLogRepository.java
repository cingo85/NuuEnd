package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.leadtek.nuu.schoolsynoymEntity.SchoolLog;

public interface SchoolLogRepository extends JpaRepository<SchoolLog, String>,PagingAndSortingRepository<SchoolLog, String> , QuerydslPredicateExecutor<SchoolLog>{

	public List<SchoolLog> findBysynoymtypeOrderByIdDesc(String synonymType);
}
