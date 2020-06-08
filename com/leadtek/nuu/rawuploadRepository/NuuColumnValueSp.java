package com.leadtek.nuu.rawuploadRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.jpa.domain.Specification;

import com.leadtek.nuu.rawuploadEntity.NuuColumnValue;

public class NuuColumnValueSp implements Specification<NuuColumnValue> {

	private final List<Map<String, String>> criteria;
	private List<Predicate> filters;

	public NuuColumnValueSp(List<Map<String, String>> criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<NuuColumnValue> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		for(Map<String, String> item : criteria) {
			
			
			
			item.forEach((k,v) ->{
				filters.add(criteriaBuilder.like(criteriaBuilder.upper(root.<String>get("columnvalue").get(k)),
						"%" + v + "%"));
			});
			
			
			
//			Set<String> keys = item.keySet();
//			List<Predicate> filters = new ArrayList<>();
//
//		
//
//				root.get("columnvalue").get("fff");
////				while (keys.hasNext()) {
//					String key = (String) keys.next();
//					String filterValue = null;
//
//					try {
//						filterValue = criteria.get(key).toString();
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//
//					if (filterValue != null) {
//						
//					}
//				}
			}		

	// this is the point : didn't know we could concatenate multiple predicates into
	// one.
	return criteriaBuilder.and(filters.toArray(new Predicate[filters.size()]));
}}
