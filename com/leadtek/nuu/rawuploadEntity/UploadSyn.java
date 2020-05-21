package com.leadtek.nuu.rawuploadEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description= "資料庫表單")
@Table(name = "Uploadsyn")
public class UploadSyn implements java.io.Serializable{
	
	@Id
	@Column(name = "CODE")
	@ApiModelProperty(value = "規則ID", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer code;
	
	@Column(name = "columnuuid")
	@ApiModelProperty(value = "欄位UUID", required = true)
	public String columnuuid;
	
	@Column(name = "optionvalue", unique = true)
	@ApiModelProperty(value = "選項", required = true)
	public String optionvalue;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getColumnuuid() {
		return columnuuid;
	}

	public void setColumnuuid(String columnuuid) {
		this.columnuuid = columnuuid;
	}

	public String getOptionvalue() {
		return optionvalue;
	}

	public void setOptionvalue(String optionvalue) {
		this.optionvalue = optionvalue;
	}

	
	
	
	
}
