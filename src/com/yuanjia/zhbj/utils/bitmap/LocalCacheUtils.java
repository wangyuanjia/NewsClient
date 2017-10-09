package com.yuanjia.zhbj.utils.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.yuanjia.zhbj.utils.MD5Encoder;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

/**
 * 本地缓存
 * 
 * @author Administrator
 * 
 */
public class LocalCacheUtils {

	public static final String PATH_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhbj_cache"; 
	/**
	 * 从本地sdcard读图片
	 * @return 
	 */
	public Bitmap getBitmapFromlocal(String url) {
		String fileName;
		try {
			fileName = MD5Encoder.encode(url);
			File file = new File(PATH_CACHE, fileName);
			
			if(file.exists()){
				Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
				
				return bitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	
		
	}

	/**
	 * 像sdcard写图片
	 */
	public void setBitmapTolocal(String url,Bitmap bitmap) {

		try {
			String fileName = MD5Encoder.encode(url);
			File file = new File(PATH_CACHE, fileName);
				
			File parentFile = file.getParentFile();
			if(!parentFile.exists()){//如果父文件夹不存在，就新建一个
				
				parentFile.mkdirs();
			}
			
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(file));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
