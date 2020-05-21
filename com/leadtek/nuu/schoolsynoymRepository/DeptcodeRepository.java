package com.leadtek.nuu.schoolsynoymRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.S90unit;

public interface DeptcodeRepository extends JpaRepository<S90unit, String>,PagingAndSortingRepository<S90unit, String>{

	@Transactional
	void deleteByuntid(String item);
}
