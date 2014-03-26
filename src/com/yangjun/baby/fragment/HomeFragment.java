package com.yangjun.baby.fragment;

import com.yangjun.baby.CommonLog;
import com.yangjun.baby.LogFactory;
import com.yangjun.baby.R;
import com.yangjun.baby.activity.ExpertActivity;
import com.yangjun.baby.activity.ForumActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends CommonFragment{
	private TextView fourmItem;
	private TextView expertItem;
	private TextView shareItem;
	private static final CommonLog log = LogFactory.createLog();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		log.e("MainFragment onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.e("MainFragment onCreateView");
		View view = inflater.inflate(R.layout.home, null);
		fourmItem=(TextView)view.findViewById(R.id.home_activity_circle);
		fourmItem.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HomeFragment.this.getActivity(),ForumActivity.class);
				HomeFragment.this.getActivity().startActivity(intent);
			}
		});
		expertItem=(TextView)view.findViewById(R.id.home_activity_expert);
		expertItem.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(HomeFragment.this.getActivity(),ExpertActivity.class);
				HomeFragment.this.getActivity().startActivity(intent);
			}
		});
		shareItem=(TextView)view.findViewById(R.id.home_activity_share);
		shareItem.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v){  
                Intent intent=new Intent(Intent.ACTION_SEND);  
                intent.setType("text/plain");  
                intent.putExtra(Intent.EXTRA_SUBJECT, HomeFragment.this.getString(R.string.share));
                intent.putExtra(Intent.EXTRA_TITLE,HomeFragment.this.getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT,HomeFragment.this.getString(R.string.shareContent));  
                startActivity(Intent.createChooser(intent, HomeFragment.this.getString(R.string.shareSel)));  
            }  
		});
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		log.e("MainFragment onActivityCreated");
		
		setupViews();
	}
	
	private void setupViews(){
	        
	}
	@Override
	public void onDestroy() {
		log.e("MainFragment onDestroy");
		super.onDestroy();
	}
}
