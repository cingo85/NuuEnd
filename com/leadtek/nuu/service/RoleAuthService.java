package com.leadtek.nuu.service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.leadtek.nuu.entity.RoleAuth;
import com.leadtek.nuu.entity.UnitManage;
import com.leadtek.nuu.entity.Users;
import com.leadtek.nuu.etlEntity.EtlAuth;
import com.leadtek.nuu.etlRepository.EtlAuthRepository;
import com.leadtek.nuu.repository.RoleAuthRepository;
import com.leadtek.nuu.repository.UnitManageRepository;

@Service
public class RoleAuthService {

	@Autowired
	RoleAuthRepository roleAuthRepository;

	@Autowired
	UnitManageRepository unitManageRepository;

	@Autowired
	EtlAuthRepository etlAuthRepository;

	public <S extends RoleAuth> S save(S entity) {

		Set<String> authgroup = new HashSet<String>();

		List<EtlAuth> etlauthitem = etlAuthRepository.findAll();
		for (EtlAuth item : etlauthitem) {
			authgroup.add(item.getColumngroup() + "_" + item.getTablename());
		}

		if (entity.getCode() == null) {
			for (String setitem : authgroup) {
				EtlAuth saveauthitem = new EtlAuth();

				String spl[] = setitem.split("_");

				saveauthitem.setColumngroup(spl[0]);
				saveauthitem.setTablename(spl[1]);
				saveauthitem.setEtlauth("2");
				saveauthitem.setAuthName(entity.getAuthName());

				etlAuthRepository.save(saveauthitem);
			}
		}

		return roleAuthRepository.save(entity);
	}

	public Iterable<RoleAuth> findAll(Sort sort) {
		return roleAuthRepository.findAll(sort);
	}

	public Iterable<UnitManage> findAllUM() {
		return unitManageRepository.findAll();
	}

	public <S extends RoleAuth> Iterable<S> saveAll(Iterable<S> entities) {
		return roleAuthRepository.saveAll(entities);
	}

	public void roleName(Iterator<Users> it) {
		if (it != null) {
			while (it.hasNext()) {
				Users users = it.next();
				if (users.getRole() != null) {

				}
			}
		}
	}

	public Page<RoleAuth> findAll(Pageable pageable) {
		return roleAuthRepository.findAll(pageable);
	}

	public List<RoleAuth> findById(Integer id) {
		return roleAuthRepository.findByCode(id);
	}

	public boolean existsById(String id) {
		return roleAuthRepository.existsById(id);
	}

	public Iterable<RoleAuth> findAll() {

		Iterable<RoleAuth> itme = roleAuthRepository.findAll();
		return itme;
	}

	public Iterable<RoleAuth> findAllById(Iterable<String> ids) {
		return roleAuthRepository.findAllById(ids);
	}

	public long count() {
		return roleAuthRepository.count();
	}

	public void deleteById(String id) {
		roleAuthRepository.deleteById(id);
	}

	public void delete(RoleAuth entity) {
		roleAuthRepository.delete(entity);
	}

	public void deleteAll(Iterable<? extends RoleAuth> entities) {
		roleAuthRepository.deleteAll(entities);
	}

	public void deleteAll() {
		roleAuthRepository.deleteAll();
	}

}
