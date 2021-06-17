package com.mango.makingfriend.model;

import java.util.Date;

public class JsonBean<T> extends Message<T> {
	
	private Date time;//当前时间
	private String backgroundImage;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}


}
