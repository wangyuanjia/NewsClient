package com.yuanjia.zhbj.base.implement;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.yuanjia.zhbj.base.BasePager;

public class GovAffairsPager extends BasePager {

	public GovAffairsPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {

		System.out.println("初始化。。。。");
		
		tv_title.setText("人口管理"); //修改标题
//		btn_menu.setVisibility(View.GONE);//隐藏菜单按钮
		setSlidingMenuEnable(true); //隐藏侧边栏
		
		TextView text = new TextView(mActivity);
		text.setText("政府事件");
		text.setTextColor(Color.RED);
        text.setTextSize(25);
        text.setGravity(Gravity.CENTER);
        
        fl_content.addView(text);//向FragmentLayout中动态添加布局
      
	}

	

	
	
}
