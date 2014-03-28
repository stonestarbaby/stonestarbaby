package com.yangjun.baby.activity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.yangjun.baby.R;
import com.yangjun.baby.fragment.ForumsFragment;
import com.yangjun.baby.fragment.HomeFragment;
import com.yangjun.baby.fragment.PersonEditFragment;
import com.yangjun.baby.fragment.PersonForumFragment;
import com.yangjun.baby.fragment.PersonReplyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.TextView;

public class PersonActivity extends SherlockFragmentActivity {
    private FragmentManager fragmentManager;
    private RadioGroup radioGroup;
    private TextView mTitleTextView;
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person);
        initActionBar();
        fragmentManager =this.getSupportFragmentManager();
        radioGroup = (RadioGroup) findViewById(R.id.tab_person);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Fragment fragment = FragmentFactory.getInstanceByIndex(checkedId);
                transaction.replace(R.id.tab_content, fragment);
                transaction.commit();
            }
        });
    }
    private void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_title);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
	}

}
class FragmentFactory {
    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        Log.i("baby", "R:"+index);
        switch (index) {
            case R.id.tab_person:
                fragment = new PersonEditFragment();
                break;
            case R.id.tab_post:
                fragment = new PersonForumFragment();
                break;
            case R.id.tab_reply:
                fragment = new PersonReplyFragment();
                break;
            case R.id.tab_message:
                fragment = new PersonEditFragment();
                break;
        }
        return fragment;
    }
}
