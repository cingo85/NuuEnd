package com.leadtek.nuu.schoolsynoymEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModel;

@Table(name = "_graeng_syn")
@ApiModel(description = "語言能力")
@Entity
public class Graeng_syn extends BasicEntity implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;

	@Column(name = "langType", nullable = false,unique=true)
	public String langType;

	@Column(name = "nuuname")
	public String nuuname;

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLangType() {
		return langType;
	}

	public void setLangType(String langType) {
		this.langType = langType;
	}

	public String getNuuname() {
		return nuuname;
	}

	public void setNuuname(String nuuname) {
		this.nuuname = nuuname;
	}

	

}
