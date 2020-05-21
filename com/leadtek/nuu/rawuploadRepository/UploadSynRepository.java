package com.leadtek.nuu.rawuploadRepository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.leadtek.nuu.rawuploadEntity.UploadSyn;

public interface UploadSynRepository extends JpaRepository<UploadSyn, String>,PagingAndSortingRepository<UploadSyn, String> , QuerydslPredicateExecutor<UploadSyn>{

	Set<UploadSyn> findByColumnuuid(String uuid);
}
