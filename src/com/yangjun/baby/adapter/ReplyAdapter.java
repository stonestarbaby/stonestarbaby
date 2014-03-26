package com.yangjun.baby.adapter;

import com.yangjun.baby.R;
import com.yangjun.baby.entity.ReplyEntity;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ReplyAdapter extends BabyBaseAdapter<ReplyEntity>{
    public ReplyAdapter(Context context){
    	super(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
    	final ReplyEntity reply=this.getItem(position);
    	ReplyItemViewCache forumCache;
    	if(null==convertView){
    		convertView = mInflater.inflate(R.layout.reply_item, null);
    		forumCache=new ReplyItemViewCache(convertView);
    		ImageView headView=forumCache.getHeadImg();
    		headView.setImageResource(R.drawable.img_head_default);
    		
    		TextView contentText=forumCache.getContentText();
    		contentText.setText(reply.getContent());
    		
    		TextView nicknameText=forumCache.getNicknameText();
    		nicknameText.setText(reply.getNickname());
    		
    		TextView timeText=forumCache.getTimeText();
    		timeText.setText(reply.getDate());
    		
    		TextView foolrText=forumCache.getFloorText();
    				foolrText.setText((position+1)+"¥");
    		
    		return convertView;
    	}
		return convertView;
    }
    public class ReplyItemViewCache{
    	private View baseView;
    	private ImageView headView;
    	private TextView contentText;
    	private TextView nickNameText;
    	private TextView timeText;
    	private TextView floorText;
    	public ReplyItemViewCache(View view){
    		this.baseView=view;
    	}
    	public ImageView getHeadImg(){
    		if(null==this.headView){
    			this.headView=(ImageView)this.baseView.findViewById(R.id.headImg);
    			return this.headView;
    		}
    		return this.headView;
    	}
    	public TextView getNicknameText(){
    		if(null==this.nickNameText){
    			this.nickNameText=(TextView)this.baseView.findViewById(R.id.nickname_r);
    			return this.nickNameText;
    		}
    		return this.nickNameText;
    	}
    	public TextView getTimeText(){
    		if(null==this.timeText){
    			this.timeText=(TextView)this.baseView.findViewById(R.id.time_r);
    			return this.timeText;
    		}
    		return this.timeText;
    	}
    	public TextView getContentText(){
    		if(null==this.contentText){
    			this.contentText=(TextView)this.baseView.findViewById(R.id.content_r);
    			return this.contentText;
    		}
    		return this.contentText;
    	}
    	public TextView getFloorText(){
    		if(null==this.floorText){
    			this.floorText=(TextView)this.baseView.findViewById(R.id.floor);
    			return this.floorText;
    		}
    		return this.floorText;
    	}
    }
    
}