package com.leadtek.nuu.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "LOGS" ) 
public class Logs implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7713436146484835949L;

	@Id 
	@Column(name = "LOG_SEQ", length = 50, unique = true )
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String logSeq; 
	
	@Column(name = "USERNAME", length = 50) 
	private String userName;
	
	//作業
	@Column(name = "OPERATION", length = 50) 
	private String operation;
	
	//物件class
	@Column(name = "OBJECTNAME", length = 100) 
	private String objectName;
	
	//物件id
	@Column(name = "OBJECTID" , length = 50) 
	private String objectID;
	
	@Column(name = "DEVICE", length = 50) 
	private String device;
	
	@Column(name = "BROWSER", length = 50) 
	private String browser;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTTIME" ) 
	private Date startTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXETIME" ) 
	private Date exeTime;
	
	@Column(name = "URL", length = 250) 
	private String url;
	
	@Column(name = "IPADDRESS", length = 50) 
	private String ipAddress;
	
	@Column(name = "SESSIONID", length = 50) 
	private String session;
	
	@Column(name = "logtype", length = 50) 
	private String logtype;

	public String getLogSeq() {
		return logSeq;
	}

	public void setLogSeq(String logSeq) {
		this.logSeq = logSeq;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getObjectID() {
		return objectID;
	}

	public void setObjectID(String objectID) {
		this.objectID = objectID;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getExeTime() {
		return exeTime;
	}

	public void setExeTime(Date exeTime) {
		this.exeTime = exeTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getLogtype() {
		return logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	
	
	
}
