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
 * ���绺��
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
	 * ����������ͼƬ
	 * 
	 * @param ivPic
	 * @param url
	 */
	public void getBitmapFromNet(ImageView ivPic, String url) {
		System.out.println("aaa");
		new BitmapTask().execute(ivPic, url);// ����asyncTask,��������doInBackground�д���

	}

	/**
	 * handler���̳߳صķ�װ 1.��һ�����ͣ� �������� 2.�ڶ������ͣ� ���½��ȵķ��� 3.���������ͣ� onPostExecute���صĽ��
	 * 
	 * @author Administrator
	 * 
	 */
	class BitmapTask extends AsyncTask<Object, Void, Bitmap> {


		private ImageView ivPic;
		private String url;
		// ��̨��ʱ�����ڴ�ִ�У����߳�
		@Override
		protected Bitmap doInBackground(Object... params) {
			ivPic = (ImageView) params[0];
			url = (String) params[1];
			ivPic.setTag(url);// ��url��ivPic��
			Bitmap bitmap = downloadBitmap(url);
			System.out.println("aaa");
			return bitmap; 
		}
 
		// ���ȸ��£����߳�
		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		// ��ʱ�������������ô˷��������߳�
		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				String bindUrl = (String) ivPic.getTag();
				
				if(url.equals(bindUrl)){//ȷ��ͼƬ�趨������ȷ��imageView
					ivPic.setImageBitmap(result);
					mLocalCacheUtils.setBitmapTolocal(url, result);
					mMomeryCacheUtils.setBitmapToMomery(url, result);
					System.out.println("��ͼƬ�����ڱ���");
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
				
				//ͼƬѹ������
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
