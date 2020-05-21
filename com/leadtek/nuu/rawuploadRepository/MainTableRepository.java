package com.leadtek.nuu.rawuploadRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.rawuploadEntity.MainTable;

@Repository
public interface MainTableRepository extends JpaRepository<MainTable, String>,
		PagingAndSortingRepository<MainTable, String>, QuerydslPredicateExecutor<MainTable>,JpaSpecificationExecutor<MainTable> {

	MainTable findByTableuuid(String uuid);

	Page<MainTable> findAll(Pageable pageable);


	Page<MainTable> findAll(Specification<MainTable> spec,Pageable pageable);

}
