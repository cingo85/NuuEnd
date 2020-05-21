package com.leadtek.nuu.etlRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.leadtek.nuu.etlEntity.EtlAuth;

@RepositoryRestResource
public interface EtlAuthRepository extends JpaRepository<EtlAuth, String>,PagingAndSortingRepository<EtlAuth, String>{

	List<EtlAuth> findBytablename(String tablename);

}
