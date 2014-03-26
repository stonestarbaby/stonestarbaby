package com.yangjun.baby.activity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;  
import java.util.Arrays;
import java.util.Calendar;  
import java.util.HashMap;
import java.util.List;   
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.yangjun.baby.MainActivity;
import com.yangjun.baby.R;
import com.yangjun.baby.adapter.WeChatAdapter;
import com.yangjun.baby.chat.WeChatMessage;
import com.yangjun.baby.constants.BabyConstants;
import com.yangjun.baby.entity.Forum;
import com.yangjun.baby.entity.MessageEntity;
import com.yangjun.baby.entity.User;
import com.yangjun.baby.entity.ChatInfo;
import com.yangjun.baby.util.BabyUtils;

import android.os.Bundle;  
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;  
import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.Window;  
import android.view.inputmethod.InputMethodManager;  
import android.widget.Button;  
import android.widget.EditText;  
import android.widget.ListView;  
import android.widget.Toast;
  
@SuppressLint("NewApi")
public class WeChatActivity extends Activity {  
  
    private Button BtnSend;  
    private EditText InputBox;  
    private List<WeChatMessage> mData;  
    private WeChatAdapter mAdapter;
    private  ListView mListView;
    private Thread getMThread;
    private boolean isMessage=true;
    private boolean issending=false;
    private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(BabyConstants.TYPE_CHAT==msg.what){
				issending=false;
				InputMethodManager imm=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);  
				String result=msg.getData().getString(BabyConstants.BUN_H_CHAT_SEND);
				ObjectMapper mapper=new ObjectMapper();
				MessageEntity[] forums;
				if(!result.equals("[]")&&!result.equals("FAIL")&&!result.equals("")){
					try {
						forums = mapper.readValue(result,MessageEntity[].class);
						Log.i("chat", forums.length+"");
						List<WeChatMessage> forumsList=WeChatMessage.convertMsg(forums);
						for(int i=0;i<forumsList.size();i++){
							mData.add(forumsList.get(i));
						}
						 mAdapter.Refresh();
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
					//清空输入框  
	                InputBox.setText("");  
	                //关闭输入法  
	                imm.hideSoftInputFromWindow(null, InputMethodManager.HIDE_IMPLICIT_ONLY);  
	                //滚动列表到当前消息  
	                mListView.smoothScrollToPositionFromTop(mData.size(), 0);  
				}
			}
		}
		
	};
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.chat);
        WeChatMessage.LAST_DATED=BabyUtils.getStringToday();
        mListView=(ListView)findViewById(R.id.MainList);  
        mData=LoadData();  
        mAdapter=new WeChatAdapter(this, mData);  
        mListView.setAdapter(mAdapter);  
        mListView.smoothScrollToPositionFromTop(mData.size(), 0);  
        InputBox=(EditText)findViewById(R.id.InputBox);  
        BtnSend=(Button)findViewById(R.id.BtnSend);  
        BtnSend.setOnClickListener(new OnClickListener(){  
            @Override  
            public void onClick(View view){  
            	sentMessage();
            }  
        });
    } 
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isMessage=false;;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isMessage=true;
		getMThread=new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(isMessage){
					getMessage();
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
        	
        });
		getMThread.start();
	}

	private void sentMessage(){
    	Log.i("chat", InputBox.getText().toString()+":"+issending);
    	if(!InputBox.getText().toString().equals("")&&!issending){ 
        	issending=true;
        	new Thread(){
				public void run(){
					Map<String,String> map=new HashMap<String,String>();
					map.put("from_id", ChatInfo.USER_ID);
					map.put("to_id", ChatInfo.EXPERT_ID);
					try {
						map.put("content", URLEncoder.encode(InputBox.getText().toString(),"UTF-8"));
						map.put("lastdated", URLEncoder.encode(WeChatMessage.LAST_DATED,"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String res=BabyUtils.getMGetResult(BabyUtils.CHAT_SEND_URL,map);
					Log.i("chat", res);
					Message msg=new Message();
					msg.what=BabyConstants.TYPE_CHAT;
					Bundle bun=new Bundle();
					bun.putString(BabyConstants.BUN_H_CHAT_SEND, res);
					msg.setData(bun);
					handler.sendMessage(msg);
				}
			}.start();
        }  
    }
    private void getMessage(){
    	if(!issending){ 
        	issending=true;
        	new Thread(){
				public void run(){
					Map<String,String> map=new HashMap<String,String>();
					map.put("from_id", ChatInfo.USER_ID);
					map.put("to_id", ChatInfo.EXPERT_ID);
					try {
						map.put("lastdated", URLEncoder.encode(WeChatMessage.LAST_DATED,"UTF-8"));
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String result=BabyUtils.getMGetResult(BabyUtils.CHAT_GET_URL,map);
					Log.i("chatget", result);
					if(!result.equals("[]")&&!result.equals("FAIL")&&!result.equals("")){
						Message msg=new Message();
						msg.what=BabyConstants.TYPE_CHAT;
						Bundle bun=new Bundle();
						bun.putString(BabyConstants.BUN_H_CHAT_SEND, result);
						msg.setData(bun);
						handler.sendMessage(msg);
					}else{
						issending=false;
					}
				}
			}.start();
        }  
    }
    private List<WeChatMessage> LoadData()   {  
        List<WeChatMessage> Messages=new ArrayList<WeChatMessage>();  
          
        WeChatMessage Message=new WeChatMessage(WeChatMessage.MessageType_Time,BabyUtils.getStringToday());  
        Messages.add(Message);  
        return Messages;  
    }    
}  