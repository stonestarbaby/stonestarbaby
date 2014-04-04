package com.yangjun.baby.ui;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import com.yangjun.baby.R;
import com.yangjun.baby.constants.BabyConstants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.widget.TextView;

public class URLImageParser implements ImageGetter {

    private Context context;
    private TextView textView;

    public URLImageParser(Context context, TextView textView) {
            this.context = context;
            this.textView = textView;
    }
    @Override
    public Drawable getDrawable(String source) {
    		URLDrawable drawable=new URLDrawable(context);
    		if(source.startsWith("http://localhost/bbs/")){
    			Log.d("post", "http://localhost/bbs/");
    			source=source.replace("http://localhost/bbs/", BabyConstants.URL_BASE);
    		}
            new RssImageLoader(drawable).execute(source);
            Log.d("***img_uri***", source);
            return drawable;
    }
    class RssImageLoader extends AsyncTask<String, Void, Drawable> {

            private URLDrawable urlDrawable;

            public RssImageLoader(URLDrawable urlDrawable) {
                    this.urlDrawable = urlDrawable;
                    Log.d("drawable", "*************");
            }

            @Override
            protected Drawable doInBackground(String... params) {
                    String uri = params[0];
                    BitmapDrawable drawable = null;
                    HttpGet get = new HttpGet(uri);
                    try {
                            HttpResponse response = new DefaultHttpClient().execute(get);
                            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                                    BufferedHttpEntity entity = new BufferedHttpEntity(
                                                    response.getEntity());
                                    // 对大分辨率和大图做处理
                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inJustDecodeBounds = true;
                                    BitmapFactory.decodeStream(entity.getContent(), null,
                                                    options);
                                    int imgWidth = options.outWidth;
                                    int imgHeight = options.outHeight;
                                    final int REQUIRED_SIZE = 250;
                                    int scale = 1; // 缩放的倍数
                                    while (true) {
                                            if (imgWidth / 2 < REQUIRED_SIZE
                                                            || imgHeight / 2 < REQUIRED_SIZE) {
                                                    break;
                                            }
                                            imgWidth /= 2;
                                            imgHeight /= 2;
                                            scale *= 2;
                                    }
                                    options.inSampleSize = scale;
                                    options.inJustDecodeBounds = false;
                                    Bitmap bitmap = BitmapFactory.decodeStream(
                                                    entity.getContent(), null, options);
                                    //Bitmap bitmap = BitmapFactory.decodeStream(entity.getContent());
                                    drawable = new BitmapDrawable(bitmap);
                                    drawable.setBounds(0,URLImageParser.this.textView.getHeight(), drawable.getIntrinsicWidth(),
                                                    drawable.getIntrinsicHeight());
                            }
                    } catch (ClientProtocolException e) {
                            e.printStackTrace();
                    } catch (IOException e) {
                            e.printStackTrace();
                    }
                    
                    return drawable;
            }

            @Override
            protected void onPostExecute(Drawable result) {
                    super.onPostExecute(result);
                    if (result != null) {
                    	urlDrawable.setBounds(result.getBounds());
                        urlDrawable.drawable = result;
                        URLImageParser.this.textView.invalidate();
                        URLImageParser.this.textView.setHeight(URLImageParser.this.textView.getHeight()+result.getIntrinsicHeight());
                        URLImageParser.this.textView.setEllipsize(null);
                    }
            }

    }

} 