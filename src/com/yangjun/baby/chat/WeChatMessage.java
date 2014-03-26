package com.yangjun.baby.chat;

import java.util.ArrayList;
import java.util.List;

import com.yangjun.baby.entity.MessageEntity;
import com.yangjun.baby.entity.ChatInfo;

public class WeChatMessage{  
	//定义3种布局类型  
    public static final int MessageType_Time=0;  
    public static final int MessageType_From=1;  
    public static final int MessageType_To=2;   
    public static String LAST_DATED="2014-03-20/18:37:50";
    public WeChatMessage(int Type,String Content){  
        this.mType=Type;  
        this.mContent=Content;  
    }  
    //消息类型  
    private int mType;  
    //消息内容  
    private String mContent;  
    //获取类型  
    public int getType() {  
    	return mType;  
    }  
    //设置类型  
    public void setType(int mType) {  
    	this.mType = mType;  
    }  
    //获取内容  
    public String getContent() {  
    	return mContent;  
    }  
    //设置内容  
    public void setContent(String mContent) {  
    	this.mContent = mContent;  
    }
    public static List<WeChatMessage> convertMsg(MessageEntity[] msgs){
    	List<WeChatMessage> res=new ArrayList<WeChatMessage>();
    	if(msgs==null||msgs.length==0){
    		return res;
    	}
    	for(int i=0;i<msgs.length;i++){
    		MessageEntity ent=msgs[i];
    		if(ent.getFromId().equals(ChatInfo.USER_ID)){
    			WeChatMessage wcm=new WeChatMessage(WeChatMessage.MessageType_To,ent.getContent());
    			res.add(wcm);
    		}else{
    			WeChatMessage wcm=new WeChatMessage(WeChatMessage.MessageType_From,ent.getContent());
    			res.add(wcm);
    		}
    		if(i==msgs.length-1){
    			LAST_DATED=ent.getLastDated();
    		}
    	}
		return res;	
    }
}  