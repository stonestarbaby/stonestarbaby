package com.yangjun.baby.fragment;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.yangjun.baby.CommonLog;
import com.yangjun.baby.LogFactory;
import com.yangjun.baby.MainActivity;
import com.yangjun.baby.R;
import com.yangjun.baby.activity.ExpertActivity;
import com.yangjun.baby.activity.LoginMainActivity;
import com.yangjun.baby.activity.PersonActivity;
import com.yangjun.baby.activity.RegisterActivity;
import com.yangjun.baby.activity.WeChatActivity;
import com.yangjun.baby.chat.WeChatMessage;
import com.yangjun.baby.constants.BabyConstants;
import com.yangjun.baby.entity.Infos;
import com.yangjun.baby.entity.User;
import com.yangjun.baby.util.BabyUtils;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends CommonFragment{
	
	private static final CommonLog log = LogFactory.createLog();
	private EditText eUser;
	private EditText ePass;
	private AlertDialog loadingDialog;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(BabyConstants.TYPE_LOGIN==msg.what){
				if(loadingDialog!=null){
					loadingDialog.cancel();
				}
				String res=msg.getData().getString(BabyConstants.BUN_H_LOGIN);
				if(res==""){
					new AlertDialog.Builder(LoginFragment.this.getActivity()).setIcon(R.drawable.login_error)
					.setTitle(R.string.net_wrong_title).setMessage(R.string.net_wrong_des)
					.create().show();
					return ;
				}else{
					if(BabyConstants.MSG_NONAME.equals(res)){
						Toast toast=Toast.makeText(LoginFragment.this.getActivity(), R.string.msg_noname, Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						
					}else if(BabyConstants.MSG_WRONGPASSWORD.equals(res)){
						Toast toast=Toast.makeText(LoginFragment.this.getActivity(), R.string.msg_wrongpassword, Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}else{
						ObjectMapper mapper=new ObjectMapper();
						try {
							User user=mapper.readValue(res, User.class);
							if(user!=null){
								Infos.user=user;
								Infos.USER_ID=user.getId();
								Infos.USER_NICKNAME=user.getNickname();
								Infos.ISLOGIN=true;
								if(user.getIsExpert().equals("1")){
									listenChat();
									Infos.EXPERT_ID=Infos.USER_ID;
								}
								Log.i("baby", user.toString());
							}
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
						Intent intent=new Intent(LoginFragment.this.getActivity(),MainActivity.class);
						LoginFragment.this.getActivity().finish();
						LoginFragment.this.getActivity().startActivity(intent);
					}
				}
			}
		}
		
	};
	private void listenChat(){
		Log.i("baby", "start");
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Map<String,String> map=new HashMap<String,String>();
				map.put("expert_id",Infos.user.getId());
				while(true){
					String res=BabyUtils.getMGetResult(BabyUtils.EXPERT_NEW_CHAT_EXIST_URL, map);
					if(res.equals("FAIL")){
						
					}else{
						String[] arr=res.split(",");
						if(arr.length==2){
							Infos.EXPERT_ID=arr[1];
							Infos.CHAT_INFO_ID=arr[0];
							Infos.main.startActivity(new Intent(Infos.main,WeChatActivity.class));
							return ;
						}
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
        	
        }).start();
    }  
	@Override
	public void onCreate(Bundle savedInstanceState) {
		log.e("PersonFragment onCreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		log.e("PersonFragment onCreateView");
		View view = inflater.inflate(R.layout.login_main, null);
		Button login=(Button)view.findViewById(R.id.loginBtn);
		this.eUser=(EditText)view.findViewById(R.id.user_name);
		this.ePass=(EditText)view.findViewById(R.id.nickname);
		login.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String name=eUser.getText().toString();
				final String password=ePass.getText().toString();
				if("".equals(name)||"".equals(password)){
					new AlertDialog.Builder(LoginFragment.this.getActivity()).setIcon(R.drawable.login_error)
					.setTitle(R.string.login_wrong_title).setMessage(R.string.login_wrong_des)
					.create().show();
					return ;
				}
				loadingDialog=BabyUtils.getLoadingDialog(LoginFragment.this.getActivity(),R.string.load_logining);
				loadingDialog.show();
				new Thread(){
					public void run(){
						String res=BabyUtils.getLoginResult(name, password);
						System.out.println("Baby:"+res);
						Log.i("baby", res);
						Message msg=new Message();
						msg.what=BabyConstants.TYPE_LOGIN;
						Bundle bun=new Bundle();
						bun.putString(BabyConstants.BUN_H_LOGIN, res);
						msg.setData(bun);
						handler.sendMessage(msg);
					}
				}.start();
			}
		
		});
		Button register=(Button)view.findViewById(R.id.registerBtn);
		register.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginFragment.this.getActivity(),RegisterActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				LoginFragment.this.getActivity().startActivity(intent);
				
			}
			
		});
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		log.e("PersonFragment onActivityCreated");
		
		setupViews();
	}
	
	private void setupViews(){
	}

	@Override
	public void onDestroy() {
		log.e("TechFragment onDestroy");
		super.onDestroy();
	}
}
