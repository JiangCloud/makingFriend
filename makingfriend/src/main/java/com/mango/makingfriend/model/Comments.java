package com.mango.makingfriend.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
//评论类
@Entity
public class Comments {
	private int cid;
	//private int userId;
	

	private User user;
	//private int mid;
	private String cotent;
	private Date creatTime;

	private Monents monets;
	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
/*	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}*/
	/*public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}*/
	public String getCotent() {
		return cotent;
	}
	public void setCotent(String cotent) {
		this.cotent = cotent;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="mid")
	public Monents getMonets() {
		return monets;
	}
	
	public void setMonets(Monents monets) {
		this.monets = monets;
	}
	@ManyToOne
	@JoinColumn(name="userId")
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
