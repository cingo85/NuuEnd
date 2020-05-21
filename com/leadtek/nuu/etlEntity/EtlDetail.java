package com.leadtek.nuu.etlEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leadtek.nuu.entity.BasicEntity;
import com.leadtek.nuu.service.utils.Utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "etldetail")
@ApiModel(description = "etl詳細")
@Entity
public class EtlDetail extends BasicEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "自增序號", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ApiModelProperty(value = "表單名稱", required = true)
	@Column(name = "tablename")
	private String tablename;

	@ApiModelProperty(value = "欄位名稱", required = true)
	@Column(name = "columnname")
	private String columnname;

	@ApiModelProperty(value = "欄位英文名稱", required = true)
	@Column(name = "columnengname")
	private String columnengname;

	@ApiModelProperty(value = "欄位是否為PK", required = true)
	@Column(name = "columnpk")
	private String columnpk;

	@ApiModelProperty(value = "欄位群組", required = true)
	@Column(name = "columngroup")
	private String columngroup;

	@ApiModelProperty(value = "欄位加密", required = true)
	@Column(name = "columnencryption")
	private String columnencryption;

	@ApiModelProperty(value = "來源表格", required = true)
	@Column(name = "etltable")
	private String etltable;
	
	@ApiModelProperty(value = "管理單位", required = true)
	@Column(name = "authName")
	public String authName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getColumnname() {
		return columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}

	public String getColumnengname() {
		return columnengname;
	}

	public void setColumnengname(String columnengname) {
		this.columnengname = columnengname;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getColumnpk() {
		return columnpk;
	}

	public void setColumnpk(String columnpk) {
		this.columnpk = columnpk;
	}

	public String getColumngroup() {
		return columngroup;
	}

	public void setColumngroup(String columngroup) {
		this.columngroup = columngroup;
	}

	public String getColumnencryption() {
		return columnencryption;
	}

	public void setColumnencryption(String columnencryption) {
		this.columnencryption = columnencryption;
	}

	public String getEtltable() {
		return etltable;
	}

	public void setEtltable(String etltable) {
		this.etltable = etltable;
	}
	
	public String[] getAuthName() {
		return Utils.stringToArray(authName);
	}

	public void setAuthName(String[] authName) {
		this.authName = Utils.arrayToString(authName);
	}

}
