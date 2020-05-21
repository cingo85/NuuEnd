package com.leadtek.nuu.schoolsynoymEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "_enrolltype_syn")
@ApiModel(description = "入學管道")
@Entity
public class Enrolltype_syn extends BasicEntity implements java.io.Serializable {

	@Id
	@ApiModelProperty(value = "enrolltypeid", required = true)
	@Column(name = "enrolltypeid", nullable = false)
	public String enrolltypeid;

	@ApiModelProperty(value = "enrolltypename", required = true)
	@Column(name = "enrolltypename", nullable = false)
	public String enrolltypename;

	@ApiModelProperty(value = "nuucode")
	@Lob
	@Column(name = "nuucode")
	public String nuucode;

	@ApiModelProperty(value = "nuuname")
	@Lob
	@Column(name = "nuuname")
	public String nuuname;

	

	public String getEnrolltypename() {
		return enrolltypename;
	}

	public void setEnrolltypename(String enrolltypename) {
		this.enrolltypename = enrolltypename;
	}

	public String getEnrolltypeid() {
		return enrolltypeid;
	}

	public void setEnrolltypeid(String enrolltypeid) {
		this.enrolltypeid = enrolltypeid;
	}

	public String getNuucode() {
		return nuucode;
	}

	public void setNuucode(String nuucode) {
		this.nuucode = nuucode;
	}

	public String getNuuname() {
		return nuuname;
	}

	public void setNuuname(String nuuname) {
		this.nuuname = nuuname;
	}
	
	

}
