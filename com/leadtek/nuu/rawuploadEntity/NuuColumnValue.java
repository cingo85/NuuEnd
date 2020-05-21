package com.leadtek.nuu.rawuploadEntity;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModelProperty;

@Document
public class NuuColumnValue extends BasicEntity implements java.io.Serializable {

	@Id
	public String id;

	@Column(name = "valueuuid", unique = true)
	@ApiModelProperty(value = "欄位值UUID", required = true)
	public String valueuuid;

	@ApiModelProperty(value = "表單UUID", required = true)
	@Column(name = "tableuuid")
	public String tableuuid;

	@ApiModelProperty(value = "表單名稱", required = true)
	@Column(name = "tablename")
	public String tablename;

	@Indexed
	@ApiModelProperty(value = "表單PK", required = true)
	@Column(name = "columnpkvalue", unique = true)
	public String columnpkvalue;

	@ApiModelProperty(value = "值", required = true)
	@Column(name = "columnvalue", unique = true)
	public String columnvalue;

	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValueuuid() {
		return valueuuid;
	}

	public void setValueuuid(String valueuuid) {
		this.valueuuid = valueuuid;
	}

	public String getTableuuid() {
		return tableuuid;
	}

	public void setTableuuid(String tableuuid) {
		this.tableuuid = tableuuid;
	}

	public String getColumnpkvalue() {
		return columnpkvalue;
	}

	public void setColumnpkvalue(String columnpkvalue) {
		this.columnpkvalue = columnpkvalue;
	}

	public String getColumnvalue() {
		return columnvalue;
	}

	public void setColumnvalue(String columnvalue) {
		this.columnvalue = columnvalue;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

}
