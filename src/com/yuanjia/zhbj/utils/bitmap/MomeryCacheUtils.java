package com.yuanjia.zhbj.utils.bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

public class MomeryCacheUtils {

//	public HashMap<String, SoftReference<Bitmap>> hashMap = new HashMap<String, SoftReference<Bitmap>>();
	
	private LruCache<String, Bitmap> mMemoryCache;
	
	
	public MomeryCacheUtils(){
		long maxMemory = Runtime.getRuntime().maxMemory()/8; //模拟器默认为16M
		
		mMemoryCache = new LruCache<String, Bitmap>((int) maxMemory) {
			
			@Override
			protected int sizeOf(String key, Bitmap value) {
 
				int byteCount = value.getByteCount();
//				int byteCount1 = value.getRowBytes()*value.getHeight();
				return byteCount;
			}
		};
	}
	
	
	/**
	 * 从内存中获取图片
	 * @param url
	 */
	public Bitmap getBitmapFromMomery(String url){
//		SoftReference<Bitmap> softBitmap = hashMap.get(url);
//		if(softBitmap != null){
//			Bitmap bitmap = softBitmap.get();
//			return bitmap;
//		}
	
		Bitmap lruCache = mMemoryCache.get(url);
		return lruCache;
		
	}
	
	/**
	 * 把图片写到内存中
	 * @param url
	 * @param bitmap
	 */
	public void setBitmapToMomery(String url,Bitmap bitmap){
//		SoftReference<Bitmap> softBitmap = new SoftReference<Bitmap>(bitmap);
//		hashMap.put(url, softBitmap);
		mMemoryCache.put(url, bitmap);
	}
}
