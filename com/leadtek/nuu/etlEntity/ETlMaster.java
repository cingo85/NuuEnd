package com.leadtek.nuu.etlEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.leadtek.nuu.entity.BasicEntity;
import com.leadtek.nuu.service.utils.Utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "etlmaster")
@ApiModel(description = "etl表單")
@Entity
public class ETlMaster extends BasicEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "自增序號", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ApiModelProperty(value = "表單中文名稱", required = true)
	@Column(name = "tablename")
	private String tablename;

	@ApiModelProperty(value = "表單英文名稱", required = true)
	@Column(name = "tableengname")
	private String tableengname;

	@ApiModelProperty(value = "狀態", required = true)
	@Column(name = "status")
	private Integer status;

	@ApiModelProperty(value = "筆數", required = true)
	@Column(name = "tablecount")
	private Integer tablecount;
	
	@ApiModelProperty(value = "是否啟動倉儲自動清洗", required = true)
	@Column(name = "cleanyn")
	private String cleanyn;

	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "第一次啟動日期", required = true)
	@Column(name = "cleandatefirst")
	private Date cleandatefirst;
	
	@ApiModelProperty(value = "重複類型", required = true)
	@Column(name = "redaytype")
	private String redaytype;

	@ApiModelProperty(value = "重複天數", required = true)
	@Column(name = "reday")
	private Integer reday;

	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "最後啟動時間", required = true)
	@Column(name = "cleandatecal")
	private Date cleandatecal;

	@ApiModelProperty(value = "結束是否提醒倉儲管理", required = true)
	@Column(name = "endsend")
	private String endsend;
	
	@ApiModelProperty(value = "啟動次數", required = true)
	@Column(name = "startcount")
	private Integer startcount;
	
	@Column(name = "AUTH_NAME", length = 150)
	private String authName;
	
	@ApiModelProperty(value = "今日是否有清洗", required = true)
	@Column(name = "todayclean")
	private String todayclean;

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

	public String getTableengname() {
		return tableengname;
	}

	public void setTableengname(String tableengname) {
		this.tableengname = tableengname;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTablecount() {
		return tablecount;
	}

	public void setTablecount(Integer tablecount) {
		this.tablecount = tablecount;
	}

	public Date getCleandatefirst() {
		return cleandatefirst;
	}

	public void setCleandatefirst(Date cleandatefirst) {
		this.cleandatefirst = cleandatefirst;
	}

	public Integer getReday() {
		return reday;
	}

	public void setReday(Integer reday) {
		this.reday = reday;
	}

	public Date getCleandatecal() {
		return cleandatecal;
	}

	public void setCleandatecal(Date cleandatecal) {
		this.cleandatecal = cleandatecal;
	}

	public String getEndsend() {
		return endsend;
	}

	public void setEndsend(String endsend) {
		this.endsend = endsend;
	}

	public Integer getStartcount() {
		return startcount;
	}

	public void setStartcount(Integer startcount) {
		this.startcount = startcount;
	}

	public String getRedaytype() {
		return redaytype;
	}

	public void setRedaytype(String redaytype) {
		this.redaytype = redaytype;
	}

	public String getCleanyn() {
		return cleanyn;
	}

	public void setCleanyn(String cleanyn) {
		this.cleanyn = cleanyn;
	}
	
	public String[] getAuthName() {
		return Utils.stringToArray(authName);
	}

	public void setAuthName(String[] authName) {
		this.authName = Utils.arrayToString(authName);
	}

	public String getTodayclean() {
		return todayclean;
	}

	public void setTodayclean(String todayclean) {
		this.todayclean = todayclean;
	}


}
