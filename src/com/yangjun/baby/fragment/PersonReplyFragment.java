package com.yangjun.baby.fragment;

import com.yangjun.baby.CommonLog;
import com.yangjun.baby.LogFactory;
import com.yangjun.baby.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PersonReplyFragment extends CommonFragment{

	private static final CommonLog log = LogFactory.createLog();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.e("PersonEditFragment onCreateView");
		View view = inflater.inflate(R.layout.person_reply, null);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViews();
	}
	private void setupViews(){
		
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}
