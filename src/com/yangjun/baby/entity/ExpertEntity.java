package com.yangjun.baby.entity;

public class ExpertEntity {
	private String expertId;
	private String name;
	private String description;
	private String scoreAve;
	private String scoreSum;
	public ExpertEntity(){
		
	}
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getScoreAve() {
		return scoreAve;
	}
	public void setScoreAve(String scoreAve) {
		this.scoreAve = scoreAve;
	}
	public String getScoreSum() {
		return scoreSum;
	}
	public void setScoreSum(String scoreSum) {
		this.scoreSum = scoreSum;
	}
}
