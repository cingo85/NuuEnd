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

@Table(name = "_transschool_syn")
@ApiModel(description = "交換校系")
@Entity
public class Transschool_syn extends BasicEntity implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;

	@ApiModelProperty(value = "ID", required = true)
	@Column(name = "exchangedepteduid", nullable = false)
	public String exchangedepteduid;

	@ApiModelProperty(value = "exchangeSchool", required = true)
	@Column(name = "exchangeschool", nullable = false)
	public String exchangeschool;

	@ApiModelProperty(value = "exchangeCollege", required = true)
	@Column(name = "exchangecollege", nullable = false)
	public String exchangecollege;

	@ApiModelProperty(value = "exchangeDept", required = true)
	@Column(name = "exchangedept", nullable = false)
	public String exchangedept;

	@ApiModelProperty(value = "exchangeType", required = true)
	@Column(name = "exchangetype", nullable = false)
	public String exchangetype;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExchangedepteduid() {
		return exchangedepteduid;
	}

	public void setExchangedepteduid(String exchangedepteduid) {
		this.exchangedepteduid = exchangedepteduid;
	}

	public String getExchangeschool() {
		return exchangeschool;
	}

	public void setExchangeschool(String exchangeschool) {
		this.exchangeschool = exchangeschool;
	}

	public String getExchangecollege() {
		return exchangecollege;
	}

	public void setExchangecollege(String exchangecollege) {
		this.exchangecollege = exchangecollege;
	}

	public String getExchangedept() {
		return exchangedept;
	}

	public void setExchangedept(String exchangedept) {
		this.exchangedept = exchangedept;
	}

	public String getExchangetype() {
		return exchangetype;
	}

	public void setExchangetype(String exchangetype) {
		this.exchangetype = exchangetype;
	}

}
