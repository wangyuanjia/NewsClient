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
 * 菜单详情页——话题
 * @author Administrator
 *
 */
public class TopicMenuDetailPager extends BaseMenuDetailPager{

	public TopicMenuDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		TextView text = new TextView(mActivity);
		text.setText("菜单详情页——话题");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);
		return text;
	}

}
