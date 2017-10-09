package com.yuanjia.zhbj.utils.bitmap;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * 网络缓存
 * 
 * @author Administrator
 * 
 */
public class NetCacheUtils {

	private HttpURLConnection conn = null;
    private LocalCacheUtils mLocalCacheUtils;
    private MomeryCacheUtils mMomeryCacheUtils; 
	public NetCacheUtils(LocalCacheUtils localCacheUtils, MomeryCacheUtils momeryCacheUtils) {
		mLocalCacheUtils = localCacheUtils;
		mMomeryCacheUtils = momeryCacheUtils;
	}

	/**
	 * 从网络下载图片
	 * 
	 * @param ivPic
	 * @param url
	 */
	public void getBitmapFromNet(ImageView ivPic, String url) {
		System.out.println("aaa");
		new BitmapTask().execute(ivPic, url);// 启动asyncTask,参数会在doInBackground中传递

	}

	/**
	 * handler和线程池的封装 1.第一个范型： 参数类型 2.第二个范型： 更新进度的范型 3.第三个范型： onPostExecute返回的结果
	 * 
	 * @author Administrator
	 * 
	 */
	class BitmapTask extends AsyncTask<Object, Void, Bitmap> {


		private ImageView ivPic;
		private String url;
		// 后台耗时方法在此执行，子线程
		@Override
		protected Bitmap doInBackground(Object... params) {
			ivPic = (ImageView) params[0];
			url = (String) params[1];
			ivPic.setTag(url);// 将url和ivPic绑定
			Bitmap bitmap = downloadBitmap(url);
			System.out.println("aaa");
			return bitmap; 
		}
 
		// 进度更新，主线程
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		// 耗时方法结束，调用此方法，主线程
		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				String bindUrl = (String) ivPic.getTag();
				
				if(url.equals(bindUrl)){//确保图片设定给了正确的imageView
					ivPic.setImageBitmap(result);
					mLocalCacheUtils.setBitmapTolocal(url, result);
					mMomeryCacheUtils.setBitmapToMomery(url, result);
					System.out.println("将图片保存在本地");
				}
			}
		}

	}

	private Bitmap downloadBitmap(String url) {
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);

			conn.setRequestMethod("GET");
			conn.connect();

			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				
//				Bitmap bitmap = BitmapFactory.decodeStream(is);
				
				//图片压缩处理
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inSampleSize = 2;
				option.inPreferredConfig = Bitmap.Config.RGB_565;
				
				
				Bitmap bitmap = BitmapFactory.decodeStream(is, null, option);
				return bitmap;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.disconnect();
		}
		return null;
	}
	

}
