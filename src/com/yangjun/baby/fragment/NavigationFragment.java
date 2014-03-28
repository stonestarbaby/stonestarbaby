package com.yangjun.baby.fragment;


import com.yangjun.baby.CommonLog;
import com.yangjun.baby.FragmentControlCenter;
import com.yangjun.baby.FragmentModel;
import com.yangjun.baby.LogFactory;
import com.yangjun.baby.MainActivity;
import com.yangjun.baby.R;
import com.yangjun.baby.activity.PersonActivity;
import com.yangjun.baby.entity.Infos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NavigationFragment extends Fragment implements OnCheckedChangeListener{

	private static final CommonLog log = LogFactory.createLog();
	
	private View mView;
	private RadioGroup  m_radioGroup;
	private FragmentControlCenter mControlCenter;
	
	public NavigationFragment(){
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		log.e("NavigationFragment onCreate");
		
		mControlCenter = FragmentControlCenter.getInstance(getActivity());
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		log.e("NavigationFragment onDestroy");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		log.e("NavigationFragment onCreateView");
		
		mView = inflater.inflate(R.layout.navitation_channel_layout, null);
		return mView;	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		log.e("NavigationFragment onActivityCreated");
		
		setupViews();
	}
	
	
	private void setupViews(){
		m_radioGroup = (RadioGroup) mView.findViewById(R.id.nav_radiogroup);
		((RadioButton) m_radioGroup.getChildAt(0)).toggle();
		
		m_radioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		RadioButton account=(RadioButton)mView.findViewById(R.id.nav_person);
		if(Infos.ISLOGIN){
			account.setText("ÒÑµÇÂ¼");
		}else{
			account.setText("Î´µÇÂ¼");
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int id) {
		switch(id){
		case R.id.nav_person:
			this.goPersonFragment();
			break;
		case R.id.nav_home:
			this.goHomeFragment();
			break;
		case R.id.nav_settings:
			this.goSettingsFragment();
			break;
		}
	}
	
	private void goHomeFragment(){
		if (getActivity() == null)
			return;

		FragmentModel fragmentModel = mControlCenter.getHomeFragmentModel();
		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragmentModel);
		}
	}
	private void goPersonFragment(){
		if (getActivity() == null)
			return;

		FragmentModel fragmentModel = mControlCenter.getPersonFragmentModel();
		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			if(!Infos.ISLOGIN){
				ra.switchContent(fragmentModel);
			}else{
				Intent intent=new Intent(ra,PersonActivity.class);
				ra.startActivity(intent);
			}
		}
	}
	private void goSettingsFragment(){
		if (getActivity() == null)
			return;

		FragmentModel fragmentModel = mControlCenter.getSettingsFragmentModel();
		if (getActivity() instanceof MainActivity) {
			MainActivity ra = (MainActivity) getActivity();
			ra.switchContent(fragmentModel);
		}
	}
}
