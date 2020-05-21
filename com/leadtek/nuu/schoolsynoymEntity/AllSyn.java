package com.leadtek.nuu.schoolsynoymEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "Allsyn")
@ApiModel(description = "所有同義詞")
@Entity
public class AllSyn implements java.io.Serializable {

	@Id
	@ApiModelProperty(value = "表單ID")
	@Column(name = "id")
	private String id;

	@ApiModelProperty(value = "表單名稱")
	@Column(name = "tablename")
	private String tablename;

	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "表單最後修改日期")
	@Column(name = "updatedate")
	private Date updatedate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public Date getUpdatedate() {
		return updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}

}
