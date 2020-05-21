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

@Table(name = "_licensetype_syn")
@ApiModel(description = "證照")
@Entity
public class licensetype_syn extends BasicEntity implements java.io.Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Integer id;
	
	
	@ApiModelProperty(value = "licenseid", required = true)
	@Column(name = "licenseid", nullable = false,unique = true)
	public String licenseid;

	@ApiModelProperty(value = "licenselevel", required = true)
	@Column(name = "licenselevel", nullable = false)
	public String licenselevel;

	@ApiModelProperty(value = "licensename", required = true)
	@Column(name = "licensename", nullable = false)
	public String licensename;

	@ApiModelProperty(value = "licensehost", required = true)
	@Column(name = "licensehost", nullable = false)
	public String licensehost;

	@ApiModelProperty(value = "licenserank", required = true)
	@Column(name = "licenserank")
	public String licenserank;

	public String getLicenseid() {
		return licenseid;
	}

	public void setLicenseid(String licenseid) {
		this.licenseid = licenseid;
	}

	public String getLicenselevel() {
		return licenselevel;
	}

	public void setLicenselevel(String licenselevel) {
		this.licenselevel = licenselevel;
	}

	public String getLicensename() {
		return licensename;
	}

	public void setLicensename(String licensename) {
		this.licensename = licensename;
	}

	public String getLicensehost() {
		return licensehost;
	}

	public void setLicensehost(String licensehost) {
		this.licensehost = licensehost;
	}

	public String getLicenserank() {
		return licenserank;
	}

	public void setLicenserank(String licenserank) {
		this.licenserank = licenserank;
	}

}
