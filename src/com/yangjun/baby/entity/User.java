package com.yangjun.baby.entity;

public class User {
	private String id;
	private String username;
	private String nickname;
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
	public String toString(){
		return "id:"+id+" username:"+username+" nickname:"+nickname;
	}
}
