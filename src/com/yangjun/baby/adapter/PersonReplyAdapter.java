package com.yangjun.baby.adapter;

import com.yangjun.baby.R;
import com.yangjun.baby.entity.PersonReplyEntity;
import com.yangjun.baby.entity.ReplyEntity;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PersonReplyAdapter extends BabyBaseAdapter<PersonReplyEntity>{
    public PersonReplyAdapter(Context context){
    	super(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
    	final PersonReplyEntity reply=this.getItem(position);
    	PersonReplyItemViewCache forumCache;
    	if(null==convertView){
    		convertView = mInflater.inflate(R.layout.person_reply_item, null);
    		forumCache=new PersonReplyItemViewCache(convertView);
    		ImageView headView=forumCache.getHeadImg();
    		headView.setImageResource(R.drawable.img_head_default);
    		

    		TextView contentText=forumCache.getContentText();
    		contentText.setText(Html.fromHtml("<b>ʱ�䣺"+reply.getDate()+"</b>"+"<div>"+reply.getContent()+"</div>"));
    		
    		TextView nicknameText=forumCache.getNicknameText();
    		nicknameText.setText(reply.getNickname());
    		
    		TextView timeText=forumCache.getTimeText();
    		timeText.setText(reply.getDate());
    		
    		TextView titleText=forumCache.getTitleText();
    				titleText.setText(reply.getPostTitle());
    		
    		return convertView;
    	}
		return convertView;
    }
    public class PersonReplyItemViewCache{
    	private View baseView;
    	private ImageView headView;
    	private TextView contentText;
    	private TextView nickNameText;
    	private TextView timeText;
    	private TextView titleText;
    	public PersonReplyItemViewCache(View view){
    		this.baseView=view;
    	}
    	public ImageView getHeadImg(){
    		if(null==this.headView){
    			this.headView=(ImageView)this.baseView.findViewById(R.id.person_reply_headImg);
    			return this.headView;
    		}
    		return this.headView;
    	}
    	public TextView getNicknameText(){
    		if(null==this.nickNameText){
    			this.nickNameText=(TextView)this.baseView.findViewById(R.id.person_reply_nickname);
    			return this.nickNameText;
    		}
    		return this.nickNameText;
    	}
    	public TextView getTimeText(){
    		if(null==this.timeText){
    			this.timeText=(TextView)this.baseView.findViewById(R.id.person_reply_postTime);
    			return this.timeText;
    		}
    		return this.timeText;
    	}
    	public TextView getContentText(){
    		if(null==this.contentText){
    			this.contentText=(TextView)this.baseView.findViewById(R.id.person_reply_content);
    			return this.contentText;
    		}
    		return this.contentText;
    	}
    	public TextView getTitleText(){
    		if(null==this.titleText){
    			this.titleText=(TextView)this.baseView.findViewById(R.id.person_reply_postTitle);
    			return this.titleText;
    		}
    		return this.titleText;
    	}
    }
    
}