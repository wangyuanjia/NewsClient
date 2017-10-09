package com.yuanjia.zhbj.fragment;

import java.util.ArrayList;
import java.util.List;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yuanjia.zhbj.R;
import com.yuanjia.zhbj.base.BasePager;
import com.yuanjia.zhbj.base.implement.GovAffairsPager;
import com.yuanjia.zhbj.base.implement.HomePager;
import com.yuanjia.zhbj.base.implement.NewsCenterPager;
import com.yuanjia.zhbj.base.implement.SettingPager;
import com.yuanjia.zhbj.base.implement.SmartServicePager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContentFragment extends BaseFragment {

	@ViewInject(R.id.rg_group)
	private RadioGroup rg_group;
	@ViewInject(R.id.vp_content)
	private ViewPager mViewPager;
	private ArrayList<BasePager> mArrayList;
	@Override
	public View initView() {
		
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		ViewUtils.inject(this, view);
	
		return view;
	}
	
	@Override
	public void initData() {
		rg_group.check(R.id.rb_home);//默认勾选首页
		
		mArrayList = new ArrayList<BasePager>();
		
		mArrayList.add(new HomePager(mActivity));//将pager加入到数组中
		mArrayList.add(new NewsCenterPager(mActivity));
		mArrayList.add(new SmartServicePager(mActivity));
		mArrayList.add(new GovAffairsPager(mActivity));
		mArrayList.add(new SettingPager(mActivity));
		
		mViewPager.setAdapter(new MyContentAdapter());
		
		//监听RadioGroup的选择事件
		rg_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					mViewPager.setCurrentItem(0, false); //去掉动画切换的效果
					break;
				case R.id.rb_news:
					mViewPager.setCurrentItem(1, false);
					break;
				case R.id.rb_smart:
					mViewPager.setCurrentItem(2, false);
					break;
				case R.id.rb_gov:
					mViewPager.setCurrentItem(3, false);
					break;
				case R.id.rb_setting:
					mViewPager.setCurrentItem(4, false);
					break;

				default:
					break;
				}
			}
		});
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mArrayList.get(arg0).initData();// 获取当前被选中的页面, 初始化该页面数据
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		
		
//		mArrayList.get(0).initData();  //初始化布局    此至关重要---------------------------------------------------------------------------
	}

	public class MyContentAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return mArrayList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mArrayList.get(position).mRootView);
			mArrayList.get(0).initData();//初始化数据----------------------------------------------------------
		
			return mArrayList.get(position).mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);           
		}
		
	}
	
	//获取新闻中心页面
	
	public NewsCenterPager getNewsCenterPager(){
		return (NewsCenterPager) mArrayList.get(1);
	}
}
