package com.yangjun.baby.entity;

public class PersonForum {
	private String postId;
	private String postTime;
	private String viewNum;
	private String replyNum;
	private String postTitle;
	public PersonForum(){
		
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getPostTime() {
		return postTime;
	}
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}
	public String getViewNum() {
		return viewNum;
	}
	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}
	public String getReplyNum() {
		return replyNum;
	}
	public void setReplyNum(String replyNum) {
		this.replyNum = replyNum;
	}
	public String toString(){
		return this.postTitle;
	}
}
