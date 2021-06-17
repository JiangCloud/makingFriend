package com.mango.makingfriend.model;

import java.util.Date;
import javax.persistence.*;


//点赞类
@Entity
public class Praise {
	
	private int pid;//id
	private User user;
	private Date creatTime;
	private Monents monets;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}

	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	@ManyToOne( fetch = FetchType.LAZY)
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


	public Praise() {
		super();
	}
}
