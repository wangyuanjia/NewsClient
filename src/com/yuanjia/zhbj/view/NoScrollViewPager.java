package com.yuanjia.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * �������һ���viewpager
 * @author Administrator
 *
 */
public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public NoScrollViewPager(Context context) {
		super(context);
	}
	
	// ��ʾ�¼��Ƿ�����, ����false��ʾ������
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}
	
	
	//��дonTouchEvent�¼�����ʾʲô��������
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return false;
	}

	
}
