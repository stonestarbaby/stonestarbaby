package com.yangjun.baby.adapter;

import java.util.List;    

import com.yangjun.baby.R;
import com.yangjun.baby.chat.WeChatMessage;
import com.yangjun.baby.entity.ChatInfo;

import android.content.Context;  
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.TextView;  
  
public class WeChatAdapter extends BaseAdapter{  
      
    private Context mContext;  
    private List<WeChatMessage> mData;  
      
    public WeChatAdapter(Context context,List<WeChatMessage> data){  
        this.mContext=context;  
        this.mData=data;  
    }  
      
    public void Refresh() {  
       this.notifyDataSetChanged();  
    }  
      
    @Override  
    public int getCount(){  
        return mData.size();  
    }  
  
    @Override  
    public Object getItem(int Index){  
        return mData.get(Index);  
    }  
  
    @Override  
    public long getItemId(int Index){  
        return Index;  
    }  
  
    @Override  
    public View getView(int Index, View mView, ViewGroup mParent) {  
        TextView content; 
        TextView nickname;
            switch(mData.get(Index).getType()){  
               case WeChatMessage.MessageType_Time:  
                   mView=LayoutInflater.from(mContext).inflate(R.layout.chat_item_time, null);  
                   content=(TextView)mView.findViewById(R.id.chatTime);  
                   content.setText(mData.get(Index).getContent());  
                   break;  
               case WeChatMessage.MessageType_From:  
                   mView=LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_text_left, null);  
                   content=(TextView)mView.findViewById(R.id.chat_content_left);  
                   content.setText(mData.get(Index).getContent());
                   nickname=(TextView)mView.findViewById(R.id.tv_username);  
                   nickname.setText(ChatInfo.EXPERT_NICKNAME);  
                   break;  
               case WeChatMessage.MessageType_To:  
                   mView=LayoutInflater.from(mContext).inflate(R.layout.chatting_item_msg_text_right, null);  
                   content=(TextView)mView.findViewById(R.id.chat_content_right);  
                   content.setText(mData.get(Index).getContent());
                   nickname=(TextView)mView.findViewById(R.id.tv_username);  
                   nickname.setText(ChatInfo.USER_NICKNAME);
                   break;  
            }   
        return mView;  
    }  
  
}  