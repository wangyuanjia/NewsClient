package com.yuanjia.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 11个子页签水平滑动的viewpager，暂时不要
 * @author Administrator
 *
 */
public class horizontalViewPager extends ViewPager {

	public horizontalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public horizontalViewPager(Context context) {
		super(context);
	}
	
//	// 表示事件是否拦截, 返回false表示不拦截
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		return false;
//	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(getCurrentItem()!=0){
			getParent().requestDisallowInterceptTouchEvent(true); 
		}else{
			getParent().requestDisallowInterceptTouchEvent(false); //如果是第一个页面，需要显示侧边栏
		}
		
		return super.dispatchTouchEvent(ev);
	}
}
