package com.yuanjia.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 11个子页签水平滑动的viewpager
 * 
 * @author Administrator
 * 
 */
public class TopNewsViewPager extends ViewPager {

	private int startX;
	private int startY;

	public TopNewsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TopNewsViewPager(Context context) {
		super(context);
	}

	// // 表示事件是否拦截, 返回false表示不拦截
	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// return false;
	// }

	/**
	 * 事件分发，请求父控件及祖宗事件是否拦截事件 1.右划，而且是第一个页面，需要拦截 2.左划，是最后一个页面，需要拦截 3.上下滑动，需要拦截
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		getParent().requestDisallowInterceptTouchEvent(true);

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);// 不要拦截，这样是为了保证ACTION_MOVE的调用

			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			int endX = (int) ev.getRawX();
			int endY = (int) ev.getRawY();
			
			if(Math.abs(endX-startX)>Math.abs(endY-startY)){//左右滑动
				if(endX-startX>0){//向右
					if(getCurrentItem()==0){//第一页
						getParent().requestDisallowInterceptTouchEvent(false);
					}
					
				}else{//向左
					if(getCurrentItem()==getAdapter().getCount()-1){//最后一页
						getParent().requestDisallowInterceptTouchEvent(false);
					}
				}
			}else{
				getParent().requestDisallowInterceptTouchEvent(false);
			}
				
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}
}
