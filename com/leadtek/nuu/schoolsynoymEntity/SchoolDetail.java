package com.leadtek.nuu.schoolsynoymEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "schooldetail")
@ApiModel(description = "同義詞子表")
@Entity
public class SchoolDetail extends BasicEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "自增序號", required = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@ApiModelProperty(value = "學校代碼", required = true)
	@Column(name = "graduateschoolcode", nullable = false)
	private String graduateschoolcode;

	@ApiModelProperty(value = "同義詞名稱", required = true)
	@Column(name = "graduateschoolsynonymsnames", unique = true)
	private String graduateschoolsynonymsnames;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getGraduateschoolcode() {
		return graduateschoolcode;
	}

	public void setGraduateschoolcode(String graduateschoolcode) {
		this.graduateschoolcode = graduateschoolcode;
	}

	public String getGraduateschoolsynonymsnames() {
		return graduateschoolsynonymsnames;
	}

	public void setGraduateschoolsynonymsnames(String graduateschoolsynonymsnames) {
		this.graduateschoolsynonymsnames = graduateschoolsynonymsnames;
	}

}
