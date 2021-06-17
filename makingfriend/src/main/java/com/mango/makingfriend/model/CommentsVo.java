package com.mango.makingfriend.model;

public class CommentsVo {
	
	private int cid;//评论id
	
	private String nickname;
	private int userId;//评论人id
	
	private String cotent;//内容
    private String fxid;//环信id
    
	

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCotent() {
		return cotent;
	}

	public void setCotent(String cotent) {
		this.cotent = cotent;
	}

	public String getFxid() {
		return fxid;
	}

	public void setFxid(String fxid) {
		this.fxid = fxid;
	}


	
	
	

}
