package com.yuanjia.zhbj.utils;

import android.content.Context;

/**
 * ���湤����
 * @author Administrator
 *
 */
public class CacheUtils {

	/**
	 * ���û���key ��url,value ��json
	 * @param key
	 * @param value
	 * @param cxt
	 */
	public static void setCache(Context cxt,String key,String value){
		SharedPreUtils.setString(cxt, key, value);
	}
	
	
	/**
	 * ��ȡ����key ��url
	 * @param cxt
	 * @param key
	 * @return
	 */
	public static String getCache(Context cxt,String key){
		
		return SharedPreUtils.getString(cxt, key, null);
	}
}
