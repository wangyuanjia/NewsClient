package com.yuanjia.zhbj;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.yuanjia.zhbj.fragment.ContentFragment;
import com.yuanjia.zhbj.fragment.LeftMenuFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class HomeActivity extends SlidingFragmentActivity {

	public static final String FRAGMENT_CONTENT = "fragment_content";
	private static final String FRAGMENT_LEFT_MENU = "fragment_left_menu";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		setBehindContentView(R.layout.left_menu);//设置左侧边栏
		SlidingMenu slidingMenu = getSlidingMenu();	
	
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//设置全屏滑动
//		slidingMenu.setSecondaryMenu(R.layout.right_menu);//设置右侧边栏
	
//		slidingMenu.setMode(SlidingMenu.LEFT_RIGHT);
		slidingMenu.setBehindOffset(500);
        
		initFragment();
	}

	/**
	 * 初始化fragment，将fragment数据填充给布局文件
	 */
	private void initFragment() {
		
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.fl_content, new ContentFragment(), FRAGMENT_CONTENT);
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), FRAGMENT_LEFT_MENU);
		
		transaction.commit();
		
	}
	
	//获取侧边栏fragment
	public LeftMenuFragment getLeftMenuFragment(){
		FragmentManager fm = getSupportFragmentManager();
		LeftMenuFragment fragmet = (LeftMenuFragment) fm.findFragmentByTag(FRAGMENT_LEFT_MENU);
		return fragmet;
		
	}
	
	//获取主页面的fragment
	public ContentFragment getContentFragment(){
		FragmentManager fm = getSupportFragmentManager();
		ContentFragment fragmet = (ContentFragment) fm.findFragmentByTag(FRAGMENT_CONTENT);
		return fragmet;
		
	}
}


