package com.leadtek.nuu.etlEntity;

import java.util.LinkedList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "欄位輸出")
public class ColumnList {
	
	@ApiModelProperty(value = "篩選年度forScore&Term", required = true)
	public String alldatayear;
	
	@ApiModelProperty(value = "篩選年度", required = true)
	public List<String> year= new LinkedList<String>();

	@ApiModelProperty(value = "欄位群組", required = true)
	public String tablename;

	@ApiModelProperty(value = "欄位們", required = true)
	public List<String> columns = new LinkedList<String>();

	

	public String getAlldatayear() {
		return alldatayear;
	}

	public void setAlldatayear(String alldatayear) {
		this.alldatayear = alldatayear;
	}

	public List<String> getYear() {
		return year;
	}

	public void setYear(List<String> year) {
		this.year = year;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	

}
