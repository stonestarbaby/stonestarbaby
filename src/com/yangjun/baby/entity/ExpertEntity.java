package com.yangjun.baby.entity;

import com.yangjun.baby.R;

public class ExpertEntity {
	public final static int STATE_OFF=0;
	public final static int STATE_ONLINE=1;
	public final static int STATE_BUSY=2;
	private String expertId;
	private String name;
	private String description;
	private String scoreAve;
	private String scoreSum;
	private String online;
	private String serve;
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public String getServe() {
		return serve;
	}
	public void setServe(String serve) {
		this.serve = serve;
	}
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
	public int getState(){
		if(this.online.equals("0")){
			return STATE_OFF;
		}
		if(this.serve.equals("1")){
			return STATE_BUSY;
		}
		return STATE_ONLINE;
	}
	public int getImageId(){
		int state=this.getState();
		if(state==STATE_OFF){
			return R.drawable.expert_off;
		}
		if(state==STATE_ONLINE){
			return R.drawable.expert_online;
		}
		return R.drawable.expert_busy;
	}
}
