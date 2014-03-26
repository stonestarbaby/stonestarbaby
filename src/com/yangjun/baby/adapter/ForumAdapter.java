package com.yangjun.baby.adapter;

import com.yangjun.baby.R;
import com.yangjun.baby.entity.Forum;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ForumAdapter extends BabyBaseAdapter<Forum>{
    public ForumAdapter(Context context){
    	super(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
    	final Forum forum=this.getItem(position);
    	ForumItemViewCache forumCache;
    	if(null==convertView){
    		convertView = mInflater.inflate(R.layout.forum_item, null);
    		forumCache=new ForumItemViewCache(convertView);
    		ImageView headView=forumCache.getHeadImg();
    		headView.setImageResource(R.drawable.img_head_default);
    		
    		TextView titleText=forumCache.getTitleText();
    		titleText.setText(forum.getPostTitle());
    		
    		TextView nicknameText=forumCache.getNicknameText();
    		nicknameText.setText(forum.getNickName());
    		
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
    public class ForumItemViewCache{
    	private View baseView;
    	private ImageView headView;
    	private TextView titleText;
    	private TextView nickNameText;
    	private TextView timeText;
    	private TextView viewText;
    	private TextView replyText;
    	public ForumItemViewCache(View view){
    		this.baseView=view;
    	}
    	public ImageView getHeadImg(){
    		if(null==this.headView){
    			this.headView=(ImageView)this.baseView.findViewById(R.id.headImageView);
    			return this.headView;
    		}
    		return this.headView;
    	}
    	public TextView getNicknameText(){
    		if(null==this.nickNameText){
    			this.nickNameText=(TextView)this.baseView.findViewById(R.id.nicknameText);
    			return this.nickNameText;
    		}
    		return this.nickNameText;
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