package com.yangjun.baby.activity;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yangjun.baby.R;
import com.yangjun.baby.adapter.ReplyAdapter;
import com.yangjun.baby.entity.Forum;
import com.yangjun.baby.entity.ReplyEntity;
import com.yangjun.baby.ui.URLImageParser;
import com.yangjun.baby.util.BabyUtils;
import com.yangjun.baby.util.JSONUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
public class PostActivity extends Activity{
	private TextView  postView;
	private ReplyAdapter adapter;
	private PullToRefreshListView list;
	private String post_id;
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
		post_id=getIntent().getStringExtra("post_id");
		Log.i("baby","PostID:"+this.post_id);
		postView=(TextView)this.findViewById(R.id.postContent);
		list=(PullToRefreshListView)this.findViewById(R.id.person_replyList);
		adapter=new ReplyAdapter(this);
		list.setMode(PullToRefreshBase.Mode.BOTH);
		new GetDataTask(this.post_id).execute();
	}
	private void updateAdapter(String result){
		Log.i("baby", "updateAdapter");
		ObjectMapper mapper=new ObjectMapper();
		try {
			Forum forum=JSONUtils.jsonNode2GenericObject(JSONUtils.getNode(result, "post"),  new TypeReference<Forum>(){});
			postView.setText(Html.fromHtml(forum.getContent(), new URLImageParser(PostActivity.this,PostActivity.this.postView),null));
			Log.i("baby", forum.toString());
			JavaType javaType = JSONUtils.getCollectionType(LinkedList.class, ReplyEntity.class); 
			LinkedList<ReplyEntity> replys =  (LinkedList<ReplyEntity>)mapper.readValue(JSONUtils.getNode(result, "replys"), javaType); 
			Log.i("baby", "Length:"+replys.size());
			//adapter=new ForumAdapter(ForumActivity.this);
			adapter.clear();
			adapter.setMultitermDataToFooter(replys);
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
		private String postid;
		public GetDataTask(String postid){
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
			updateAdapter(result);
		}
	}
}
