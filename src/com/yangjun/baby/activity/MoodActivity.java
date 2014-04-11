package com.yangjun.baby.activity;

import java.io.IOException;
import java.util.ArrayList;
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
import com.yangjun.baby.adapter.MoodAdapter;
import com.yangjun.baby.constants.BabyConstants;
import com.yangjun.baby.entity.Infos;
import com.yangjun.baby.entity.MoodEntity;
import com.yangjun.baby.util.BabyUtils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class MoodActivity extends SherlockActivity{
	private PullToRefreshListView list;
	private MoodAdapter adapter;
	private int page=0;
	private boolean isFirstLoading = true;
	private TextView mTitleTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mood_list);
		initActionBar();
		adapter=new MoodAdapter(MoodActivity.this);
		list=(PullToRefreshListView)this.findViewById(R.id.moodList);
		list.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		list.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener<ListView>(){
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Log.i("pull","onPullUpToRefresh");
				// TODO Auto-generated method stub
				String str = DateUtils.formatDateTime(MoodActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_NO_NOON);
				list.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
		        list.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
		        list.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
		        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后加载时间:" + str); 
				page++;
				new GetDataTask().execute();
			}
		});
		new GetDataTask().execute();
	}
	public List<Map<String, Object>> getData(){
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		for(int i=0;i<10;i++){
			Map<String,Object> item; 
			item = new HashMap<String,Object>();
			item.put(BabyConstants.FORUM_HEAD_IMG, R.drawable.img_head_default);
			item.put(BabyConstants.FORUM_TITLE, R.string.register_tip);
			item.put(BabyConstants.FORUM_NICKNAME, R.string.register_nickname);
			item.put(BabyConstants.FORUM_TIME, R.string.login_str);
			item.put(BabyConstants.FORUM_VIEW_NUM, "20");
			item.put(BabyConstants.FORUM_REPLY_NUM, "5");
			data.add(item); 
		}
		return data;
	}
	private void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_new);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mTitleTextView = (TextView) findViewById(R.id.tv_title);
		mTitleTextView.setText(R.string.main_motion);
		ImageView back=(ImageView)this.findViewById(R.id.iv_left_icon);
		back.setOnClickListener(new ImageView.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent localIntent = new Intent(MoodActivity.this, MainActivity.class);
				 localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 MoodActivity.this.startActivity(localIntent);
			}
			
		});
		ImageView newBtn=(ImageView)this.findViewById(R.id.iv_right_icon);
		newBtn.setOnClickListener(new ImageView.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent localIntent = new Intent(MoodActivity.this, MoodNewActivity.class);
				
				 MoodActivity.this.startActivity(localIntent);
			}
			
		});
	}
	private void updateAdapter(String result){
		ObjectMapper mapper=new ObjectMapper();
		try {
			MoodEntity[] forums=mapper.readValue(result,MoodEntity[].class);
	
			List<MoodEntity> forumsList=Arrays.asList(forums);
			adapter.setMultitermDataToFooter(forumsList);
		    ListView mlist = list.getRefreshableView();  
		    if (!(mlist).isStackFromBottom()) {  
		        mlist.setStackFromBottom(true);  
		    }  
		    mlist.setStackFromBottom(false);  
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
			Map<String,String> map=new HashMap<String,String>();
			map.put("page", page+"");
			map.put("user_id",Infos.user.getId());
			String res=BabyUtils.getMGetResult(BabyUtils.MOOD_LIST_URL, map);
			return res;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateAdapter(result);
		}
	}
}
