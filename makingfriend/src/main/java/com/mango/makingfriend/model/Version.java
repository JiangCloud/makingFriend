package com.mango.makingfriend.model;

import java.util.Date;

public class Version {
	private String version;
	private String downUrl;
	private Date uploeDate;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public Date getUploeDate() {
		return uploeDate;
	}
	public void setUploeDate(Date uploeDate) {
		this.uploeDate = uploeDate;
	}
	public Version(String version, String downUrl) {
		super();
		this.version = version;
		this.downUrl = downUrl;
	}
	public Version() {
		
	}
	
	
	

}
