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
		ivPic.setImageResource(R.drawable.news_pic_default);//设置默认图片

		//1.内存缓存
		Bitmap mMomeryBitmap = mMomeryCacheUtils.getBitmapFromMomery(url);
		if(mMomeryBitmap != null){
			ivPic.setImageBitmap(mMomeryBitmap);
			 System.out.println("从内存读取图片.....");	
			return;
		}
		
		//2.本地缓存
		Bitmap bitmap = mLocalCacheUtils.getBitmapFromlocal(url);
		if(bitmap != null){
			ivPic.setImageBitmap(bitmap);
            System.out.println("从本地读取图片.....");	
            mMomeryCacheUtils.setBitmapToMomery(url, bitmap);
            return;
		}
		
		//3.网络缓存	
		
		mNetCacheUtils.getBitmapFromNet(ivPic, url);
	}
}
