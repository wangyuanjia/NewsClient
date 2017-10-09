package com.yuanjia.zhbj.base;

import android.app.Activity;
import android.view.View;

/**
 * �˵�����ҳ����
 * @author Administrator
 *
 */
public abstract class BaseMenuDetailPager {

	public Activity mActivity;
	public View mRootView;  //�����ֶ���
	
	public BaseMenuDetailPager(Activity activity){
		mActivity = activity;
		mRootView = initView();
	}
	
	//��ʼ������
	public abstract View initView();
	
	//��ʼ������
	public void initData(){
		
	}
}
