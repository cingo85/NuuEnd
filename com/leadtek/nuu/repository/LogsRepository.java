package com.leadtek.nuu.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.entity.Logs;



@Repository
public interface LogsRepository extends PagingAndSortingRepository<Logs, String> {

}
