package com.leadtek.nuu.schoolsynoymEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "_dropstu_syn")
@ApiModel(description = "退學")
@Entity
public class Dropstu_syn extends BasicEntity implements java.io.Serializable {

	@Id
	@ApiModelProperty(value = "dropremarkid", required = true)
	@Column(name = "dropremarkid", nullable = false)
	public String dropremarkid;

	@ApiModelProperty(value = "dropremark", required = true)
	@Column(name = "dropremark")
	public String dropremark;

	@ApiModelProperty(value = "nuucode", required = true)
	@Lob
	@Column(name = "nuucode")
	public String nuucode;

	@ApiModelProperty(value = "nuuname", required = true)
	@Lob
	@Column(name = "nuuname")
	public String nuuname;


	public String getDropremarkid() {
		return dropremarkid;
	}

	public void setDropremarkid(String dropremarkid) {
		this.dropremarkid = dropremarkid;
	}



	public String getDropremark() {
		return dropremark;
	}

	public void setDropremark(String dropremark) {
		this.dropremark = dropremark;
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
