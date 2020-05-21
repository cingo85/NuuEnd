package com.leadtek.nuu.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.leadtek.nuu.entity.Logs;
import com.leadtek.nuu.repository.LogsRepository;



@Service
public class LogsService {
	
	@Autowired
	LogsRepository logsRepository;

	public <S extends Logs> S save(S entity) {
		return logsRepository.save(entity);
	}

	public Iterable<Logs> findAll(Sort sort) {
		return logsRepository.findAll(sort);
	}
	
	public Page<Logs> findAll(Integer pageno, Integer pagesize, List<Map<String, String>> sort){
		
		Pageable pageable = null;
//		for (Map<String, String> item : sort) {
			pageable = pageable(pageno, pagesize, sort);
//		}

		Page<Logs> item = logsRepository.findAll(pageable);
		
		return item;
		
	}

	public <S extends Logs> Iterable<S> saveAll(Iterable<S> entities) {
		return logsRepository.saveAll(entities);
	}

	public Page<Logs> findAll(Pageable pageable) {
		return logsRepository.findAll(pageable);
	}

	public Optional<Logs> findById(String id) {
		return logsRepository.findById(id);
	}

	public boolean existsById(String id) {
		return logsRepository.existsById(id);
	}

	public Iterable<Logs> findAll() {
		return logsRepository.findAll();
	}

	public Iterable<Logs> findAllById(Iterable<String> ids) {
		return logsRepository.findAllById(ids);
	}

	public long count() {
		return logsRepository.count();
	}

	public void deleteById(String id) {
		logsRepository.deleteById(id);
	}

	public void delete(Logs entity) {
		logsRepository.delete(entity);
	}

	public void deleteAll(Iterable<? extends Logs> entities) {
		logsRepository.deleteAll(entities);
	}

	public void deleteAll() {
		logsRepository.deleteAll();
	}
	
	public Pageable pageable(Integer pageno, Integer pagesize, List<Map<String, String>> item) {
		Pageable pageable = null;
		
		String ordervalue = "";
		String namevalue = "";
		
		for(Map<String, String> obj:item) {
			ordervalue = obj.get("order");
			namevalue = obj.get("name");
		}
		
		
		if (item != null) {
			if ("asc".equals(ordervalue)) {
				Sort sortitem = new Sort(Direction.ASC, namevalue);
				pageable = PageRequest.of(pageno, pagesize, sortitem);
			} else if ("desc".equals(ordervalue)) {
				Sort sortitem = new Sort(Direction.DESC, namevalue);
				pageable = PageRequest.of(pageno, pagesize, sortitem);
			}
		} else {
			pageable = PageRequest.of(pageno, pagesize);
		}

		return pageable;
	}
	
	
	
}
