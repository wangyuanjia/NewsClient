package com.yuanjia.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 11����ҳǩˮƽ������viewpager����ʱ��Ҫ
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
	
//	// ��ʾ�¼��Ƿ�����, ����false��ʾ������
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//		return false;
//	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(getCurrentItem()!=0){
			getParent().requestDisallowInterceptTouchEvent(true); 
		}else{
			getParent().requestDisallowInterceptTouchEvent(false); //����ǵ�һ��ҳ�棬��Ҫ��ʾ�����
		}
		
		return super.dispatchTouchEvent(ev);
	}
}
