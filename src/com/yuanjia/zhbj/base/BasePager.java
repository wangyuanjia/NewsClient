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
 * 主页下5个子页面的基类
 * @author Administrator
 *
 */
public class BasePager {

	public Activity mActivity;
	public View mRootView;      //布局对象
	public ImageButton btn_menu;//菜单按钮
	public FrameLayout fl_content;//内容
	public TextView tv_title;     //标题对象
	public ImageButton btn_photo; //组图切换
	
	public BasePager(Activity activity){
		mActivity = activity;
		initViews();
	}

	/**
	 * 初始化布局
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
	 * 切换slidingmenu的状态
	 */
	protected void toggleSlidingMenu() {
		HomeActivity mainUi = (HomeActivity) mActivity;
			SlidingMenu slidingMenu = mainUi.getSlidingMenu();
			slidingMenu.toggle();//切换状态，显示时隐藏，隐藏时显示
	}

	/**
	 * 初始化数据
	 */
	public void initData(){}
	
	/**
	 * 设置侧边栏开启和关闭
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
