package com.yangjun.baby.adapter;

import com.yangjun.baby.R;
import com.yangjun.baby.entity.ExpertEntity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpertAdapter extends BabyBaseAdapter<ExpertEntity>{
    public ExpertAdapter(Context context){
    	super(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
    	final ExpertEntity expert=this.getItem(position);
    	ExpertItemViewCache forumCache;
    	if(null==convertView){
    		convertView = mInflater.inflate(R.layout.expert_item, null);
    		forumCache=new ExpertItemViewCache(convertView);
    		
    		TextView desText=forumCache.getDescriptionText();
    		desText.setText(expert.getDescription());
    		
    		TextView name=forumCache.getNameText();
    		name.setText(expert.getName());
    		
    		TextView score=forumCache.getScoreText();
    		score.setText(expert.getScoreAve()+"/"+expert.getScoreSum());
    		ImageView image=forumCache.getImage();
    		image.setImageResource(expert.getImageId());
    		return convertView;
    	}
		return convertView;
    }
    public class ExpertItemViewCache{
    	private View baseView;
    	private TextView name;
    	private TextView description;
    	private TextView score;
    	private ImageView image;
    	public ExpertItemViewCache(View view){
    		this.baseView=view;
    	}
    	public TextView getNameText(){
    		if(null==this.name){
    			this.name=(TextView)this.baseView.findViewById(R.id.expertName);
    			return this.name;
    		}
    		return this.name;
    	}
    	public ImageView getImage(){
    		if(null==this.image){
    			this.image=(ImageView)this.baseView.findViewById(R.id.expertImage);
    			return this.image;
    		}
    		return this.image;
    	}
    	public TextView getDescriptionText(){
    		if(null==this.description){
    			this.description=(TextView)this.baseView.findViewById(R.id.expertDes);
    			return this.description;
    		}
    		return this.description;
    	}
    	public TextView getScoreText(){
    		if(null==this.score){
    			this.score=(TextView)this.baseView.findViewById(R.id.score);
    			return this.score;
    		}
    		return this.score;
    	}

    }
    
}