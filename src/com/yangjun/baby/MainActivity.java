package com.yangjun.baby;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.yangjun.baby.entity.Infos;
import com.yangjun.baby.fragment.NavigationFragment;
/**
 * 
 * @author lance
 * @csdn-blog --> http://blog.csdn.net/geniuseoe2012
 * @android-develop-group 锛�98044305
 */
@SuppressLint("NewApi")
public class MainActivity extends SlidingFragmentActivity implements OnClickListener{

	private static final CommonLog log = LogFactory.createLog();
	
	private int mTitle;
	private Fragment mContent;
	
	private ImageView mLeftIcon;
	private TextView mTitleTextView;
	
	private FragmentControlCenter mControlCenter;
	
	public FragmentControlCenter getmControlCenter() {
		return mControlCenter;
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mControlCenter = FragmentControlCenter.getInstance(this);
		Infos.main=this;
		setupViews();
		
		initData();
	}
	
	
	
	private void setupViews(){

		setContentView(R.layout.main_slidemenu_layout);
		
		initActionBar();
		
		initSlideMenu();
	
	}
	
	private void initSlideMenu(){
		FragmentModel fragmentModel = mControlCenter.getHomeFragmentModel();
		switchContent(fragmentModel);

		
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);

		setBehindContentView(R.layout.left_menu_frame);
		sm.setSlidingEnabled(true);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.left_menu_frame, new NavigationFragment())
		.commit();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setBehindScrollScale(0);
		sm.setFadeDegree(0.25f);
	}
	
	private void initActionBar(){
		ActionBar actionBar =this.getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_main);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mLeftIcon = (ImageView) findViewById(R.id.iv_left_icon);
		mLeftIcon.setOnClickListener(this);
		mTitleTextView = (TextView) findViewById(R.id.tv_title);
	}
	
	private void initData(){
		
	}
	public void switchContent(final FragmentModel fragment) {
		mTitle = fragment.mTitle;
		mContent = fragment.mFragment;

		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		Handler h = new Handler();
		h.postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showContent();
			}
		}, 50);
		
		mTitleTextView.setText(mTitle);
	}



	@Override
	public void onClick(View view) {
		switch(view.getId()){
		case R.id.iv_left_icon:
			toggle();
			break;
		}
	}	

}

