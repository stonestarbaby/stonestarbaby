package com.yangjun.baby.activity;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yangjun.baby.R;
import com.yangjun.baby.adapter.ReplyAdapter;
import com.yangjun.baby.entity.Forum;
import com.yangjun.baby.entity.Infos;
import com.yangjun.baby.entity.ReplyEntity;
import com.yangjun.baby.util.BabyUtils;
import com.yangjun.baby.util.JSONUtils;
import com.yangjun.baby.util.UIUtils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class PostActivity extends SherlockActivity{
	private TextView  postView,replyView;
	private ReplyAdapter adapter;
	private PullToRefreshListView list;
	private TextView mTitleTextView;
	private String post_id;
	private String content;
	private int page=1;
	
	public Handler handle=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.post_main);
		initActionBar();
		post_id=getIntent().getStringExtra("post_id");
		Log.i("baby","PostID:"+this.post_id);
		//postView=(TextView)this.findViewById(R.id.postContent);
		replyView=(TextView)this.findViewById(R.id.post_reply_textView);
		adapter=new ReplyAdapter(this);
		list=(PullToRefreshListView)this.findViewById(R.id.person_replyList);
		list.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
		list.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener<ListView>(){
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				Log.i("pull","onPullUpToRefresh");
				// TODO Auto-generated method stub
				String str = DateUtils.formatDateTime(PostActivity.this, System.currentTimeMillis(), DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_NO_NOON);
				list.getLoadingLayoutProxy().setRefreshingLabel("正在加载");
		        list.getLoadingLayoutProxy().setPullLabel("上拉加载更多");
		        list.getLoadingLayoutProxy().setReleaseLabel("释放开始加载");
		        refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("最后加载时间:" + str); 
				page++;
				new GetDataListTask(post_id).execute();
			}
		});
		Button replyBtn=(Button)this.findViewById(R.id.post_replyBtn);
		replyBtn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String content=replyView.getText().toString();
				if(!content.equals("")){
					new GetDataTask(post_id).execute();
				}
			}
			
		});
		new GetDetailDataTask(this.post_id).execute();
	}
	private void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_back);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mTitleTextView = (TextView) findViewById(R.id.tv_title);
		mTitleTextView.setText(R.string.title_post);
		ImageView back=(ImageView)this.findViewById(R.id.iv_left_icon);
		back.setOnClickListener(new ImageView.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(PostActivity.this,ForumActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				PostActivity.this.finish();
				PostActivity.this.startActivity(intent);
			}
			
		});
	}
	private void updateDetail(String result){
		Log.i("baby", "updateAdapter");
		ObjectMapper mapper=new ObjectMapper();
		try {
			Forum forum=JSONUtils.jsonNode2GenericObject(JSONUtils.getNode(result, "post"),  new TypeReference<Forum>(){});
			String html="<h1>this is h1</h1><p>This text is normal</p><img src='https://www.google.com.hk/intl/zh-CN/images/logo_cn.png' />";
			content=forum.getContent();
			//postView.setText(Html.fromHtml(forum.getContent(), new URLImageParser(PostActivity.this,PostActivity.this.postView),null));
			Log.i("baby", forum.toString());
			JavaType javaType = JSONUtils.getCollectionType(LinkedList.class, ReplyEntity.class); 
			LinkedList<ReplyEntity> replys =  (LinkedList<ReplyEntity>)mapper.readValue(JSONUtils.getNode(result, "replys"), javaType); 
			Log.i("baby", "Length:"+replys.size());
			//adapter=new ForumAdapter(ForumActivity.this);
			ReplyEntity text=new ReplyEntity();
			text.setContent(content);
			adapter.clear();
			adapter.setDataFirst(text);
			adapter.setMultitermDataToFooter(replys);
			list.setAdapter(adapter);
			//UIUtils.setListViewHeightBasedOnChildren(list.getRefreshableView());
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
	private void updateListAdapter(String result){
		Log.i("baby", "updateAdapter");
		ObjectMapper mapper=new ObjectMapper();
		try {
			ReplyEntity[] forums=mapper.readValue(result,ReplyEntity[].class);
			//adapter=new ForumAdapter(ForumActivity.this);
			List<ReplyEntity> forumsList=Arrays.asList(forums);
			adapter.setMultitermDataToFooter(forumsList);
			list.setAdapter(adapter);
			//UIUtils.setListViewHeightBasedOnChildren(list.getRefreshableView());
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
	private void updateAdapter(String result){
		Log.i("baby", "updateAdapter");
		ObjectMapper mapper=new ObjectMapper();
		try {
			ReplyEntity forums=mapper.readValue(result,ReplyEntity.class);
			//adapter=new ForumAdapter(ForumActivity.this);
			adapter.setDataLast(forums);
			list.setAdapter(adapter);
			//UIUtils.setListViewHeightBasedOnChildren(list.getRefreshableView());
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
		private String postid;
		public GetDataTask(String postid){
			this.postid=postid;
		}
		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			Map<String,String> map=new HashMap<String,String>();
			map.put("user_id", Infos.USER_ID);
			map.put("post_id",postid);
			map.put("content",replyView.getText().toString());
			String res=BabyUtils.getMGetResult(BabyUtils.POST_REPLY_URL, map);
			return res;
		}
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateAdapter(result);
		}
	}
	private class GetDataListTask extends AsyncTask<Void, Void, String> {
		private String postid;
		public GetDataListTask(String postid){
			this.postid=postid;
		}
		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			Map<String,String> map=new HashMap<String,String>();
			map.put("post_id",postid);
			map.put("page",page+"");
			page++;
			String res=BabyUtils.getMGetResult(BabyUtils.PERSON_REPLY_LIST_URL, map);
			return res;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateListAdapter(result);
		}
	}
	private class GetDetailDataTask extends AsyncTask<Void, Void, String> {
		private String postid;
		public GetDetailDataTask(String postid){
			this.postid=postid;
		}
		@Override
		protected String doInBackground(Void... params) {
			// Simulates a background job.
			String res=BabyUtils.getPostDetailResult(this.postid);
			return res;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateDetail(result);
		}
	}
}
