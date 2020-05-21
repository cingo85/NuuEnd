package com.leadtek.nuu.etlEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "etlauth")
@ApiModel(description = "etl權限")
@Entity
public class EtlAuth extends BasicEntity implements java.io.Serializable {

	@Id
	@ApiModelProperty(value = "自增序號", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ApiModelProperty(value = "表單名稱")
	@Column(name = "tablename")
	private String tablename;

	// 單位名稱
	@Column(name = "AUTH_NAME", length = 150)
	private String authName;
	
	@Column(name = "columngroup", length = 2600)
	private String columngroup;
	
	// 單位權限
	@Column(name = "etlauth", length = 2600)
	private String etlauth;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getColumngroup() {
		return columngroup;
	}

	public void setColumngroup(String columngroup) {
		this.columngroup = columngroup;
	}

	public String getEtlauth() {
		return etlauth;
	}

	public void setEtlauth(String etlauth) {
		this.etlauth = etlauth;
	}
	
	

}
