package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.Oversea_syn;

@Repository
public interface OverseaRepository extends JpaRepository<Oversea_syn, String>,
		PagingAndSortingRepository<Oversea_syn, String>, QuerydslPredicateExecutor<Oversea_syn> {
			
	Oversea_syn findById(Integer id);

	List<Oversea_syn> findByOverseatype(String overseatype);
	
	@Transactional
	void deleteByid(Integer item);

}
