package com.yuanjia.zhbj.utils.bitmap;

import com.yuanjia.zhbj.R;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class MyBitmapUtils {

	
	private NetCacheUtils mNetCacheUtils = null ;
    private LocalCacheUtils mLocalCacheUtils; 
    private MomeryCacheUtils mMomeryCacheUtils;
	
	public MyBitmapUtils(){
		mMomeryCacheUtils = new MomeryCacheUtils();
		mLocalCacheUtils = new LocalCacheUtils();
		mNetCacheUtils = new NetCacheUtils(mLocalCacheUtils,mMomeryCacheUtils);
	}
	
	
	
	

	public void display(ImageView ivPic,String url){
		ivPic.setImageResource(R.drawable.news_pic_default);//����Ĭ��ͼƬ

		//1.�ڴ滺��
		Bitmap mMomeryBitmap = mMomeryCacheUtils.getBitmapFromMomery(url);
		if(mMomeryBitmap != null){
			ivPic.setImageBitmap(mMomeryBitmap);
			 System.out.println("���ڴ��ȡͼƬ.....");	
			return;
		}
		
		//2.���ػ���
		Bitmap bitmap = mLocalCacheUtils.getBitmapFromlocal(url);
		if(bitmap != null){
			ivPic.setImageBitmap(bitmap);
            System.out.println("�ӱ��ض�ȡͼƬ.....");	
            mMomeryCacheUtils.setBitmapToMomery(url, bitmap);
            return;
		}
		
		//3.���绺��	
		
		mNetCacheUtils.getBitmapFromNet(ivPic, url);
	}
}
