package com.leadtek.nuu.rawuploadRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.rawuploadEntity.MainTable;
import com.leadtek.nuu.rawuploadEntity.NuuColumnValue;

@Repository
public interface NuuColumnValueRepository extends MongoRepository<NuuColumnValue, String> {
	
	NuuColumnValue findByValueuuid(String valueuuid);
	
	NuuColumnValue findByColumnpkvalue(String Colunmpkvalue);

	Page<NuuColumnValue> findByTableuuid(String uuid,Pageable pageable);

	Page<NuuColumnValue> findByTableuuid(Specification<NuuColumnValue> spec,Pageable pageable,String uuid);
	
//	Page<NuuColumnValue> findAll(Specification<NuuColumnValue> spec,Pageable pageable);

//	public default Specification<NuuColumnValue> specification(String uuid, Pageable pageable, Sort sort,
//			List<Map<String, Object>> filter) {
//		Specification<NuuColumnValue> sp = new Specification<NuuColumnValue>() {
//
//			@Override
//			public Predicate toPredicate(Root<NuuColumnValue> root, CriteriaQuery<?> query,
//					CriteriaBuilder criteriaBuilder) {
//				List<Predicate> predicate = new ArrayList<>();
//
//				for (Map<String, Object> mpi : filter) {
//					for (Map.Entry<String, Object> entry : mpi.entrySet()) {
//						Path<NuuColumnValue> group = root.get(entry.getKey());
//						predicate.add(group.in(entry.getValue()));
//					}
//				}
//				Predicate[] predicates = new Predicate[predicate.size()];
//				return criteriaBuilder.and(predicate.toArray(predicates));
//			}
//
//		};
//		return sp;
//	}

	void deleteByValueuuid(String uuid);
}
