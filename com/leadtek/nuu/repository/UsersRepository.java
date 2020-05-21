package com.leadtek.nuu.repository;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.entity.Users;


@Repository
public interface UsersRepository extends PagingAndSortingRepository<Users, String> , QuerydslPredicateExecutor<Users> {
	
	public Users findByAccount(String userName);
	

	
	List<Users> findByrole(String rolename);
//	public Users findByEmail(String email);
	
}
