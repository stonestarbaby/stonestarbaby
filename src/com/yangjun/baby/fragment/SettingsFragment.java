package com.yangjun.baby.fragment;

import com.yangjun.baby.CommonLog;
import com.yangjun.baby.LogFactory;
import com.yangjun.baby.MainActivity;
import com.yangjun.baby.R;
import com.yangjun.baby.entity.Infos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SettingsFragment extends CommonFragment{

	private static final CommonLog log = LogFactory.createLog();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		log.e("BlogFragment onCreate -->" + this);
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.e("BlogFragment onCreateView");
		View view = inflater.inflate(R.layout.settings, null);
		Button loginOutBtn=(Button)view.findViewById(R.id.loginout);
		loginOutBtn.setOnClickListener(new OnClickListener(){  
            @Override  
            public void onClick(View view){  
            	Infos.clear();
            	MainActivity main=(MainActivity)SettingsFragment.this.getActivity();
            	main.switchContent(main.getmControlCenter().getPersonFragmentModel());
            }  
	    });
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		log.e("BlogFragment onActivityCreated");
		
		setupViews();
	}
	private void setupViews(){
	}

	@Override
	public void onDestroy() {
		log.e("BlogFragment onDestroy");
		super.onDestroy();
	}
	
}
