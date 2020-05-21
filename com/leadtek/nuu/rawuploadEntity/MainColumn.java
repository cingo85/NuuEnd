package com.leadtek.nuu.rawuploadEntity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leadtek.nuu.entity.BasicEntity;
import com.leadtek.nuu.service.utils.Utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "欄位表單")
@Table(name = "MainColumn")
public class MainColumn extends BasicEntity implements java.io.Serializable {

	@Id
	@Column(name = "columnuuid", unique = true)
	@ApiModelProperty(value = "欄位UUID", required = true)
	public String columnuuid;

	@ApiModelProperty(value = "表單UUID", required = true)
	@Column(name = "tableuuid")
	public String tableuuid;

	@ApiModelProperty(value = "表單UUID", required = true)
	@Column(name = "tablename")
	public String tablename;

	@ApiModelProperty(value = "欄位來源", required = true)
	@Column(name = "columntable", nullable = false)
	public String columntable;

	@ApiModelProperty(value = "欄位英文名稱", required = true)
	@Column(name = "columnename", nullable = false)
	public String columnename;

	@ApiModelProperty(value = "欄位中文名稱", required = true)
	@Column(name = "columncname")
	private String columncname;

	@ApiModelProperty(value = "欄位資料型態", required = true)
	@Column(name = "datatype")
	private String datatype;
	
	@ApiModelProperty(value = "欄位資料型態", required = true)
	@Column(name = "dateinputformat")
	private String dateinputformat;

	@ApiModelProperty(value = "備註", required = true)
	@Column(name = "note", nullable = true)
	private String note;

	@ApiModelProperty(value = "資料單位", required = true)
	@Column(name = "pk")
	private String pk;

	@ApiModelProperty(value = "欄位順序", required = true)
	@Column(name = "sort")
	private String sort;

	@ApiModelProperty(value = "加密", required = true)
	@Column(name = "encode")
	private String encode;

	@ApiModelProperty(value = "欄位選項", required = true)
	@Transient
	private Set<Object> option;

	@Column(name = "AUTH_NAME", length = 150, unique = true)
	private String authName;

//	@ManyToOne
//	@JoinColumn(name="main_column_columnuuid",referencedColumnName="tableuuid")
//	private MainTable MainTable;

	public String getColumnuuid() {
		return columnuuid;
	}

	public String getColumntable() {
		return columntable;
	}

	public void setColumntable(String columntable) {
		this.columntable = columntable;
	}

	public void setColumnuuid(String columnuuid) {
		this.columnuuid = columnuuid;
	}

	public String getColumnename() {
		return columnename;
	}

	public void setColumnename(String columnename) {
		this.columnename = columnename;
	}

	public String getColumncname() {
		return columncname;
	}

	public void setColumncname(String columncname) {
		this.columncname = columncname;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getTableuuid() {
		return tableuuid;
	}

	public void setTableuuid(String tableuuid) {
		this.tableuuid = tableuuid;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public Set<Object> getOption() {
		return option;
	}

	public void setOption(Set<Object> option) {
		this.option = option;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String[] getAuth() {
		return Utils.stringToArray(authName);
	}

	public void setAuth(String[] auth) {
		this.authName = Utils.arrayToString(auth);
	}

	public String getDateinputformat() {
		return dateinputformat;
	}

	public void setDateinputformat(String dateinputformat) {
		this.dateinputformat = dateinputformat;
	}
	
	

//	public MainTable getMainTable() {
//		return MainTable;
//	}
//
//	public void setMainTable(MainTable mainTable) {
//		MainTable = mainTable;
//	}

}
