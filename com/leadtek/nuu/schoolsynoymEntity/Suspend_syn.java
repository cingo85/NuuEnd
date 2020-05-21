package com.leadtek.nuu.schoolsynoymEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.leadtek.nuu.entity.BasicEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Table(name = "_suspend_syn")
@ApiModel(description = "休學")
@Entity
public class Suspend_syn extends BasicEntity implements java.io.Serializable {

	@Id
	@ApiModelProperty(value = "休學代碼", required = true)
	@Column(name = "suspendremarkid", nullable = false)
	public String suspendremarkid;

	@ApiModelProperty(value = "休學名稱", required = true)
	@Column(name = "suspendremark", nullable = false)
	public String suspendremark;

	@ApiModelProperty(value = "同義詞代碼", required = true)
	@Column(name = "nuucode")
	public String nuucode;

	@ApiModelProperty(value = "同義詞名稱", required = true)
	@Column(name = "nuuname")
	public String nuuname;

	public String getSuspendremarkid() {
		return suspendremarkid;
	}

	public void setSuspendremarkid(String suspendremarkid) {
		this.suspendremarkid = suspendremarkid;
	}

	public String getSuspendremark() {
		return suspendremark;
	}

	public void setSuspendremark(String suspendremark) {
		this.suspendremark = suspendremark;
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
