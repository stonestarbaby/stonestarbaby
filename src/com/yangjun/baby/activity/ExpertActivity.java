package com.yangjun.baby.activity;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yangjun.baby.MainActivity;
import com.yangjun.baby.R;
import com.yangjun.baby.adapter.ExpertAdapter;
import com.yangjun.baby.entity.Infos;
import com.yangjun.baby.entity.ExpertEntity;
import com.yangjun.baby.fragment.LoginFragment;
import com.yangjun.baby.util.BabyUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class ExpertActivity extends SherlockActivity{
	private ImageView mLeftIcon;
	private TextView mTitleTextView;
	private PullToRefreshListView list;
	private ExpertAdapter adapter;
	private int page=0;
	private boolean isFirstLoading = true;
	private AlertDialog loadingDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.expert);
		initActionBar();
		adapter=new ExpertAdapter(ExpertActivity.this);
		list=(PullToRefreshListView)this.findViewById(R.id.expertList);
		list.setMode(PullToRefreshBase.Mode.BOTH);
		new GetDataTask().execute();
		list.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2<ListView>(){

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> arg0) {
				// TODO Auto-generated method stub
				if(page<0){
					page=0;
				}else{
					page--;
					new GetDataTask().execute();;
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> arg0) {
				// TODO Auto-generated method stub
				page++;
				new GetDataTask().execute();
			}
			
		});
		list.setOnItemClickListener( new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				Log.i("chat", "index:"+index);
				ExpertEntity ent=adapter.getItem(index-1);
				Infos.EXPERT_ID=ent.getExpertId();
				Infos.EXPERT_NICKNAME=ent.getName();
				loadingDialog=BabyUtils.getLoadingDialog(ExpertActivity.this,R.string.newchating);
				loadingDialog.show();
				new NewChat().execute();
			}
			
		});
	}
	private void initActionBar(){
		ActionBar actionBar =this.getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_back);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mLeftIcon = (ImageView) findViewById(R.id.iv_left_icon);
		mLeftIcon.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ExpertActivity.this,MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				ExpertActivity.this.startActivity(intent);
				ExpertActivity.this.finish();
			}
			
		});
		mTitleTextView = (TextView) findViewById(R.id.tv_title);
		mTitleTextView.setText(R.string.main_experts);
	}
	private void handleNewChat(String result){
		if(loadingDialog!=null){
			loadingDialog.cancel();
		}
		if(result.equals("FAIL")){
			new AlertDialog.Builder(ExpertActivity.this).setIcon(R.drawable.login_error)
			.setTitle(R.string.login_wrong_title).setMessage(R.string.login_wrong_des)
			.create().show();
			return ;
		}
		Infos.CHAT_INFO_ID=result;
		ExpertActivity.this.startActivity(new Intent(ExpertActivity.this,WeChatActivity.class));
	}
	private void updateAdapter(String result){
		ObjectMapper mapper=new ObjectMapper();
		try {
			ExpertEntity[] experts=mapper.readValue(result,ExpertEntity[].class);
			List<ExpertEntity> forumsList=Arrays.asList(experts);
			//adapter=new ForumAdapter(ForumActivity.this);
			adapter.clear();
			adapter.setMultitermDataToFooter(forumsList);
			list.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			list.onRefreshComplete();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class GetDataTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			String res=BabyUtils.getMGetResult(BabyUtils.EXPERT_LIST_URL, null);
			return res;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateAdapter(result);
		}
	}
	private class NewChat extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			Map<String,String> map=new HashMap<String,String>();
			map.put("user_id", Infos.USER_ID);
			map.put("expert_id", Infos.EXPERT_ID);
			String res=BabyUtils.getMGetResult(BabyUtils.EXPERT_NEW_CHAT_URL, map);
			return res;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			handleNewChat(result);
		}
	}
}
