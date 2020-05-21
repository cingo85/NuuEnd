package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.Dropstu_syn;

@Repository
public interface DropstuRepostiory extends JpaRepository<Dropstu_syn, String>,PagingAndSortingRepository<Dropstu_syn, String> , QuerydslPredicateExecutor<Dropstu_syn>{
	
	@Transactional
	void deleteBydropremarkid(String item);
	
	List<Dropstu_syn> findBydropremarkid (String item);
	
}
