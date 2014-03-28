package com.yangjun.baby.fragment;

import com.yangjun.baby.CommonLog;
import com.yangjun.baby.LogFactory;
import com.yangjun.baby.R;
import com.yangjun.baby.entity.Infos;
import com.yangjun.baby.entity.User;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class PersonEditFragment extends CommonFragment{

	private static final CommonLog log = LogFactory.createLog();
	private EditText username,nickname,birthday;
	private ImageView headImg;
	private User user;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		user=Infos.user;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.e("PersonEditFragment onCreateView");
		View view = inflater.inflate(R.layout.person_edit, null);
		if(user!=null){
			username=(EditText)view.findViewById(R.id.person_edit_email);
			username.setText(user.getUsername());
			nickname=(EditText)view.findViewById(R.id.person_edit_nickname);
			nickname.setText(user.getNickname());
			birthday=(EditText)view.findViewById(R.id.person_edit_birthDay);
			birthday.setText(user.getBirthDay());
		}
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
