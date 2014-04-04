package com.yangjun.baby.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

public class EditTextImage extends EditText {
	public EditTextImage(Context context) {
		super(context);
	}
	public EditTextImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	public void insertDrawable(String url,Drawable d) {
		String imgStr="<p><img src='"+url+"' /></p>";
		final SpannableString ss = new SpannableString(imgStr);
		//得到drawable对象，即所要插入的图片
		d.setBounds(0, this.getLineCount()*this.getLineHeight(), this.getWidth(), d.getIntrinsicHeight());
		//用这个drawable对象代替字符串easy
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
		//包括0但是不包括"easy".length()即：4。[0,4)。值得注意的是当我们复制这个图片的时候，实际是复制了"easy"这个字符串。
		ss.setSpan(span, 0, imgStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		append(ss);
	}
}