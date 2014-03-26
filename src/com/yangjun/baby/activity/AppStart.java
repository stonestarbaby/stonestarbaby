package com.yangjun.baby.activity;

import com.yangjun.baby.MainActivity;
import com.yangjun.baby.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class AppStart extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.welcome);
		new Handler().postDelayed(new Runnable(){  
            @Override  
            public void run(){  
                Intent intent = new Intent (AppStart.this,MainActivity.class);           
                startActivity(intent);
                AppStart.this.finish();
            }  
        }, 2000); 
	}
	
}
