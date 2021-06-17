package com.mango.makingfriend.model;

import java.util.Date;

public class AppInfo {
	private String userSig;//用户签名
	private String Identifier;
	private String version;
	private String downUrl;
	
	private int hxid;
	private String fxid;
	private Date time;//注册时间
	private String avatar;//头像url
	private String region; //
	private String address;
	private int age;
	private String nick;//昵称
	private String tel;//手机号码
	private int power;
	private int sex;

	private String username;//用户名
	private String education;//学历
	private String hobby;//爱好
	private String job;
	private int wage;//工资
	private Date bornDate;//出生日期
	private String makingfriendWord;//交友宣言
	
	


	public String getIdentifier() {
		return Identifier;
	}

	public void setIdentifier(String identifier) {
		Identifier = identifier;
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


	public String getVersion() {
		return version;
	}

	public AppInfo(String version, String downUrl) {
		this.version = version;
		this.downUrl = downUrl;
	}
	
	

	public String getUserSig() {
		return userSig;
	}

	public void setUserSig(String userSig) {
		this.userSig = userSig;
	}

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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getWage() {
		return wage;
	}

	public void setWage(int wage) {
		this.wage = wage;
	}

	public Date getBornDate() {
		return bornDate;
	}

	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}

	public String getMakingfriendWord() {
		return makingfriendWord;
	}

	public void setMakingfriendWord(String makingfriendWord) {
		this.makingfriendWord = makingfriendWord;
	}

	public AppInfo() {
		super();
		
	}
	
	

}
