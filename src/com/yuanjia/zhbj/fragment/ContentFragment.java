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
		rg_group.check(R.id.rb_home);//Ĭ�Ϲ�ѡ��ҳ
		
		mArrayList = new ArrayList<BasePager>();
		
		mArrayList.add(new HomePager(mActivity));//��pager���뵽������
		mArrayList.add(new NewsCenterPager(mActivity));
		mArrayList.add(new SmartServicePager(mActivity));
		mArrayList.add(new GovAffairsPager(mActivity));
		mArrayList.add(new SettingPager(mActivity));
		
		mViewPager.setAdapter(new MyContentAdapter());
		
		//����RadioGroup��ѡ���¼�
		rg_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					mViewPager.setCurrentItem(0, false); //ȥ�������л���Ч��
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
				mArrayList.get(arg0).initData();// ��ȡ��ǰ��ѡ�е�ҳ��, ��ʼ����ҳ������
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		
		
//		mArrayList.get(0).initData();  //��ʼ������    ��������Ҫ---------------------------------------------------------------------------
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
			mArrayList.get(0).initData();//��ʼ������----------------------------------------------------------
		
			return mArrayList.get(position).mRootView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);           
		}
		
	}
	
	//��ȡ��������ҳ��
	
	public NewsCenterPager getNewsCenterPager(){
		return (NewsCenterPager) mArrayList.get(1);
	}
}
