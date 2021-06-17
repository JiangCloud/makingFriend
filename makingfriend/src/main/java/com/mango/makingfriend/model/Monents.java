package com.mango.makingfriend.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;




/***
 * 朋友圈说说
 * @author cloud
 *
 */
@Entity
public class Monents implements Serializable {
	private int mid;
	private Date publishTime;//发布时间
	private User user;
	private String content;//内容 
	private String location;//地理位置
	private String imageStr;//图片名称
	
    private Set<Praise> praise = new HashSet<Praise>();
    private Set<Comments> comments = new HashSet<Comments>();
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}
	public Date getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getImageStr() {
		return imageStr;
	}
	public void setImageStr(String imageStr) {
		this.imageStr = imageStr;
	}

   @ManyToOne
   @JoinColumn(name="userId")
   public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

   @OneToMany(mappedBy="monets" )
	public Set<Praise> getPraise() {
		return praise;
	}

	public void setPraise(Set<Praise> praise) {
		this.praise = praise;
	}
	@OneToMany(mappedBy="monets")
	public Set<Comments> getComments() {
		return comments;
	}
	 
	public void setComments(Set<Comments> comments) {
		this.comments = comments;
	}

	public Monents() {
		super();

	}
	
	 
	

	
	

}
