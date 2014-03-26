package com.yangjun.baby.activity;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.yangjun.baby.MainActivity;
import com.yangjun.baby.R;
import com.yangjun.baby.constants.BabyConstants;
import com.yangjun.baby.entity.User;
import com.yangjun.baby.entity.ChatInfo;
import com.yangjun.baby.util.BabyUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class LoginMainActivity extends Activity {
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
					new AlertDialog.Builder(LoginMainActivity.this).setIcon(R.drawable.login_error)
					.setTitle(R.string.net_wrong_title).setMessage(R.string.net_wrong_des)
					.create().show();
					return ;
				}else{
					if(BabyConstants.MSG_NONAME.equals(res)){
						Toast toast=Toast.makeText(LoginMainActivity.this, R.string.msg_noname, Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						
					}else if(BabyConstants.MSG_WRONGPASSWORD.equals(res)){
						Toast toast=Toast.makeText(LoginMainActivity.this, R.string.msg_wrongpassword, Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}else{
						ObjectMapper mapper=new ObjectMapper();
						try {
							User user=mapper.readValue(res, User.class);
							if(user!=null){
								ChatInfo.USER_ID=user.getId();
								ChatInfo.USER_NICKNAME=user.getNickname();
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
						Intent intent=new Intent(LoginMainActivity.this,PersonActivity.class);
						LoginMainActivity.this.startActivity(intent);
					}
				}
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.login_main);
		Button login=(Button)this.findViewById(R.id.loginBtn);
		this.eUser=(EditText)this.findViewById(R.id.user_name);
		this.ePass=(EditText)this.findViewById(R.id.nickname);
		login.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String name=eUser.getText().toString();
				final String password=ePass.getText().toString();
				if("".equals(name)||"".equals(password)){
					new AlertDialog.Builder(LoginMainActivity.this).setIcon(R.drawable.login_error)
					.setTitle(R.string.login_wrong_title).setMessage(R.string.login_wrong_des)
					.create().show();
					return ;
				}
				loadingDialog=BabyUtils.getLoadingDialog(LoginMainActivity.this,R.string.load_logining);
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
		Button register=(Button)this.findViewById(R.id.registerBtn);
		register.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginMainActivity.this,MainActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				LoginMainActivity.this.startActivity(intent);
				
			}
			
		});
	}
}
