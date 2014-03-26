package com.yangjun.baby.entity;

public class ForumDetail {
	private Forum post;
	private ReplyEntity[] replys;
	public ForumDetail(){
		
	}
	public Forum getFourm() {
		return post;
	}
	public void setFourm(Forum fourm) {
		this.post = fourm;
	}
	public ReplyEntity[] getReplys() {
		return replys;
	}
	public void setReplys(ReplyEntity[] replys) {
		this.replys = replys;
	}
}
