package com.mango.makingfriend.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class User implements Serializable {
	
	private int hxid;
	private String fxid;
	private Date time;
	private String avatar;//头像url
	private String backgroundImage;
	private String region;
	private String address;
	private int age;
	private String nick;//昵称
	private String password;//密码
	private String tel;//手机号码
	private int power;
	private int sex;
	private String userSig;//用户签名
	private String username;//用户名
	private String education;//学历
	private String hobby;//爱好
	private String job;
	private int wage;//工资
	private String province;//省会
	private String city ;//城市
	private Date bornDate;//出生日期
	private String makingfriendWord;//交友宣言


	private Set<Praise> praise = new HashSet<Praise>();

    @OneToMany(mappedBy="user")
    public Set<Praise> getPraise() {
        return praise;
    }

    public void setPraise(Set<Praise> praise) {
        this.praise = praise;
    }

    @Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	public String getPassword() {
		return password;
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

	public String getUserSig() {
		return userSig;
	}
	public void setUserSig(String userSig) {
		this.userSig = userSig;
	}

	
	public int getPower() {
		return power;
	}

	public String getUsername() {
		return username;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public void setPower(int power) {
		this.power = power;
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
	public String getMakingfriendWord() {
		return makingfriendWord;
	}
	public void setMakingfriendWord(String makingfriendWord) {
		this.makingfriendWord = makingfriendWord;
	}
	
	

	public Date getBornDate() {
		return bornDate;
	}
	public void setBornDate(Date bornDate) {
		this.bornDate = bornDate;
	}
	public String getAddress() {
		return address;
	}
	public int getAge() {
		return age;
	}
	public String getBackgroundImage() {
		return backgroundImage;
	}
	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}




    public User() {
    }
}
