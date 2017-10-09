package com.yuanjia.zhbj.base;

import java.io.UTFDataFormatException;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
import com.yuanjia.zhbj.NewsDetailAvtivity;
import com.yuanjia.zhbj.R;
import com.yuanjia.zhbj.domain.NewsData.NewsTabData;
import com.yuanjia.zhbj.domain.TabData;
import com.yuanjia.zhbj.domain.TabData.TabDetail;
import com.yuanjia.zhbj.domain.TabData.TabNewsData;
import com.yuanjia.zhbj.domain.TabData.TopNewsData;
import com.yuanjia.zhbj.global.GlobalContants;
import com.yuanjia.zhbj.utils.CacheUtils;
import com.yuanjia.zhbj.utils.SharedPreUtils;
import com.yuanjia.zhbj.utils.bitmap.MyBitmapUtils;
import com.yuanjia.zhbj.view.RefreshListView;
import com.yuanjia.zhbj.view.RefreshListView.onRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ҳǩ����ҳ
 * 
 * @author Administrator
 * 
 */
public class TabDetailPager extends BaseMenuDetailPager implements
		OnPageChangeListener {

	private NewsTabData mTabData;
	private TextView text;
	private String mUrl;
	private TabData mTabDetailData;
	@ViewInject(R.id.vp_news)
	private ViewPager mViewPager;
	@ViewInject(R.id.tv_title)
	private TextView tv_title;// ͷ�����ŵı���
	private ArrayList<TopNewsData> mTopNewsList;
	// private CirclePageIndicator indicator_topnews;
	@ViewInject(R.id.lv_list)
	private RefreshListView lv_list;
	private ArrayList<TabNewsData> mNewsList;
//	private BitmapUtils utils;
	@ViewInject(R.id.indicator_topnews)
	private CirclePageIndicator indicator_topnews;
	// @ViewInject(R.id.rl_topnews)
	// private RelativeLayout rl_topnews;
	private String mMoreUrl;
	private NewsAdapter mNewsAdapter;
	private Handler mHandler;
	private MyBitmapUtils utils;

	public TabDetailPager(Activity activity, NewsTabData mNewsTabData) {
		super(activity);
		mTabData = mNewsTabData;
		mUrl = GlobalContants.SERVER_URL + mTabData.url;

	}

	@Override
	public View initView() {

		// text = new TextView(mActivity);
		// text.setText("ҳǩ����ҳ");
		// text.setTextColor(Color.RED);
		// text.setTextSize(25);
		// text.setGravity(Gravity.CENTER);

		View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);

		View headerView = View.inflate(mActivity, R.layout.list_header_topnews,
				null);
		ViewUtils.inject(this, view);
		ViewUtils.inject(this, headerView);
		// mViewPager.setOnPageChangeListener(this);
		// indicator_topnews = (CirclePageIndicator)
		// view.findViewById(R.id.indicator_topnews);
		// ��ͷ��������ͷ���ֵ���ʽ���ظ�listview
		lv_list.addHeaderView(headerView);
		// lv_list.addHeaderView(rl_topnews);

		lv_list.setOnRefreshListener(new onRefreshListener() {

			@Override
			public void onRefresh() {

				getDataFromServer();
			}

			@Override
			public void onLoadMore() {
				if (mMoreUrl != null) {
					getMoreDataFromServer();

				} else {
					Toast.makeText(mActivity, "û��������", 1).show();// fuck
					lv_list.onRefreshComplete(false); // ���Ų�������
				}
			}
		});

		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("�������" + position);

				// 245,232,567,756,���ؼ�¼�Ѷ�״̬
				String mIds = SharedPreUtils.getString(mActivity, "read_ids",
						"");
				String newsId = mNewsList.get(position).id;
				if (!mIds.contains(newsId)) {
					mIds = mIds + newsId + ",";
					SharedPreUtils.setString(mActivity, "read_ids", mIds);
				}

				System.out.println("�������" + newsId);
				// mNewsAdapter.notifyDataSetChanged();
				changeTextColor(view); // ʵ�־ֲ�����ˢ�� �������Item

				// ��ת��������ҳ
				Intent intent = new Intent(mActivity, NewsDetailAvtivity.class);
				intent.putExtra("url", mNewsList.get(position).url);
				mActivity.startActivity(intent);
			}
		});
		return view;
	}

	public void changeTextColor(View view) {
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setTextColor(Color.BLUE);
	}

	@Override
	public void initData() {

		String cache = CacheUtils.getCache(mActivity, mUrl);
		if (!TextUtils.isEmpty(cache)) {
			parseData(cache, false);
		}

		// text.setText(mTabData.title);
		getDataFromServer();

	}

	/**
	 * ��ȡ���������
	 */
	private void getMoreDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {

			private String result;

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				result = (String) responseInfo.result;
				System.out.println("moreData���ؽ����" + result);

				parseData(result, true);
				lv_list.onRefreshComplete(true);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, 0).show();
				error.printStackTrace();
				lv_list.onRefreshComplete(false);
			}
		});
	}

	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

			private String result;

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				result = (String) responseInfo.result;
				System.out.println("ҳǩ����ҳ���ؽ����" + result);

				parseData(result, false);
				CacheUtils.setCache(mActivity, mUrl, result);
				lv_list.onRefreshComplete(true);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, 0).show();
				error.printStackTrace();
				lv_list.onRefreshComplete(false);
			}
		});
	}

	/**
	 * ����tabdata����
	 * 
	 * @param result
	 */
	protected void parseData(String result, boolean isMore) {
		Gson gson = new Gson();
		mTabDetailData = gson.fromJson(result, TabData.class);
		System.out.println("ҳǩ���������" + mTabDetailData);

		// ������һҳ����
		String more = mTabDetailData.data.more;
		if (!TextUtils.isEmpty(more)) {
			mMoreUrl = GlobalContants.SERVER_URL + more;
			System.out.println(mMoreUrl);
		} else {
			mMoreUrl = null;
		}

		if (!isMore) {

			mTopNewsList = mTabDetailData.data.topnews;
			mNewsList = mTabDetailData.data.news;

			// mTabDetailData.data.news;
			if (mTopNewsList != null) {

				mViewPager.setAdapter(new TopNewsAdapter());
				tv_title.setText(mTopNewsList.get(0).title);

				indicator_topnews.setViewPager(mViewPager);
				indicator_topnews.setSnap(true);
				indicator_topnews.setOnPageChangeListener(this);

				indicator_topnews.onPageSelected(0);// ��ָʾ�����¶�λ����һ��
			}

			// ��listview�������
			if (mNewsList != null) {
				mNewsAdapter = new NewsAdapter();
				lv_list.setAdapter(mNewsAdapter);
			}

			// �Զ��ֲ�����ʾ
			if (mHandler == null) {
				mHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						int mCurrentItem = mViewPager.getCurrentItem();
						if (mCurrentItem < mTopNewsList.size() - 1) {
							mCurrentItem++;
						} else {
							mCurrentItem = 0;
						}
						mViewPager.setCurrentItem(mCurrentItem);
						mHandler.sendEmptyMessageDelayed(0, 3000);// �γ�ѭ��
					}
				};
				mHandler.sendEmptyMessageDelayed(0, 3000);// ��ʱ3�����Ϣ
			}

		} else {// ����Ǽ��ظ�������ݣ���Ҫ������׷�ӵ�ԭ���ļ���
			ArrayList<TabNewsData> mMoreNewsList = mTabDetailData.data.news;
			mNewsList.addAll(mMoreNewsList);
			mNewsAdapter.notifyDataSetChanged();
		}

	}

	/**
	 * ͷ������������
	 * 
	 * @author Administrator
	 */
	class TopNewsAdapter extends PagerAdapter {
		
		public TopNewsAdapter() {
//			utils = new BitmapUtils(mActivity);
//			utils.configDefaultLoadingImage(R.drawable.topnews_item_default);
			 utils = new MyBitmapUtils();
		}

		@Override
		public int getCount() {
			return mTabDetailData.data.topnews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView image = new ImageView(mActivity);
			// image.setImageResource(R.drawable.topnews_item_default);
			image.setScaleType(ScaleType.FIT_XY);// ��춿ؼ���С���DƬ
			TopNewsData mTopNewsData = mTopNewsList.get(position);
			utils.display(image,
					mTopNewsData.topimage.replace("10.0.2.2", "192.168.1.108")
							.replace("/zhbj", ""));// ����image�����ͼƬ��ַ
			container.addView(image);

			image.setOnTouchListener(new TopNewsTouchListener());
			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	class TopNewsTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
                 System.out.println("����");
				mHandler.removeCallbacksAndMessages(null);//ɾ��handle�е�������Ϣ
//				mHandler.postDelayed(new Runnable() {

				//					@Override
//					public void run() {
//					}
//				}, 3000);
				break;

			case MotionEvent.ACTION_UP:
                 System.out.println("̧��");
				mHandler.sendEmptyMessageDelayed(0, 3000);
				break;
				
			case MotionEvent.ACTION_CANCEL:
                System.out.println("������");
				mHandler.sendEmptyMessageDelayed(0, 3000);
				break;
			default:
				break;
			}
			return true;
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		tv_title.setText(mTopNewsList.get(position).title);
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	/**
	 * ����������
	 * 
	 * @author Administrator
	 * 
	 */
	class NewsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mNewsList.size();
		}

		@Override
		public TabNewsData getItem(int position) {
			return mNewsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder mViewHolder;
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.list_view_item,
						null);
				mViewHolder = new ViewHolder();
				convertView.setTag(mViewHolder);
				mViewHolder.iv_pic = (ImageView) convertView
						.findViewById(R.id.iv_pic);
				mViewHolder.tv_date = (TextView) convertView
						.findViewById(R.id.tv_date);
				mViewHolder.tv_title = (TextView) convertView
						.findViewById(R.id.tv_title);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}

			TabNewsData item = getItem(position);
			mViewHolder.tv_date.setText(item.pubdate);
			mViewHolder.tv_title.setText(item.title);

			String ids = SharedPreUtils.getString(mActivity, "read_ids", "");
			if (ids.contains(mNewsList.get(position).id)) {
				mViewHolder.tv_title.setTextColor(Color.BLUE);
			} else {
				mViewHolder.tv_title.setTextColor(Color.BLACK);
			}
			utils.display(mViewHolder.iv_pic,
					item.listimage.replace("10.0.2.2", "192.168.1.108")
							.replace("/zhbj", ""));
			return convertView;
		}

	}

	static class ViewHolder {
		public TextView tv_title;
		public ImageView iv_pic;
		public TextView tv_date;
	}

}
