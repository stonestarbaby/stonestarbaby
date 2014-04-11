package com.yangjun.baby.activity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.yangjun.baby.R;
import com.yangjun.baby.constants.BabyConstants;
import com.yangjun.baby.entity.Infos;
import com.yangjun.baby.ui.EditTextImage;
import com.yangjun.baby.util.BabyUtils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class MoodNewActivity extends SherlockActivity implements OnItemSelectedListener {
	private Spinner typeSpinner;
	private EditText contentT;
	private TextView tips;
	private AlertDialog loadingDialog;
	private TextView mTitleTextView;
	private int type=0;
	private ArrayAdapter<String> adapter;
	private String[] typeStr=new String[]{"高兴","伤心","生气"};
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(BabyConstants.TYPE_MOOD_NEW==msg.what){
				if(loadingDialog!=null){
					loadingDialog.cancel();
				}
				String res=msg.getData().getString(BabyConstants.BUN_H__MOOD_NEW);
				if(res==""){
					new AlertDialog.Builder(MoodNewActivity.this).setIcon(R.drawable.login_error)
					.setTitle(R.string.net_wrong_title).setMessage(R.string.net_wrong_des)
					.create().show();
					return ;
				}else{
					if(BabyConstants.MSG_FAIL.equals(res)){
						Toast toast=Toast.makeText(MoodNewActivity.this, R.string.msg_noname, Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						
					}else{
						Intent intent=new Intent(MoodNewActivity.this,MoodActivity.class);
						MoodNewActivity.this.finish();
						MoodNewActivity.this.startActivity(intent);
					}
				}
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mood_new);
		initActionBar();
		this.contentT=(EditTextImage)this.findViewById(R.id.mood_new_content);
		this.typeSpinner=(Spinner)this.findViewById(R.id.mood_new_spinner);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typeStr);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.typeSpinner.setAdapter(adapter);
		this.typeSpinner.setOnItemSelectedListener(this);
		this.typeSpinner.setVisibility(View.VISIBLE);
		this.tips=(TextView)this.findViewById(R.id.mood_new_tips);
	}
	private void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_new);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mTitleTextView = (TextView) findViewById(R.id.tv_title);
		mTitleTextView.setText(R.string.nav_fourm);
		ImageView back=(ImageView)this.findViewById(R.id.iv_left_icon);
		back.setOnClickListener(new ImageView.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent localIntent = new Intent(MoodNewActivity.this, ForumActivity.class);
				 localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 MoodNewActivity.this.startActivity(localIntent);
			}
			
		});
		ImageView save=(ImageView)this.findViewById(R.id.iv_right_icon);
		save.setImageResource(R.drawable.save);
		save.setOnClickListener(new ImageView.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String content=contentT.getText().toString();
				Log.i("post", "Content:"+content);
				if("".equals(content)){
					new AlertDialog.Builder(MoodNewActivity.this).setIcon(R.drawable.login_error)
					.setTitle(R.string.post_new_wrong_title).setMessage(R.string.post_new_wrong_des)
					.create().show();
					return ;
				}
				loadingDialog=BabyUtils.getLoadingDialog(MoodNewActivity.this,R.string.post_new_contening);
				loadingDialog.show();
				new Thread(){
					public void run(){
						String[] strArr=new String[]{Infos.USER_ID,type+"",content};
						String res=BabyUtils.moodNew(strArr);
						Message msg=new Message();
						msg.what=BabyConstants.TYPE_MOOD_NEW;
						Bundle bun=new Bundle();
						bun.putString(BabyConstants.BUN_H__MOOD_NEW, res);
						msg.setData(bun);
						handler.sendMessage(msg);
					}
				}.start();
			}
		
		});
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		type=arg2;
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
