package com.leadtek.nuu.schoolsynoymEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "commonuse")
@ApiModel(description = "同義詞系統代碼對照")
@Entity
public class CommonUse implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "自增序號", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@ApiModelProperty(value = "英文表名", required = true)
	@Column(name = "tableengname",nullable = true)
	private String tableengname;
	
	@ApiModelProperty(value = "屬性", required = true)
	@Column(name = "type",nullable=false)
	private String type;
	
	@ApiModelProperty(value = "英文欄位", required = true)
	@Column(name = "engname",nullable = true)
	private String engname;
	
	@ApiModelProperty(value = "中文欄位", required = true)
	@Column(name = "chtname",nullable=false)
	private String chtname;
	
	@ApiModelProperty(value = "欄位順序", required = true)
	@Column(name = "seq")
	private int seq;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableengname() {
		return tableengname;
	}

	public void setTableengname(String tableengname) {
		this.tableengname = tableengname;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEngname() {
		return engname;
	}

	public void setEngname(String engname) {
		this.engname = engname;
	}

	public String getChtname() {
		return chtname;
	}

	public void setChtname(String chtname) {
		this.chtname = chtname;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}





}
