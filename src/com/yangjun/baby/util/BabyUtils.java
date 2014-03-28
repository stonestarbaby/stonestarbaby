package com.yangjun.baby.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.yangjun.baby.R;
import com.yangjun.baby.activity.LoginMainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class BabyUtils {
	private static String BASE_URL="http://121.54.173.183/";
	private static String LOGIN_URL=BASE_URL+"app/login.php";
	private static String REGISTER_URL=BASE_URL+"app/register.php";
	private static String POST_LIST_URL=BASE_URL+"app/post_list.php";
	private static String POST_DETAIL_URL=BASE_URL+"app/post_detail.php";
	public static String EXPERT_LIST_URL=BASE_URL+"app/expert_list.php";
	public static String CHAT_SEND_URL=BASE_URL+"app/chat_send.php";
	public static String CHAT_GET_URL=BASE_URL+"app/chat_get.php";
	public static String PERSON_POST_LIST_URL=BASE_URL+"app/person_post_list.php";
	public static String PERSON_REPLY_LIST_URL=BASE_URL+"app/person_reply_list.php";
	public static String getMGetResult(String handleurl,Map<String,String> map){
		int res = 0;
	    HttpClient client = new DefaultHttpClient();
	    StringBuilder str = new StringBuilder();
	    String url=handleurl;
	    if(null!=map&&map.size()>=1){
	    	 url+="?";
	    	 Iterator<Entry<String,String>> ite=map.entrySet().iterator();
	    	 while(ite.hasNext()){
	    		 Entry<String,String> ent=ite.next();
	    		 String pair=ent.getKey()+"="+ent.getValue()+"&";
	    		 url+=pair;
	    	 }
	    	 url=url.substring(0, url.length()-1);
	    }
	    Log.i("baby", url);
	    HttpGet httpGet = new HttpGet(url);
	    try {
	        HttpResponse httpRes = client.execute(httpGet);
	        res = httpRes.getStatusLine().getStatusCode();
	        if(res == 200){
	            BufferedReader buffer = new BufferedReader(new InputStreamReader(httpRes.getEntity().getContent()));
	            for(String s = buffer.readLine(); s != null ; s = buffer.readLine()) {
	            	str.append(s);
	            }
	        }else{
	        	return "";
	        }   
		}catch(Exception io){
			return "";
		}
	    Log.i("baby", str.toString());
	    return str.toString();
	}
	
	public static String getPostDetailResult(String postId){
		int res = 0;
	    HttpClient client = new DefaultHttpClient();
	    StringBuilder str = new StringBuilder();
	    String url=BabyUtils.POST_DETAIL_URL+"?postid="+postId;
	    Log.i("baby", url);
	    HttpGet httpGet = new HttpGet(url);
	    try {
	        HttpResponse httpRes = client.execute(httpGet);
	        httpRes = client.execute(httpGet);
	        res = httpRes.getStatusLine().getStatusCode();
	        if(res == 200){
	            BufferedReader buffer = new BufferedReader(new InputStreamReader(httpRes.getEntity().getContent()));
	            for(String s = buffer.readLine(); s != null ; s = buffer.readLine()) {
	            	str.append(s);
	            }
	        }else{
	        	return "";
	        }   
		}catch(Exception io){
			return "";
		}
	    Log.i("baby", str.toString());
	    return str.toString();
	}
	public static String getPostListResult(int page){
		if(page<0){
			page=0;
		}
		int res = 0;
	    HttpClient client = new DefaultHttpClient();
	    StringBuilder str = new StringBuilder();
	    String url=BabyUtils.POST_LIST_URL+"?page="+page;
	    Log.i("baby", url);
	    HttpGet httpGet = new HttpGet(url);
	    try {
	        HttpResponse httpRes = client.execute(httpGet);
	        httpRes = client.execute(httpGet);
	        res = httpRes.getStatusLine().getStatusCode();
	        if(res == 200){
	            BufferedReader buffer = new BufferedReader(new InputStreamReader(httpRes.getEntity().getContent()));
	            for(String s = buffer.readLine(); s != null ; s = buffer.readLine()) {
	            	str.append(s);
	            }
	        }else{
	        	return "";
	        }   
		}catch(Exception io){
			return "";
		}
	    return str.toString();
	}
	public static String getLoginResult(String name,String pwd){
		
		int res = 0;
	    HttpClient client = new DefaultHttpClient();
	    StringBuilder str = new StringBuilder();
	    String url=BabyUtils.LOGIN_URL+"?username="+name+"&pwd="+pwd;
	    Log.i("baby", url);
	    HttpGet httpGet = new HttpGet(url);
	    try {
	        HttpResponse httpRes = client.execute(httpGet);
	        httpRes = client.execute(httpGet);
	        res = httpRes.getStatusLine().getStatusCode();
	        if(res == 200){
	            BufferedReader buffer = new BufferedReader(new InputStreamReader(httpRes.getEntity().getContent()));
	            for(String s = buffer.readLine(); s != null ; s = buffer.readLine()) {
	            	str.append(s);
	            }
	        }else{
	        	return "";
	        }   
		}catch(Exception io){
			return "";
		}
	    return str.toString();
	}
	private static String[] USER_ARR_NAME={"username","nickname","pwd","sex","year","month","day","province","city"};
	public static String getRegisterResult(String[] strArr){
		int res = 0;
	    HttpClient client = new DefaultHttpClient();
	    StringBuilder str = new StringBuilder();
	    HttpPost httpPost= new HttpPost(REGISTER_URL);
	    List<NameValuePair> params = new ArrayList<NameValuePair>();  
	    for(int i=0;i<USER_ARR_NAME.length;i++){
	    	 params.add(new BasicNameValuePair(USER_ARR_NAME[i],strArr[i]));
	    }
	   
	    try {
	    	httpPost.setEntity(new UrlEncodedFormEntity(params)); 
	        HttpResponse httpRes = client.execute(httpPost);
	        httpRes = client.execute(httpPost);
	        res = httpRes.getStatusLine().getStatusCode();
	        if(res == 200){
	            BufferedReader buffer = new BufferedReader(new InputStreamReader(httpRes.getEntity().getContent()));
	            for(String s = buffer.readLine(); s != null ; s = buffer.readLine()) {
	            	str.append(s);
	            }
	        }else{
	        	return "";
	        }   
		}catch(Exception io){
			return "";
		}
	    return str.toString();
	}
	public static AlertDialog getLoadingDialog(Activity activity,int strId){
		ContextThemeWrapper contextTheme = new ContextThemeWrapper(activity, R.style.LoadStyle );
        AlertDialog.Builder builder;     
        AlertDialog alertDialog;          
        LayoutInflater inflater = (LayoutInflater)contextTheme.getSystemService(activity.LAYOUT_INFLATER_SERVICE);     
        View layout = inflater.inflate(R.layout.loading,null);       
        builder = new AlertDialog.Builder(contextTheme);
        TextView text=(TextView)layout.findViewById(R.id.load_str);
        text.setText(strId);
        builder.setView(layout);     
        alertDialog = builder.create();
        return alertDialog;   
    }
	public static String getStringToday() {
	    Date currentTime = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String dateString = formatter.format(currentTime);
	    return dateString;
	}
}