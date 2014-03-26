package com.yangjun.baby.activity;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.yangjun.baby.MainActivity;
import com.yangjun.baby.R;
import com.yangjun.baby.constants.BabyConstants;
import com.yangjun.baby.util.BabyUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends SherlockActivity {
	private int mYear=2013,mMonth=1,mDay=1;
	private EditText birthEdit,emailE,nicknameE,passwordE;
	private AlertDialog loadingDialog;
	private ImageView back;
	private TextView title;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(BabyConstants.TYPE_REGISTER==msg.what){
				if(loadingDialog!=null){
					loadingDialog.cancel();
				}
				String res=msg.getData().getString(BabyConstants.BUN_H_REGISTER);
				if(res==""){
					new AlertDialog.Builder(RegisterActivity.this).setIcon(R.drawable.login_error)
					.setTitle(R.string.net_wrong_title).setMessage(R.string.net_wrong_des)
					.create().show();
					return ;
				}else{
					if(BabyConstants.MSG_NAMEEXIST.equals(res)){
						Toast toast=Toast.makeText(RegisterActivity.this, R.string.msg_nameexist, Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						
					}else if(BabyConstants.MSG_FAIL.equals(res)){
						Toast toast=Toast.makeText(RegisterActivity.this, R.string.msg_fail_register, Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
					}else{
						Intent intent=new Intent(RegisterActivity.this,LoginMainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						RegisterActivity.this.startActivity(intent);
					}
				}
			}
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.register);
		initActionBar();
		birthEdit=(EditText)this.findViewById(R.id.birthday);
		birthEdit.setOnFocusChangeListener(new OnFocusChangeListener() {  
	        public void onFocusChange(View v, boolean hasFocus) {  
	            if (hasFocus == true) {  
	            	hideIM(v);  
	                showDialog(0);    
	            }  
	        }  
	    });
		birthEdit.setOnClickListener(new EditText.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				hideIM(arg0);  
                showDialog(0);  
			}
			
		});
		emailE=(EditText)this.findViewById(R.id.email);
		nicknameE=(EditText)this.findViewById(R.id.nickanme_re);
		passwordE=(EditText)this.findViewById(R.id.password);
		Button register=(Button)this.findViewById(R.id.register_btn);
		register.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String name=emailE.getText().toString();
				
				final String nickname=nicknameE.getText().toString();
				final String password=passwordE.getText().toString();
				Log.i("baby", name+":"+nickname);
				if("".equals(name)||"".equals(password)||"".equals(nickname)){
					new AlertDialog.Builder(RegisterActivity.this).setIcon(R.drawable.login_error)
					.setTitle(R.string.login_wrong_title).setMessage(R.string.login_wrong_des)
					.create().show();
					return ;
				}
				loadingDialog=BabyUtils.getLoadingDialog(RegisterActivity.this,R.string.load_registering);
				loadingDialog.show();
				new Thread(){
					public void run(){
						String[] strArr={name,nickname,password,"1",""+mYear,""+mMonth,""+mDay,"JS","NJ"};
						String res=BabyUtils.getRegisterResult(strArr);
						Log.i("baby", res);
						Message msg=new Message();
						msg.what=BabyConstants.TYPE_REGISTER;
						Bundle bun=new Bundle();
						bun.putString(BabyConstants.BUN_H_REGISTER, res);
						msg.setData(bun);
						handler.sendMessage(msg);
					}
				}.start();
			}
		
		});
	}
	private void initActionBar(){
		ActionBar actionBar =this.getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_back);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		back=(ImageView) findViewById(R.id.iv_left_icon);
		title=(TextView) findViewById(R.id.tv_title);
		title.setText(R.string.register_str);
		back.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				RegisterActivity.this.startActivity(intent);
				RegisterActivity.this.finish();
			}
			
		});
	}
	private DatePickerDialog.OnDateSetListener mDateSetListener =  
            new DatePickerDialog.OnDateSetListener() {  
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {  
                    mYear = year;  
                    String mm;  
                    String dd;  
                    if(monthOfYear<=9)  
                    {  
                        mMonth = monthOfYear+1;  
                        mm="0"+mMonth;  
                    }  
                    else{  
                        mMonth = monthOfYear+1;  
                        mm=String.valueOf(mMonth);  
                        }  
                    if(dayOfMonth<=9)  
                    {  
                        mDay = dayOfMonth;  
                        dd="0"+mDay;  
                    }  
                    else{  
                        mDay = dayOfMonth;  
                        dd=String.valueOf(mDay);  
                        }  
                    mDay = dayOfMonth;  
                    birthEdit.setText(String.valueOf(mYear)+"-"+mm+"-"+dd);  
                      
                }             
            };  
     
    protected Dialog onCreateDialog(int id) {  
        switch (id) {     
        case 0:  
            return new DatePickerDialog(this,  
                    mDateSetListener,  
                    mYear, mMonth, mDay);  
        case 1:  
            return new DatePickerDialog(this,  
                    mDateSetListener,  
                    mYear, mMonth, mDay);  
        }  
        return null;  
    }  
      
    // Òþ²ØÊÖ»ú¼üÅÌ   
    private void hideIM(View edt){  
        try {  
             InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);  
             IBinder  windowToken = edt.getWindowToken();  
             if(windowToken != null) {  
                 im.hideSoftInputFromWindow(windowToken, 0);  
             }  
         } catch (Exception e) {  
             
         }  
     }
}
