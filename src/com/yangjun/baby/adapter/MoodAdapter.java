package com.yangjun.baby.adapter;

import com.yangjun.baby.R;
import com.yangjun.baby.entity.Forum;
import com.yangjun.baby.entity.MoodEntity;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MoodAdapter extends BabyBaseAdapter<MoodEntity>{
    public MoodAdapter(Context context){
    	super(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
    	final MoodEntity forum=this.getItem(position);
    	MoodItemViewCache forumCache;
    	if(null==convertView){
    		convertView = mInflater.inflate(R.layout.mood_item, null);
    		forumCache=new MoodItemViewCache(convertView);
    	
    		
    		TextView contentText=forumCache.getContentText();
    		contentText.setText(forum.getContent());
    		
    		TextView timeText=forumCache.getTimeText();
    		timeText.setText(forum.getTime());
    		
    		return convertView;
    	}
		return convertView;
    }
    public class MoodItemViewCache{
    	private View baseView;
    	private TextView conetentText;
    	private TextView timeText;
    	public MoodItemViewCache(View view){
    		this.baseView=view;
    	}
    	public TextView getTimeText(){
    		if(null==this.timeText){
    			this.timeText=(TextView)this.baseView.findViewById(R.id.textViewDate);
    			return this.timeText;
    		}
    		return this.timeText;
    	}
    	public TextView getContentText(){
    		if(null==this.conetentText){
    			this.conetentText=(TextView)this.baseView.findViewById(R.id.textViewContent);
    			return this.conetentText;
    		}
    		return this.conetentText;
    	}
    }
    
}