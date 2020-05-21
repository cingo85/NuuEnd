package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.Transschool_syn;

@Repository
public interface TransschoolRepository extends JpaRepository<Transschool_syn, String>,PagingAndSortingRepository<Transschool_syn, String> , QuerydslPredicateExecutor<Transschool_syn>{
	
	Transschool_syn findByExchangedepteduid(String exchangedepteduid);
	
	Transschool_syn findByid(Integer id);
	
	@Transactional
	void deleteByid(Integer item);
}
