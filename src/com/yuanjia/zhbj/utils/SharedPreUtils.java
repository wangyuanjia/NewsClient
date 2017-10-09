package com.yuanjia.zhbj.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreUtils {

	public static Boolean getBoolean(Context context,String key, Boolean value){
		SharedPreferences sp = context.getSharedPreferences("config",Context.MODE_PRIVATE );
//		Boolean isFalse = sp.getBoolean("isFirstShowGuide", false);
		return sp.getBoolean(key, value);
	}
	
	public static void setBoolean(Context context,String key, Boolean value){
		SharedPreferences.Editor editor = context.getSharedPreferences("config", Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static String getString(Context context,String key, String value){
		SharedPreferences sp = context.getSharedPreferences("config",Context.MODE_PRIVATE );
//		Boolean isFalse = sp.getBoolean("isFirstShowGuide", false);
		return sp.getString(key, value);
	}
	
	public static void setString(Context context,String key, String value){
		SharedPreferences.Editor editor = context.getSharedPreferences("config", Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}
}
