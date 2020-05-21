package com.leadtek.nuu.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leadtek.nuu.service.utils.Utils;

import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.spring.web.json.Json;

@Entity
@Table(name = "ROLE_AUTH")
public class RoleAuth extends BasicEntity implements Serializable {

	/**
	 * 單位與權限
	 */
	private static final long serialVersionUID = -3687280680363944552L;

	@Id
	@Column(name = "CODE")
	@ApiModelProperty(value = "規則ID", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer code;

	// 單位名稱
	@Column(name = "AUTH_NAME", length = 150, unique = true)
	private String authName;

	// 單位權限
	@Column(name = "AUTHS", length = 2600)
	private String auths;



	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String[] getAuth() {
		return Utils.stringToArray(auths);
	}

	public void setAuth(String[] auth) {
		this.auths = Utils.arrayToString(auth);
	}

	
	
}
