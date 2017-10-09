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
 * ���ػ���
 * 
 * @author Administrator
 * 
 */
public class LocalCacheUtils {

	public static final String PATH_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhbj_cache"; 
	/**
	 * �ӱ���sdcard��ͼƬ
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
	 * ��sdcardдͼƬ
	 */
	public void setBitmapTolocal(String url,Bitmap bitmap) {

		try {
			String fileName = MD5Encoder.encode(url);
			File file = new File(PATH_CACHE, fileName);
				
			File parentFile = file.getParentFile();
			if(!parentFile.exists()){//������ļ��в����ڣ����½�һ��
				
				parentFile.mkdirs();
			}
			
			bitmap.compress(CompressFormat.JPEG, 100, new FileOutputStream(file));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
