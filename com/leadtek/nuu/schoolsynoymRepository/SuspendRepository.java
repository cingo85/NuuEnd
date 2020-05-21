package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.Dropstu_syn;
import com.leadtek.nuu.schoolsynoymEntity.Suspend_syn;

@Repository
public interface SuspendRepository extends JpaRepository<Suspend_syn, String>,PagingAndSortingRepository<Suspend_syn, String> , QuerydslPredicateExecutor<Suspend_syn>{

	
	@Transactional
	void deleteBysuspendremarkid(String item);
	
	List<Suspend_syn> findBysuspendremarkid (String item);
	
}
