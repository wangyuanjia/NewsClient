package com.yuanjia.zhbj.base.menudetail;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.widget.TextView;

import com.yuanjia.zhbj.R;
import com.yuanjia.zhbj.base.BaseMenuDetailPager;

/**
 * ²Ëµ¥ÏêÇéÒ³¡ª¡ª»¥¶¯
 * @author Administrator
 *
 */
public class InteractMenuDetailPager extends BaseMenuDetailPager{

	public InteractMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		TextView text = new TextView(mActivity);
		text.setText("²Ëµ¥ÏêÇéÒ³¡ª¡ª»¥¶¯");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);
		return text;
	}

}
