package com.yuanjia.zhbj.base.menudetail;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.yuanjia.zhbj.R;
import com.yuanjia.zhbj.base.BaseMenuDetailPager;
import com.yuanjia.zhbj.domain.PhotosData;
import com.yuanjia.zhbj.domain.PhotosData.PhotoInfo;
import com.yuanjia.zhbj.global.GlobalContants;
import com.yuanjia.zhbj.utils.CacheUtils;
import com.yuanjia.zhbj.utils.bitmap.MyBitmapUtils;

/**
 * 菜单详情页——组图
 * @author Administrator
 *
 */
public class PhotoMenuDetailPager extends BaseMenuDetailPager{

	private ListView lv_photo;
	private GridView gv_photo;
	private MyPhotoAdapter mAdapter;
	private ArrayList<PhotoInfo> mPhotoList;
	private ImageButton btnPhoto;
	
	public PhotoMenuDetailPager(Activity activity,ImageButton btnPhoto) {
		super(activity);
		this.btnPhoto = btnPhoto;
		btnPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				changeDisplay();
			}
		});
	}

	private boolean isListView = true;
	protected void changeDisplay() {
		if(isListView){
			gv_photo.setVisibility(View.VISIBLE);
			lv_photo.setVisibility(View.INVISIBLE);
			btnPhoto.setImageResource(R.drawable.icon_pic_list_type);
			isListView = false;
		}else{
			gv_photo.setVisibility(View.INVISIBLE);
			lv_photo.setVisibility(View.VISIBLE);
			btnPhoto.setImageResource(R.drawable.icon_pic_grid_type);
			isListView = true;
		}
	}

	@Override
	public View initView() {
//		TextView text = new TextView(mActivity);
//		text.setText("菜单详情页——组图");
//		text.setTextColor(Color.RED);
//		text.setTextSize(25);
//		text.setGravity(Gravity.CENTER);
		View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);
		lv_photo = (ListView) view.findViewById(R.id.lv_photo);
		gv_photo = (GridView) view.findViewById(R.id.gv_photo);
		
		
		return view;
	}

	@Override
	public void initData() {
		super.initData();
		
		String cache = CacheUtils.getCache(mActivity, GlobalContants.PHOTOS_URL);
		if(!TextUtils.isEmpty(cache)){
			parseData(cache);
		}
		
		getDataFromServer();
	}
	
	 	/**
	 	 * 从服务器获取数据
	 	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalContants.PHOTOS_URL, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = (String) responseInfo.result;
				System.out.println("返回结果："+result);
				if(result != null){
					parseData(result);
					
					//设置缓存
					CacheUtils.setCache(mActivity, GlobalContants.PHOTOS_URL, result);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				
			}
		});
	}

/**
 * 解析数据
 * @param result
 */
	protected void parseData(String result) {
           Gson gson = new Gson();		
           PhotosData data = gson.fromJson(result, PhotosData.class);
           mPhotoList = data.data.news;
           if(mPhotoList != null){
        	   mAdapter = new MyPhotoAdapter();
        	   lv_photo.setAdapter(mAdapter);
        	   gv_photo.setAdapter(mAdapter);
           }
           
	}


	/**
	 * 设置适配器
	 * @author Administrator
	 *
	 */
	class MyPhotoAdapter extends BaseAdapter{

//		private BitmapUtils utils;
		MyBitmapUtils utils ;
		public MyPhotoAdapter(){
//			 utils = new BitmapUtils(mActivity);
//			 utils.configDefaultLoadingImage(R.drawable.news_pic_default);
			
			
			utils = new MyBitmapUtils();
		}
		
		@Override
		public int getCount() {
			return mPhotoList.size();
		}

		@Override
		public PhotoInfo getItem(int position) {
			return mPhotoList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if(convertView == null){
				convertView = View.inflate(mActivity, R.layout.list_photo_item, null);
				holder.tv_photo_title = (TextView) convertView.findViewById(R.id.tv_photo_title);
				holder.iv_photo_pic = (ImageView) convertView.findViewById(R.id.iv_photo_pic);
			    convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
//			holder.iv_photo_pic.
			holder.tv_photo_title.setText(getItem(position).title);
			if(getItem(position).listimage != null){
				String url1 = getItem(position).listimage.replace("10.0.2.2:8080/zhbj", "192.168.1.108:8080");
			
				utils.display(holder.iv_photo_pic, url1);
			}
			return convertView;
		}
		
		
	}
	static class ViewHolder{
		public TextView tv_photo_title;
		public ImageView iv_photo_pic;
	}
}
