package com.leadtek.nuu.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.entity.UnitManage;

@Repository
public interface UnitManageRepository extends PagingAndSortingRepository<UnitManage, String>{

}
