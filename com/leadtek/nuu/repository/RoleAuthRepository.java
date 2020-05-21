package com.leadtek.nuu.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.entity.RoleAuth;

@Repository
public interface RoleAuthRepository extends PagingAndSortingRepository<RoleAuth, String> {

	public List<RoleAuth> findByCode(Integer code);
	
	public RoleAuth findByAuthName(String authName);
	
}
