package com.yangjun.baby.ui;

import com.yangjun.baby.R;
import com.yangjun.baby.util.TextViewImageUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class URLDrawable extends BitmapDrawable {
    protected Drawable drawable;
                   
    public URLDrawable(Context context) {
        this.setBounds(TextViewImageUtils.getDefaultImageBounds(context));
                       
        drawable = context.getResources().getDrawable(R.drawable.welcome);
        drawable.setBounds(TextViewImageUtils.getDefaultImageBounds(context));
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