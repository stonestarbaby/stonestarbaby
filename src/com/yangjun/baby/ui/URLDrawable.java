package com.yangjun.baby.ui;

import com.yangjun.baby.R;
import com.yangjun.baby.util.ImageCacheUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class URLDrawable extends BitmapDrawable {
    protected Drawable drawable;
                   
    public URLDrawable(Context context,int top) {
        drawable = context.getResources().getDrawable(R.drawable.image_post_default);
        drawable.setBounds(ImageCacheUtils.getDefaultImageBounds(context,top));
    }
                   
    @Override
    public void draw(Canvas canvas) {
        Log.d("test", "this=" + this.getBounds());
        if (drawable != null) {
            Log.d("test", "draw=" + drawable.getBounds());
            drawable.draw(canvas);
        }
    }
                   
}