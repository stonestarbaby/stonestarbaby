package com.yangjun.baby.adapter;

import com.yangjun.baby.R;
import com.yangjun.baby.entity.Forum;
import com.yangjun.baby.entity.PersonForum;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PersonForumAdapter extends BabyBaseAdapter<PersonForum>{
    public PersonForumAdapter(Context context){
    	super(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
    	final PersonForum forum=this.getItem(position);
    	PersonForumItemViewCache forumCache;
    	if(null==convertView){
    		convertView = mInflater.inflate(R.layout.person_forum_item, null);
    		forumCache=new PersonForumItemViewCache(convertView);		
    		TextView titleText=forumCache.getTitleText();
    		titleText.setText(forum.getPostTitle());
    		TextView timeText=forumCache.getTimeText();
    		timeText.setText(forum.getPostTime());
    		
    		TextView viewText=forumCache.getViewText();
    		viewText.setText(forum.getViewNum());
    		TextView replyText=forumCache.getReplyText();
    		replyText.setText(forum.getReplyNum());
    		return convertView;
    	}
		return convertView;
    }
    public class PersonForumItemViewCache{
    	private View baseView;
    	private TextView titleText;
    	private TextView timeText;
    	private TextView viewText;
    	private TextView replyText;
    	public PersonForumItemViewCache(View view){
    		this.baseView=view;
    	}
    	public TextView getTimeText(){
    		if(null==this.timeText){
    			this.timeText=(TextView)this.baseView.findViewById(R.id.timeText);
    			return this.timeText;
    		}
    		return this.timeText;
    	}
    	public TextView getTitleText(){
    		if(null==this.titleText){
    			this.titleText=(TextView)this.baseView.findViewById(R.id.titleText);
    			return this.titleText;
    		}
    		return this.titleText;
    	}
    	public TextView getViewText(){
    		if(null==this.viewText){
    			this.viewText=(TextView)this.baseView.findViewById(R.id.viewText);
    			return this.viewText;
    		}
    		return this.viewText;
    	}
    	public TextView getReplyText(){
    		if(null==this.replyText){
    			this.replyText=(TextView)this.baseView.findViewById(R.id.replyText);
    			return this.replyText;
    		}
    		return this.replyText;
    	}
    }
    
}