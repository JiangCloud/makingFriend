package com.mango.makingfriend.model;

public class UserVo {
	private int hxid;
	private String fxid;
	private String tel;//手机号码
	private String userSig;//用户个性签名
	private String avatar;//头像
	private String sex;//性别
	private String province;//省会
	private String city ;//城市
	private String nick;
	public int getHxid() {
		return hxid;
	}
	public void setHxid(int hxid) {
		this.hxid = hxid;
	}
	public String getFxid() {
		return fxid;
	}
	public void setFxid(String fxid) {
		this.fxid = fxid;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUserSig() {
		return userSig;
	}
	public void setUserSig(String userSig) {
		this.userSig = userSig;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	
	

}
