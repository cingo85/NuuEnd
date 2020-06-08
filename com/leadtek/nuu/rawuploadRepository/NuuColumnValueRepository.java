package com.leadtek.nuu.rawuploadRepository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.leadtek.nuu.rawuploadEntity.NuuColumnValue;

@Repository
public interface NuuColumnValueRepository extends MongoRepository<NuuColumnValue, String> {

	NuuColumnValue findByValueuuid(String valueuuid);

	NuuColumnValue findByColumnpkvalue(String Colunmpkvalue);

	List<NuuColumnValue> findByTableuuid(String uuid);
	
	Page<NuuColumnValue> findByTableuuid(Pageable pageable,String uuid);

	Page<NuuColumnValue> findByTableuuid(Specification<NuuColumnValue> spec, Pageable pageable, String uuid);
	


	void deleteByValueuuid(String uuid);
}
