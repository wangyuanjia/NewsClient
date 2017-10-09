package com.yuanjia.zhbj.base.implement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yuanjia.zhbj.base.BasePager;

public class SettingPager extends BasePager {

	public SettingPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {

		System.out.println("��ʼ����������");
		
		tv_title.setText("����"); //�޸ı���
		btn_menu.setVisibility(View.GONE);//���ز˵���ť
		setSlidingMenuEnable(false); //���ز����
		
		TextView text = new TextView(mActivity);
		text.setText("����");
		text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);
        
        fl_content.addView(text);//��FragmentLayout�ж�̬��Ӳ���
      
	}

	

	
	
}
