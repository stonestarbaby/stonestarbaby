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
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.yangjun.baby.MainActivity;
import com.yangjun.baby.R;
import com.yangjun.baby.adapter.ForumAdapter;
import com.yangjun.baby.constants.BabyConstants;
import com.yangjun.baby.entity.Forum;
import com.yangjun.baby.entity.ReplyEntity;
import com.yangjun.baby.util.BabyUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class ForumActivity extends SherlockActivity{
	private PullToRefreshListView list;
	private ForumAdapter adapter;
	private int page=0;
	private boolean isFirstLoading = true;
	private TextView mTitleTextView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.forum_list);
		initActionBar();
		adapter=new ForumAdapter(ForumActivity.this);
		list=(PullToRefreshListView)this.findViewById(R.id.person_replyList);
		list.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		list.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener<ListView>(){
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Log.i("pull","onPullUpToRefresh");
				// TODO Auto-generated method stub
				String str = DateUtils.formatDateTime(ForumActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_NO_NOON);
				list.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
		        list.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
		        list.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
		        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后加载时间:" + str); 
				page++;
				new GetDataTask().execute();
			}
		});
		list.setOnItemClickListener( new ListView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				// TODO Auto-generated method stub
				int i =index-1;
			    Intent localIntent = new Intent(ForumActivity.this, PostActivity.class);
			    Bundle localBundle = new Bundle();
			    localBundle.putString("post_id",ForumActivity.this.adapter.getItem(i).getPostId());
			    localIntent.putExtras(localBundle);
			    ForumActivity.this.startActivity(localIntent);
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
		mTitleTextView.setText(R.string.nav_fourm);
		ImageView back=(ImageView)this.findViewById(R.id.iv_left_icon);
		back.setOnClickListener(new ImageView.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent localIntent = new Intent(ForumActivity.this, MainActivity.class);
				 localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 ForumActivity.this.startActivity(localIntent);
			}
			
		});
		ImageView newBtn=(ImageView)this.findViewById(R.id.iv_right_icon);
		newBtn.setOnClickListener(new ImageView.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent localIntent = new Intent(ForumActivity.this, PostNewActivity.class);
				
				 ForumActivity.this.startActivity(localIntent);
			}
			
		});
	}
	private void updateAdapter(String result){
		ObjectMapper mapper=new ObjectMapper();
		try {
			Forum[] forums=mapper.readValue(result,Forum[].class);
			for (int i = 0; i < forums.length; i++) {
	            System.out.println(forums[i]);
	        }
			Log.i("baby", "Size:"+forums[1].getPostTitle());
			List<Forum> forumsList=Arrays.asList(forums);
			//adapter=new ForumAdapter(ForumActivity.this);
			adapter.clear();
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
			String res=BabyUtils.getPostListResult(page);
			return res;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateAdapter(result);
		}
	}
}
