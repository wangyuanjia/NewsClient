package com.yuanjia.zhbj.base;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.yuanjia.zhbj.HomeActivity;
import com.yuanjia.zhbj.R;

import android.app.Activity;
import android.net.MailTo;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * ��ҳ��5����ҳ��Ļ���
 * @author Administrator
 *
 */
public class BasePager {

	public Activity mActivity;
	public View mRootView;      //���ֶ���
	public ImageButton btn_menu;//�˵���ť
	public FrameLayout fl_content;//����
	public TextView tv_title;     //�������
	public ImageButton btn_photo; //��ͼ�л�
	
	public BasePager(Activity activity){
		mActivity = activity;
		initViews();
	}

	/**
	 * ��ʼ������
	 */
	public void initViews(){
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);
		btn_menu = (ImageButton) mRootView.findViewById(R.id.btn_menu);
		fl_content = (FrameLayout) mRootView.findViewById(R.id.fl_content);
		tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
		btn_photo = (ImageButton) mRootView.findViewById(R.id.btn_photo);
		
		btn_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleSlidingMenu();
			}
		});
	}
	
	/**
	 * �л�slidingmenu��״̬
	 */
	protected void toggleSlidingMenu() {
		HomeActivity mainUi = (HomeActivity) mActivity;
			SlidingMenu slidingMenu = mainUi.getSlidingMenu();
			slidingMenu.toggle();//�л�״̬����ʾʱ���أ�����ʱ��ʾ
	}

	/**
	 * ��ʼ������
	 */
	public void initData(){}
	
	/**
	 * ���ò���������͹ر�
	 * @param 
	 */
	public void setSlidingMenuEnable(boolean enable) {
		
		HomeActivity mainUi  = (HomeActivity)mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		
		if(enable){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
		
	}
	
}
