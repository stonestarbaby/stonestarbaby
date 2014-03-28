package com.yangjun.baby.activity;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yangjun.baby.R;
import com.yangjun.baby.adapter.ExpertAdapter;
import com.yangjun.baby.entity.Infos;
import com.yangjun.baby.entity.ExpertEntity;
import com.yangjun.baby.util.BabyUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
public class ExpertActivity extends Activity{
	private PullToRefreshListView list;
	private ExpertAdapter adapter;
	private int page=0;
	private boolean isFirstLoading = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.forum_list);
		adapter=new ExpertAdapter(ExpertActivity.this);
		list=(PullToRefreshListView)this.findViewById(R.id.person_replyList);
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
				ExpertActivity.this.startActivity(new Intent(ExpertActivity.this,WeChatActivity.class));
			}
			
		});
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
}
