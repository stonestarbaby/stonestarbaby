package com.yangjun.baby.entity;

public class User {
	private String id;
	private String username;
	private String nickname;
	private String headImg;
	private String birthDay;
	public User(){
		
	}
	public User(String id,String username,String nickname){
		this.id=id;
		this.username=username;
		this.nickname=nickname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String toString(){
		return "id:"+id+" username:"+username+" nickname:"+nickname;
	}
}
