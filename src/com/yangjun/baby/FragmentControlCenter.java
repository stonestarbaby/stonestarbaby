package com.yangjun.baby;

import java.util.HashMap;
import java.util.Map;

import com.yangjun.baby.fragment.SettingsFragment;
import com.yangjun.baby.fragment.HomeFragment;
import com.yangjun.baby.fragment.LoginFragment;
import android.content.Context;

public class FragmentControlCenter {

	private static final CommonLog log = LogFactory.createLog();
	
	private static  FragmentControlCenter instance;
	private Context mContext;
	
	private Map<String, FragmentModel> mFragmentModelMaps = new HashMap<String, FragmentModel>();

	private FragmentControlCenter(Context context) {
		mContext = context;
	}
	
	public static synchronized FragmentControlCenter getInstance(Context context) {
		if (instance == null){
			instance  = new FragmentControlCenter(context);
		}
		return instance;
	}

	public FragmentModel getHomeFragmentModel(){
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.HOME_FRAGMENT);
		if (fragmentModel == null){
			fragmentModel = FragmentBuilder.getHomeFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.HOME_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	public FragmentModel getPersonFragmentModel(){
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.PERSON_FRAGMENT);
		if (fragmentModel == null){
			fragmentModel = FragmentBuilder.getPersonFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.PERSON_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	
	public FragmentModel getSettingsFragmentModel(){
		FragmentModel fragmentModel = mFragmentModelMaps.get(FragmentBuilder.SETTINGS_FRAGMENT);
		if (fragmentModel == null){
			fragmentModel = FragmentBuilder.getSettingsFragmentModel();
			mFragmentModelMaps.put(FragmentBuilder.SETTINGS_FRAGMENT, fragmentModel);
		}
		return fragmentModel;
	}
	
	
	public FragmentModel getFragmentModel(String name){
		return mFragmentModelMaps.get(name);
	}

	public void addFragmentModel(String name,FragmentModel fragment){
		mFragmentModelMaps.put(name, fragment);
	}
	

	public static class FragmentBuilder{
		public static final String HOME_FRAGMENT = "HOME_FRAGMENT";
		public static final String PERSON_FRAGMENT = "PERSON_FRAGMENT";
		public static final String SETTINGS_FRAGMENT = "SETTINGS_FRAGMENT";
		public static FragmentModel  getHomeFragmentModel(){
			HomeFragment fragment = new HomeFragment();
			FragmentModel fragmentModel = new FragmentModel(R.string.main_home, fragment);
			return fragmentModel;
		}
		public static FragmentModel  getPersonFragmentModel(){
			LoginFragment fragment = new LoginFragment();
			FragmentModel fragmentModel = new FragmentModel(R.string.login_str,fragment);
			return fragmentModel;
		}
		public static FragmentModel getSettingsFragmentModel(){
			SettingsFragment fragment = new SettingsFragment();
			FragmentModel fragmentModel = new FragmentModel(R.string.main_settings, fragment);
			return fragmentModel;
		}
	}
}
