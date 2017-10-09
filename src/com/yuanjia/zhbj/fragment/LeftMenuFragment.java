package com.yuanjia.zhbj.fragment;

import java.util.ArrayList;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.yuanjia.zhbj.HomeActivity;
import com.yuanjia.zhbj.R;
import com.yuanjia.zhbj.base.implement.NewsCenterPager;
import com.yuanjia.zhbj.domain.NewsData;
import com.yuanjia.zhbj.domain.NewsData.NewsMenuData;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LeftMenuFragment extends BaseFragment {

	private ArrayList<NewsMenuData> mMenuList;
	@ViewInject(R.id.lv_list)
	private ListView lv_list;
	
	private MenuAdapter mAdapter;
	private int mCurrentPos;
	
	@Override
	public View initView() {
		
		View view = View.inflate(mActivity, R.layout.fragment_left, null);
		ViewUtils.inject(this, view);
		return view;
	}

	
	public void initData(){
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentPos = position;
				mAdapter.notifyDataSetChanged();
				setCurrentMenuDetailPager1(position);
				
			}
		});
		
	}
	
	/**
	 * 设置当前菜单详情页
	 * @param position
	 */
	protected void setCurrentMenuDetailPager1(int position) {
		HomeActivity mainUi = (HomeActivity)mActivity;
		ContentFragment contentFragment = mainUi.getContentFragment();
		NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();
		newsCenterPager.setCurrentMenuDetailPager(position);
	}


	//设置网络数据
	public void setMenuData(NewsData newsData){
	    mMenuList = newsData.data;
	    mAdapter = new MenuAdapter();
		
	    lv_list.setAdapter(mAdapter);
	}
	
	class MenuAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mMenuList.size();
		}

		@Override
		public NewsMenuData getItem(int position) {
			return mMenuList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.list_menu_item, null);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			tv_title.setText(getItem(position).title);
			
			if(mCurrentPos == position){//判断当前的view是否别选中
				//显示红色
				tv_title.setEnabled(true);	
			}else{
				//显示白色	
				tv_title.setEnabled(false);
			}
			
			return view;
		}
		
	}
}
