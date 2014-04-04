package com.yangjun.baby.entity;

import com.yangjun.baby.MainActivity;

public class Infos {
	public static String USER_ID;
	public static String EXPERT_ID;
	public static String CHAT_INFO_ID;
	public static String USER_NICKNAME;
	public static String USER_HEADIMG;
	public static String EXPERT_NICKNAME;
	public static String EXPERT_HEADIMG;
	public static boolean ISLOGIN=false;
	public static User user;
	public static MainActivity main;
	public static void clear(){
		user=null;
		USER_ID=null;
		EXPERT_ID=null;
	}
}
