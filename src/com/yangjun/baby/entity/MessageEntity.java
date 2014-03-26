package com.yangjun.baby.entity;

public class MessageEntity {
	private String fromId;
	private String toId;
	private String content;
	private String lastDated;
	public MessageEntity(){
		
	}
	public String getFromId() {
		return fromId;
	}
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	public String getToId() {
		return toId;
	}
	public void setToId(String toId) {
		this.toId = toId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLastDated() {
		return lastDated;
	}
	public void setLastDated(String lastDated) {
		this.lastDated = lastDated;
	}
}
