package com.leadtek.nuu.rawuploadEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.leadtek.nuu.entity.BasicEntity;
import com.leadtek.nuu.service.utils.Utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@ApiModel(description = "資料庫表單")
@Table(name = "MainTable")
public class MainTable extends BasicEntity implements java.io.Serializable {

	@Id
	@Column(name = "tableuuid", unique = true)
	@ApiModelProperty(value = "表單UUID", required = true)
	public String tableuuid;

	@ApiModelProperty(value = "表單代碼", required = true, notes = "唯一值")
	@Column(name = "tablecode", length = 50, unique = true)
	public String tablecode;

	@ApiModelProperty(value = "表單名稱", required = true, notes = "唯一值")
	@Column(name = "tablename", length = 50, unique = true)
	public String tablename;

	@ApiModelProperty(value = "管理單位", required = true)
	@Column(name = "authName")
	public String authName;

	@ApiModelProperty(value = "空白檔案路徑", required = true)
	@Column(name = "filepath", length = 50)
	public String filepath;

	@ApiModelProperty(value = "檔案副檔名", required = true)
	@Column(name = "fileextension", length = 50)
	public String fileextension;
	
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value="最後更新日期")
	@Column(name="lastchange")
	public Date lastchange;

	@ApiModelProperty(value = "表單異動通知", required = true)
	@Column(name = "ischange")
	public String ischange;

	@ApiModelProperty(value = "是否開啟管理單位上傳編輯", required = true)
	@Column(name = "startupload")
	public String startupload;

	@ApiModelProperty(value = "表單開始提醒", required = true)
	@Column(name = "startsend")
	public String startsend;

	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "表單開始日期", required = true)
	@Column(name = "startdate")
	public Date startdate;

	@ApiModelProperty(value = "表單結束提醒", required = true)
	@Column(name = "endsend")
	public String endsend;

	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "表單結束日期", required = true)
	@Column(name = "enddate")
	public Date enddate;
	
	@ApiModelProperty(value = "表單總筆數", required = true)
	@Column(name = "totalrow")
	public Integer totalrow;

	public String getTableuuid() {
		return tableuuid;
	}

	public void setTableuuid(String tableuuid) {
		this.tableuuid = tableuuid;
	}

	public String getTablecode() {
		return tablecode;
	}

	public void setTablecode(String tablecode) {
		this.tablecode = tablecode;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFileextension() {
		return fileextension;
	}

	public void setFileextension(String fileextension) {
		this.fileextension = fileextension;
	}

	public String[] getAuthName() {
		return Utils.stringToArray(authName);
	}

	public void setAuthName(String[] authName) {
		this.authName = Utils.arrayToString(authName);
	}

	public String getIschange() {
		return ischange;
	}

	public void setIschange(String ischange) {
		this.ischange = ischange;
	}

	public String getStartupload() {
		return startupload;
	}

	public void setStartupload(String startupload) {
		this.startupload = startupload;
	}

	public String getStartsend() {
		return startsend;
	}

	public void setStartsend(String startsend) {
		this.startsend = startsend;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getEndsend() {
		return endsend;
	}

	public void setEndsend(String endsend) {
		this.endsend = endsend;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Date getLastchange() {
		return lastchange;
	}

	public void setLastchange(Date lastchange) {
		this.lastchange = lastchange;
	}

	public Integer getTotalrow() {
		return totalrow;
	}

	public void setTotalrow(Integer totalrow) {
		this.totalrow = totalrow;
	}
	
	

}
