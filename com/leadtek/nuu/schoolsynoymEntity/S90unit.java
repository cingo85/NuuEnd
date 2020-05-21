package com.leadtek.nuu.schoolsynoymEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "_s90_unit")
@ApiModel(description = "系所代碼")
@Entity
public class S90unit extends BasicEntity implements java.io.Serializable{
	
	@Id
	@Column(name = "unt_id", nullable = false,unique=true)
	public String untid;
	
	@Column(name = "unt_name")
	public String untname;
	
	@Column(name = "unt_dptid")
	public String untdptid;
	
	@Column(name = "unt_ls")
	public String untls;
	
	@Column(name = "unt_id_old")
	public String untidold;
	
	@Column(name = "unt_lname")
	public String untlname;

	public String getUntid() {
		return untid;
	}

	public void setUntid(String untid) {
		this.untid = untid;
	}

	public String getUntname() {
		return untname;
	}

	public void setUntname(String untname) {
		this.untname = untname;
	}

	public String getUntdptid() {
		return untdptid;
	}

	public void setUntdptid(String untdptid) {
		this.untdptid = untdptid;
	}

	public String getUntls() {
		return untls;
	}

	public void setUntls(String untls) {
		this.untls = untls;
	}

	public String getUntidold() {
		return untidold;
	}

	public void setUntidold(String untidold) {
		this.untidold = untidold;
	}

	public String getUntlname() {
		return untlname;
	}

	public void setUntlname(String untlname) {
		this.untlname = untlname;
	}


	

}
