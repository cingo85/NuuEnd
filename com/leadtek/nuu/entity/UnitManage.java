package com.leadtek.nuu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "UnitManage")
public class UnitManage implements Serializable {

	/**
	 * 權限列表
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "roleid")
	@ApiModelProperty(value = "規則ID", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleid;

	@Column(name = "mainmanage")
	@ApiModelProperty(value = "主項", required = true)
	private String mainmanage;

	@Column(name = "submanage")
	@ApiModelProperty(value = "子項", required = true)
	private String submanage;

	

	

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getMainmanage() {
		return mainmanage;
	}

	public void setMainmanage(String mainmanage) {
		this.mainmanage = mainmanage;
	}

	public String getSubmanage() {
		return submanage;
	}

	public void setSubmanage(String submanage) {
		this.submanage = submanage;
	}
	
	

}
