package com.leadtek.nuu.schoolsynoymEntity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "schoollog")
@ApiModel(description = "同義詞記錄檔")
@Entity
public class SchoolLog implements java.io.Serializable {

	@Id
	@ApiModelProperty(value = "自增序號", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ApiModelProperty(value = "log", required = true)
	@Column(name = "log")
	private String log;
	
	@ApiModelProperty(value = "log", required = true)
	@Column(name = "synoymtype")
	private String synoymtype;

//	@Column(name = "logDetail", columnDefinition = "json")
//	@Convert(attributeName = "data", converter = JsonToMapConverter.class)
//	private Map<String, Object> metaData = new HashMap<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getSynoymtype() {
		return synoymtype;
	}

	public void setSynoymtype(String synoymtype) {
		this.synoymtype = synoymtype;
	}

	
//	public Map<String, Object> getMetaData() {
//		return metaData;
//	}
//
//	public void setMetaData(Map<String, Object> metaData) {
//		this.metaData = metaData;
//	}

}
