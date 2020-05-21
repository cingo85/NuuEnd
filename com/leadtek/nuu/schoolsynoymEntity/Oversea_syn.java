package com.leadtek.nuu.schoolsynoymEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "_oversea_syn")
@ApiModel(description = "赴海外")
@Entity
public class Oversea_syn extends BasicEntity implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;
	
	@ApiModelProperty(value = "赴海外類別", required = true)
	@Column(name = "overseatype", nullable = false,unique=true)
	public String overseatype;

	@ApiModelProperty(value = "同義詞", required = true)
	@Column(name = "nuuname", nullable = false)
	public String nuuname;

	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOverseatype() {
		return overseatype;
	}

	public void setOverseatype(String overseatype) {
		this.overseatype = overseatype;
	}

	public String getNuuname() {
		return nuuname;
	}

	public void setNuuname(String nuuname) {
		this.nuuname = nuuname;
	}

}
