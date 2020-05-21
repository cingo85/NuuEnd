package com.leadtek.nuu.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "USERS")
public class Users extends BasicEntity implements Serializable {

	// 用戶
	private static final long serialVersionUID = -925464928270305042L;

	@Id
	@Column(name = "USERS_SEQ", length = 50, unique = true)
	@ApiModelProperty(value = "使用者唯一值", required = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String usersSeq;

	@ApiModelProperty(value = "LDAP帳號", required = true, notes = "唯一值")
	@Column(name = "account", length = 50, unique = true)
	private String account;

	@ApiModelProperty(value = "使用者姓名", required = true)
	@Column(name = "name", length = 50)
	private String name;

	@ApiModelProperty(value = "使用者單位")
	@Column(name = "department", length = 50)
	private String department;

	@ApiModelProperty(value = "使用者適用系統規則", required = true)
	@Column(name = "ROLE", length = 200)
	private String role;

	@ApiModelProperty(value = "使用者信箱")
	@Column(name = "EMAIL", length = 100)
	private String email;// 信箱

	@ApiModelProperty(value = "使用者狀態")
	@Column(name = "STATUS")
	private Integer status;

	public String getUsersSeq() {
		return usersSeq;
	}

	public void setUsersSeq(String usersSeq) {
		this.usersSeq = usersSeq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;// EncryptEngin.decrypt()
	}

	public void setEmail(String email) {
		this.email = email;// EncryptEngin.encrypt()
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
