package com.mango.makingfriend.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MonentsVo {
	private int mid;
	private int userId;
	private Date publishTime;//发布时间
	private String fxid;
	private String usernick;//呢称
	private String content;//内容 
	private String location;//地理位置
	private String imageStr;//图片名称
	private String avatar;//头像
	private Set<CommentsVo> comment=new HashSet<CommentsVo>() ;//评论
	private Set<PraiseVo> praises=new HashSet<PraiseVo>();//
	
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
	public String getUsernick() {
		return usernick;
	}
	public void setUsernick(String usernick) {
		this.usernick = usernick;
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
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Set<CommentsVo> getComment() {
		return comment;
	}
	public void setComment(Set<CommentsVo> comment) {
		this.comment = comment;
	}
	public Set<PraiseVo> getPraises() {
		return praises;
	}
	public void setPraises(Set<PraiseVo> praises) {
		this.praises = praises;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFxid() {
		return fxid;
	}
	public void setFxid(String fxid) {
		this.fxid = fxid;
	}
	
	
	
}
