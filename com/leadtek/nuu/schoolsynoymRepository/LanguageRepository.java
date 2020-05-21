package com.leadtek.nuu.schoolsynoymRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.leadtek.nuu.schoolsynoymEntity.Graeng_syn;

public interface LanguageRepository extends JpaRepository<Graeng_syn, String>,PagingAndSortingRepository<Graeng_syn, String>{

	List<Graeng_syn> findByLangType(String langType);
	
	Graeng_syn findById(Integer id);
	
	@Transactional
	void deleteByid(Integer id);
}
