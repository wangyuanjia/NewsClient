package com.yuanjia.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 11����ҳǩˮƽ������viewpager
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

	// // ��ʾ�¼��Ƿ�����, ����false��ʾ������
	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// return false;
	// }

	/**
	 * �¼��ַ������󸸿ؼ��������¼��Ƿ������¼� 1.�һ��������ǵ�һ��ҳ�棬��Ҫ���� 2.�󻮣������һ��ҳ�棬��Ҫ���� 3.���»�������Ҫ����
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		getParent().requestDisallowInterceptTouchEvent(true);

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			getParent().requestDisallowInterceptTouchEvent(true);// ��Ҫ���أ�������Ϊ�˱�֤ACTION_MOVE�ĵ���

			startX = (int) ev.getRawX();
			startY = (int) ev.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			int endX = (int) ev.getRawX();
			int endY = (int) ev.getRawY();
			
			if(Math.abs(endX-startX)>Math.abs(endY-startY)){//���һ���
				if(endX-startX>0){//����
					if(getCurrentItem()==0){//��һҳ
						getParent().requestDisallowInterceptTouchEvent(false);
					}
					
				}else{//����
					if(getCurrentItem()==getAdapter().getCount()-1){//���һҳ
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
