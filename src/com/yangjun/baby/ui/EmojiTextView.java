package com.yangjun.baby.ui;

import java.net.URL;

import com.yangjun.baby.R;
import com.yangjun.baby.constants.BabyConstants;
import com.yangjun.baby.util.ImageDownListener;
import com.yangjun.baby.util.TextViewImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class EmojiTextView extends TextView{
	private ImageDownListener listener;
	public ImageDownListener getListener() {
		return listener;
	}
	public Handler handler;
	public void setListener(ImageDownListener listener) {
		this.listener = listener;
	}
	private Context context;
	private Html.ImageGetter emojiGetter = new ImageGetter(this);

	public EmojiTextView(Context paramContext){
		super(paramContext);
		this.context = paramContext;
	}

	public EmojiTextView(Context paramContext, AttributeSet paramAttributeSet){
		super(paramContext, paramAttributeSet);
		this.context = paramContext;
	}

	public EmojiTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt){
		super(paramContext, paramAttributeSet, paramInt);
		this.context = paramContext;
	}
	
	public void appendHtml(String paramString){
		append(Html.fromHtml(paramString, this.emojiGetter, null));
	}

	public void setEmojiText(String paramString){
		setText(Html.fromHtml(paramString, this.emojiGetter, null));
	}
	class ImageGetter implements Html.ImageGetter{
		ImageGetter(EmojiTextView paramEmojiTextView){
		}
		public Drawable getDrawable(String source) {
			if(source==null||source==""){
				return null;
			}
			if(!source.startsWith("http")){
				source=BabyConstants.URL_BASE+source;
			}
			Bitmap bit=TextViewImageUtils.getBitmapFromCache(source);
			if(bit==null){
				 Drawable drawable=null;
				 drawable=getResources().getDrawable(R.drawable.welcome);
				 drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				return drawable;
			}else{
				return new BitmapDrawable(bit);
			}
		}
	}
	
  
}