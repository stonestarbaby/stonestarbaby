package com.yangjun.baby.fragment;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yangjun.baby.CommonLog;
import com.yangjun.baby.LogFactory;
import com.yangjun.baby.R;
import com.yangjun.baby.activity.PostActivity;
import com.yangjun.baby.adapter.PersonForumAdapter;
import com.yangjun.baby.adapter.PersonReplyAdapter;
import com.yangjun.baby.entity.Infos;
import com.yangjun.baby.entity.PersonForum;
import com.yangjun.baby.entity.PersonReplyEntity;
import com.yangjun.baby.util.BabyUtils;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class PersonReplyFragment extends CommonFragment{
	private PullToRefreshListView list;
	private PersonReplyAdapter adapter;
	private int page=0;
	private static final CommonLog log = LogFactory.createLog();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.e("PersonEditFragment onCreateView");
		View view = inflater.inflate(R.layout.person_reply, null);
		adapter=new PersonReplyAdapter(PersonReplyFragment.this.getActivity());
		list=(PullToRefreshListView)view.findViewById(R.id.person_replyList);
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
				// TODO Auto-generated method stub
				int i =index-1;
			    Intent localIntent = new Intent(PersonReplyFragment.this.getActivity(), PostActivity.class);
			    Bundle localBundle = new Bundle();
			    localBundle.putString("post_id",PersonReplyFragment.this.adapter.getItem(i).getPostId());
			    localIntent.putExtras(localBundle);
			    PersonReplyFragment.this.getActivity().startActivity(localIntent);
			}
			
		});
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViews();
	}
	private void setupViews(){
		
	}
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	private void updateAdapter(String result){
		ObjectMapper mapper=new ObjectMapper();
		try {
			PersonReplyEntity[] forums=mapper.readValue(result,PersonReplyEntity[].class);
			
			List<PersonReplyEntity> forumsList=Arrays.asList(forums);
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
			Map<String,String> map= new HashMap<String,String>();
			map.put("page", page+"");
			map.put("user_id", Infos.user.getId());
			String res=BabyUtils.getMGetResult(BabyUtils.PERSON_REPLY_LIST_URL, map);
			return res;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			updateAdapter(result);
		}
	}
}
