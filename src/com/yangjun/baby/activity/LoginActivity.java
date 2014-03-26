package com.yangjun.baby.activity;

import com.yangjun.baby.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity{  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_login);
        Button login_btn=(Button)this.findViewById(R.id.main_login_btn);
        login_btn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(LoginActivity.this,LoginMainActivity.class);
				startActivity(intent);
			}
        	
        });
    }  
} 