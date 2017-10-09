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
 * �˵�����ҳ��������
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
		// text.setText("�˵�����ҳ��������");
		// text.setTextColor(Color.RED);
		// text.setTextSize(25);
		// text.setGravity(Gravity.CENTER);
		// return text;
		View view = View.inflate(mActivity, R.layout.news_menu_detail, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_menu_detail);
		ViewUtils.inject(this, view);

		// ��ʼ���Զ���ؼ�TabPageIndicator
		indicator = (TabPageIndicator) view.findViewById(R.id.indicator);
        indicator.setOnPageChangeListener(this);//��viewpager��indicator��ʱ�����������¼���Ҫ���ø�indicator������viewpager
		return view;
	}

	// ��ת��һ��ҳ��
	@OnClick(R.id.btn_next)
	public void nextPage(View view) {
		int currentItem = mViewPager.getCurrentItem();
		mViewPager.setCurrentItem(++currentItem);
	}

	@Override
	public void initData() {
		tabDetailPager = new ArrayList<TabDetailPager>();
		// ��ʼ��ҳǩ����
		for (int i = 0; i < mNewTabData.size(); i++) {
			TabDetailPager pager = new TabDetailPager(mActivity,
					mNewTabData.get(i));
			tabDetailPager.add(pager);
		}
 
		mViewPager.setAdapter(new MyTabDetailAdapter());

		indicator.setViewPager(mViewPager); // ��ViewPager��indicator��������
	}

	class MyTabDetailAdapter extends PagerAdapter {

		/**
		 * ��д�˷���������ҳ����⣬����viewpageIndicator��ҳǩ��ʾ
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
