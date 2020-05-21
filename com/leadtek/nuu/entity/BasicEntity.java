package com.leadtek.nuu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import io.swagger.annotations.ApiModelProperty;


@MappedSuperclass
public class BasicEntity {
	
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "建立日期")
	@Column(name = "CREATION_DATE" ) 
	private Date creationDate;
	 
	@ApiModelProperty(value = "建立人員")
	@Column(name = "CREATION_USER" ) 
	private String creationUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "修改日期")
	@Column(name = "MODIFY_DATE" ) 
	private Date modifyDate;
	
	@ApiModelProperty(value = "修改人員")
	@Column(name = "MODIFY_USER" ) 
	private String modifyUser;
	
	@Version
	@Column(name = "VERSION" )
	private Integer version;
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}


	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
