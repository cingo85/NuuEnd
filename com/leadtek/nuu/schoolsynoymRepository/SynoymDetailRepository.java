package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.SchoolDetail;

@Repository
public interface SynoymDetailRepository
		extends JpaRepository<SchoolDetail, String>,PagingAndSortingRepository<SchoolDetail, String>, QuerydslPredicateExecutor<SchoolDetail> {

	List<SchoolDetail> findAll();

	List<SchoolDetail> findBygraduateschoolcode(String graduateSchoolCode);

	@Modifying
	@Transactional
	void deleteById(Integer id);
	
	@Modifying
	@Transactional
	void deleteBygraduateschoolcode(String schoolCode);

	SchoolDetail save(SchoolDetail item);

}
