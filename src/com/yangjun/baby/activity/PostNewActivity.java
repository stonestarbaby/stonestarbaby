package com.yangjun.baby.activity;

import java.io.File;
import java.io.FileNotFoundException;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yangjun.baby.R;
import com.yangjun.baby.constants.BabyConstants;
import com.yangjun.baby.entity.Infos;
import com.yangjun.baby.ui.EditTextImage;
import com.yangjun.baby.util.BabyUtils;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class PostNewActivity extends SherlockActivity {
	private EditText titleT;
	private EditTextImage contentT;
	private TextView mTitleTextView;
	private AlertDialog loadingDialog;
	private AsyncHttpClient client = new AsyncHttpClient();
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(BabyConstants.TYPE_POST_NEW==msg.what){
				if(loadingDialog!=null){
					loadingDialog.cancel();
				}
				String res=msg.getData().getString(BabyConstants.BUN_H__POST_NEW);
				if(res==""){
					new AlertDialog.Builder(PostNewActivity.this).setIcon(R.drawable.login_error)
					.setTitle(R.string.net_wrong_title).setMessage(R.string.net_wrong_des)
					.create().show();
					return ;
				}else{
					if(BabyConstants.MSG_FAIL.equals(res)){
						Toast toast=Toast.makeText(PostNewActivity.this, R.string.msg_noname, Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						
					}else{
						Intent intent=new Intent(PostNewActivity.this,PostActivity.class);
						intent.putExtra("post_id", res);
						PostNewActivity.this.finish();
						PostNewActivity.this.startActivity(intent);
					}
				}
			}
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.post_new);
		initActionBar();
		Button save=(Button)this.findViewById(R.id.post_new_saveBtn);
		this.titleT=(EditText)this.findViewById(R.id.post_new_title);
		this.contentT=(EditTextImage)this.findViewById(R.id.post_new_content);
		save.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String title=titleT.getText().toString();
				final String content=contentT.getText().toString();
				Log.i("post", "Content:"+content);
				if("".equals(title)||"".equals(content)){
					new AlertDialog.Builder(PostNewActivity.this).setIcon(R.drawable.login_error)
					.setTitle(R.string.post_new_wrong_title).setMessage(R.string.post_new_wrong_des)
					.create().show();
					return ;
				}
				loadingDialog=BabyUtils.getLoadingDialog(PostNewActivity.this,R.string.post_new_contening);
				loadingDialog.show();
				new Thread(){
					public void run(){
						String[] strArr=new String[]{Infos.USER_ID,title,content};
						String res=BabyUtils.postNew(strArr);
						Log.i("POSTNEW", title);
						Message msg=new Message();
						msg.what=BabyConstants.TYPE_POST_NEW;
						Bundle bun=new Bundle();
						bun.putString(BabyConstants.BUN_H__POST_NEW, res);
						msg.setData(bun);
						handler.sendMessage(msg);
					}
				}.start();
			}
		
		});
		Button upload=(Button)this.findViewById(R.id.post_new_imgeBtn);
		upload.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i,100);
			}
		
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == 100 && resultCode == RESULT_OK && null != data) {
	        Uri selectedImage = data.getData();
	        String[] filePathColumn = { MediaStore.Images.Media.DATA };

	        Cursor cursor = getContentResolver().query(selectedImage,
	                filePathColumn, null, null, null);
	        cursor.moveToFirst();

	        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	        String picturePath = cursor.getString(columnIndex);
	        cursor.close();
	        try {
				upLoadByAsyncHttpClient(picturePath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	        // String picturePath contains the path of selected Image
	}
	private void initActionBar(){
		ActionBar actionBar = this.getSupportActionBar();
		actionBar.setCustomView(R.layout.actionbar_back);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mTitleTextView = (TextView) findViewById(R.id.tv_title);
		mTitleTextView.setText(R.string.nav_fourm);
		ImageView back=(ImageView)this.findViewById(R.id.iv_left_icon);
		back.setOnClickListener(new ImageView.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 Intent localIntent = new Intent(PostNewActivity.this, ForumActivity.class);
				 localIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 PostNewActivity.this.startActivity(localIntent);
			}
			
		});
	}
	public void upLoadByAsyncHttpClient(final String path) throws FileNotFoundException {
		final String path1="/mnt/sdcard/Desert.jpg";
        RequestParams params = new RequestParams();
        params.put("uploadfile", new File(path));  
        client.post(BabyUtils.UPLAOD_IAMGE_URL, params, new AsyncHttpResponseHandler() {  
            @Override  
            public void onSuccess(int arg0, String url) {  
                super.onSuccess(arg0, url);
                Drawable d=Drawable.createFromPath(path);
                contentT.insertDrawable(url, d);
            }  
        });  
    }  
}
