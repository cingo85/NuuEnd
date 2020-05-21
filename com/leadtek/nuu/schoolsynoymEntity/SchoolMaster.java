package com.leadtek.nuu.schoolsynoymEntity;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "schoolmaster")
@ApiModel(description = "同義詞主表")
@Entity
public class SchoolMaster extends BasicEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "學校代碼", required = true)
	@Column(name = "graduateschoolcode")
	private String graduateschoolcode;

	@ApiModelProperty(value = "學校名稱", required = true)
	@Column(name = "graduateschoolname")
	private String graduateschoolname;

	@ApiModelProperty(value = "體系", required = true)
	@Column(name = "graduateschoolsystem")
	private String graduateschoolsystem;

	@ApiModelProperty(value = "公私立", required = true)
	@Column(name = "graduateschoolpublicprivate")
	private String graduateschoolpublicprivate;

	@ApiModelProperty(value = "層級", required = true)
	@Column(name = "graduateschoollevel")
	private String graduateschoollevel;

	@ApiModelProperty(value = "是否為都會型高中", required = true)
	@Column(name = "graduateschoolcitymarked")
	private String graduateschoolcitymarked;

	@ApiModelProperty(value = "地區", required = true)
	@Column(name = "graduateschoolregion")
	private String graduateschoolregion;

	@ApiModelProperty(value = "縣市別", required = true)
	@Column(name = "graduateschoolcity")
	private String graduateschoolcity;

	@ApiModelProperty(value = "鄉鎮區", required = true)
	@Column(name = "graduateschooldistrict")
	private String graduateschooldistrict;

	@ApiModelProperty(value = "地址", required = true)
	@Column(name = "graduateschooladdress")
	private String graduateschooladdress;

	@ApiModelProperty(value = "代碼異動紀錄", required = true)
	@Column(name = "graduateschooltranscode")
	private String graduateschooltranscode;

	@ApiModelProperty(value = "lat", required = true)
	@Column(name = "lat")
	private String lat;

	@ApiModelProperty(value = "lng", required = true)
	@Column(name = "lng")
	private String lng;

	@ApiModelProperty(value = "flag", required = true)
	@Column(name = "flag")
	private String flag;

	// 判定是否為新增或修改
	@ApiModelProperty(value = "flag", required = true)
	@Transient
	public String mergeflag;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getGraduateschoolcode() {
		return graduateschoolcode;
	}

	public void setGraduateschoolcode(String graduateschoolcode) {
		this.graduateschoolcode = graduateschoolcode;
	}

	public String getGraduateschoolname() {
		return graduateschoolname;
	}

	public void setGraduateschoolname(String graduateschoolname) {
		this.graduateschoolname = graduateschoolname;
	}

	public String getGraduateschoolsystem() {
		return graduateschoolsystem;
	}

	public void setGraduateschoolsystem(String graduateschoolsystem) {
		this.graduateschoolsystem = graduateschoolsystem;
	}

	public String getGraduateschoolpublicprivate() {
		return graduateschoolpublicprivate;
	}

	public void setGraduateschoolpublicprivate(String graduateschoolpublicprivate) {
		this.graduateschoolpublicprivate = graduateschoolpublicprivate;
	}

	public String getGraduateschoollevel() {
		return graduateschoollevel;
	}

	public void setGraduateschoollevel(String graduateschoollevel) {
		this.graduateschoollevel = graduateschoollevel;
	}

	public String getGraduateschoolcitymarked() {
		return graduateschoolcitymarked;
	}

	public void setGraduateschoolcitymarked(String graduateschoolcitymarked) {
		this.graduateschoolcitymarked = graduateschoolcitymarked;
	}

	public String getGraduateschoolregion() {
		return graduateschoolregion;
	}

	public void setGraduateschoolregion(String graduateschoolregion) {
		this.graduateschoolregion = graduateschoolregion;
	}

	public String getGraduateschoolcity() {
		return graduateschoolcity;
	}

	public void setGraduateschoolcity(String graduateschoolcity) {
		this.graduateschoolcity = graduateschoolcity;
	}

	public String getGraduateschooldistrict() {
		return graduateschooldistrict;
	}

	public void setGraduateschooldistrict(String graduateschooldistrict) {
		this.graduateschooldistrict = graduateschooldistrict;
	}

	public String getGraduateschooladdress() {
		return graduateschooladdress;
	}

	public void setGraduateschooladdress(String graduateschooladdress) {
		this.graduateschooladdress = graduateschooladdress;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getGraduateschooltranscode() {
		return graduateschooltranscode;
	}

	public void setGraduateschooltranscode(String graduateschooltranscode) {
		this.graduateschooltranscode = graduateschooltranscode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMergeflag() {
		return mergeflag;
	}

	public void setMergeflag(String mergeflag) {
		this.mergeflag = mergeflag;
	}

}
