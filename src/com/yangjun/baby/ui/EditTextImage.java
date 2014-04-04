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
		//�õ�drawable���󣬼���Ҫ�����ͼƬ
		d.setBounds(0, this.getLineCount()*this.getLineHeight(), this.getWidth(), d.getIntrinsicHeight());
		//�����drawable��������ַ���easy
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
		//����0���ǲ�����"easy".length()����4��[0,4)��ֵ��ע����ǵ����Ǹ������ͼƬ��ʱ��ʵ���Ǹ�����"easy"����ַ�����
		ss.setSpan(span, 0, imgStr.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		append(ss);
	}
}