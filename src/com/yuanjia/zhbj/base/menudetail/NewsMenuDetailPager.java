package com.yuanjia.zhbj.base.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;
import com.yuanjia.zhbj.HomeActivity;
import com.yuanjia.zhbj.R;
import com.yuanjia.zhbj.base.BaseMenuDetailPager;
import com.yuanjia.zhbj.base.TabDetailPager;
import com.yuanjia.zhbj.domain.NewsData.NewsTabData;

/**
 * 菜单详情页——新闻
 * 
 * @author Administrator
 * 
 */
public class NewsMenuDetailPager extends BaseMenuDetailPager implements OnPageChangeListener  {

	private ViewPager mViewPager;
	private ArrayList<NewsTabData> mNewTabData;
	private ArrayList<TabDetailPager> tabDetailPager;
	private TabPageIndicator indicator;

	public NewsMenuDetailPager(Activity activity,
			ArrayList<NewsTabData> children) {
		super(activity);
		mNewTabData = children;
	}

	@Override
	public View initView() {
		// TextView text = new TextView(mActivity);
		// text.setText("菜单详情页——新闻");
		// text.setTextColor(Color.RED);
		// text.setTextSize(25);
		// text.setGravity(Gravity.CENTER);
		// return text;
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);
		ViewUtils.inject(this, view);

		// 初始化自定义控件TabPageIndicator
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        indicator.setOnPageChangeListener(this);//当viewpager和indicator绑定时，滑动监听事件需要设置给indicator而不是viewpager
		return view;
	}

	// 跳转下一个页面
	@OnClick(R.id.btn_next)
	public void nextPage(View view) {
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	@Override
	public void initData() {
		tabDetailPager = new ArrayList<TabDetailPager>();
		// 初始化页签数据
		for (int i = 0; i < mNewTabData.size(); i++) {
			TabDetailPager pager = new TabDetailPager(mActivity,
					mNewTabData.get(i));
			tabDetailPager.add(pager);
		}
 
		mViewPager.setAdapter(new MyTabDetailAdapter());

		indicator.setViewPager(mViewPager); // 将ViewPager和indicator关联起来
	}

	class MyTabDetailAdapter extends PagerAdapter {

		/**
		 * 重写此方法，返回页面标题，用于viewpageIndicator的页签显示
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			return mNewTabData.get(position).title;
		}

		@Override
		public int getCount() {
			return tabDetailPager.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(tabDetailPager.get(position).mRootView);
			tabDetailPager.get(position).initData();
			return tabDetailPager.get(position).mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		
	}

	@Override
	public void onPageSelected(int position) {
		System.out.println("onPageSelected:"+position);
		HomeActivity mainUi = (HomeActivity) mActivity;
		SlidingMenu slidingMenu = mainUi.getSlidingMenu();
		if(position==0){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	
	}
}
