package com.yuanjia.zhbj.utils;

import android.content.Context;

/**
 * 缓存工具类
 * @author Administrator
 *
 */
public class CacheUtils {

	/**
	 * 设置缓存key 是url,value 是json
	 * @param key
	 * @param value
	 * @param cxt
	 */
	public static void setCache(Context cxt,String key,String value){
		SharedPreUtils.setString(cxt, key, value);
	}
	
	
	/**
	 * 获取缓存key 是url
	 * @param cxt
	 * @param key
	 * @return
	 */
	public static String getCache(Context cxt,String key){
		
		return SharedPreUtils.getString(cxt, key, null);
	}
}
