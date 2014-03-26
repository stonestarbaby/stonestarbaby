package com.yangjun.baby.activity;

import java.util.ArrayList;

import com.yangjun.baby.R;
import com.yangjun.baby.adapter.WhatsNewPagerAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public class WhatsNewActivity extends Activity {
	private ViewPager mViewPager;
	private ImageView mPage0;
	private ImageView mPage1;
	private ImageView mPage2;
	private ImageView mPage3;
	private ImageView mPage4;
	private int curIndex=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.whatsnew);
		this.mViewPager=(ViewPager)this.findViewById(R.id.whatsnew_viewpager);
		this.mViewPager.setOnPageChangeListener(new WhatsNewOnPageChangeListener());
		
		this.mPage0=(ImageView)this.findViewById(R.id.page_0);
		this.mPage1=(ImageView)this.findViewById(R.id.page_1);
		this.mPage2=(ImageView)this.findViewById(R.id.page_2);
		this.mPage3=(ImageView)this.findViewById(R.id.page_3);
		this.mPage4=(ImageView)this.findViewById(R.id.page_4);
		LayoutInflater inflater=LayoutInflater.from(this);
		View view0=inflater.inflate(R.layout.whatnew0, null);
		View view1=inflater.inflate(R.layout.whatnew1, null);
		View view2=inflater.inflate(R.layout.whatnew2, null);
		View view3=inflater.inflate(R.layout.whatnew3, null);
		View view4=inflater.inflate(R.layout.whatnew4, null);
		final ArrayList<View> views=new ArrayList<View>();
		views.add(view0);
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		this.mViewPager.setAdapter(new WhatsNewPagerAdapter(views));
	}
	public class WhatsNewOnPageChangeListener implements OnPageChangeListener {  
        @Override  
        public void onPageSelected(int arg0) {  
            switch (arg0) {  
            case 0:               
                mPage0.setImageDrawable(getResources().getDrawable(R.drawable.dot_cur));  
                mPage1.setImageDrawable(getResources().getDrawable(R.drawable.dot));  
                break;  
            case 1:  
                mPage1.setImageDrawable(getResources().getDrawable(R.drawable.dot_cur));  
                mPage0.setImageDrawable(getResources().getDrawable(R.drawable.dot));  
                mPage2.setImageDrawable(getResources().getDrawable(R.drawable.dot));  
                break;  
            case 2:  
                mPage2.setImageDrawable(getResources().getDrawable(R.drawable.dot_cur));  
                mPage1.setImageDrawable(getResources().getDrawable(R.drawable.dot));  
                mPage3.setImageDrawable(getResources().getDrawable(R.drawable.dot));  
                break;  
            case 3:  
                mPage3.setImageDrawable(getResources().getDrawable(R.drawable.dot_cur));  
                mPage4.setImageDrawable(getResources().getDrawable(R.drawable.dot));  
                mPage2.setImageDrawable(getResources().getDrawable(R.drawable.dot));  
                break;  
            case 4:  
                mPage4.setImageDrawable(getResources().getDrawable(R.drawable.dot_cur));  
                mPage3.setImageDrawable(getResources().getDrawable(R.drawable.dot));  
                break;  
           
            }  
            curIndex = arg0;  
            //animation.setFillAfter(true);// True:图片停在动画结束位置  
            //animation.setDuration(300);  
            //mPageImg.startAnimation(animation);  
        }  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
        }  
  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
        }  
    }  
}
