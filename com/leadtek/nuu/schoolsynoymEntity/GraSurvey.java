package com.leadtek.nuu.schoolsynoymEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "_grasurvey")
@ApiModel(description = "畢業生同義詞")
@Entity
public class GraSurvey extends BasicEntity implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(value = "問卷答題代號", required = true)
	@Column(name = "graduateschoolcode", nullable = false,unique=true)
	private String graduateschoolcode;

	@ApiModelProperty(value = "畢業滿年度", required = true)
	@Column(name = "yqn", nullable = false)
	private String yqn;

	@ApiModelProperty(value = "畢業學年滿年度", required = true)
	@Column(name = "qn", nullable = false)
	private String qn;

	@ApiModelProperty(value = "問卷題號", required = true)
	@Column(name = "questionid", nullable = false)
	private String questionid;

	@ApiModelProperty(value = "問卷答題選項", required = true)
	@Column(name = "sequence", nullable = false)
	private String sequence;

	@ApiModelProperty(value = "問卷答題選項描述", required = true)
	@Column(name = "description", nullable = false)
	private String description;

	@ApiModelProperty(value = "問卷答題代碼轉換", required = true)
	@Column(name = "description2", nullable = false)
	private String description2;

	public String getYqn() {
		return yqn;
	}

	public void setYqn(String yqn) {
		this.yqn = yqn;
	}

	public String getQn() {
		return qn;
	}

	public void setQn(String qn) {
		this.qn = qn;
	}

	public String getQuestionid() {
		return questionid;
	}

	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	public String getGraduateschoolcode() {
		return graduateschoolcode;
	}

	public void setGraduateschoolcode(String graduateschoolcode) {
		this.graduateschoolcode = graduateschoolcode;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription2() {
		return description2;
	}

	public void setDescription2(String description2) {
		this.description2 = description2;
	}

}
