package com.yuanjia.zhbj.base.implement;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;




import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.yuanjia.zhbj.HomeActivity;
import com.yuanjia.zhbj.base.BaseMenuDetailPager;
import com.yuanjia.zhbj.base.BasePager;
import com.yuanjia.zhbj.base.menudetail.InteractMenuDetailPager;
import com.yuanjia.zhbj.base.menudetail.NewsMenuDetailPager;
import com.yuanjia.zhbj.base.menudetail.PhotoMenuDetailPager;
import com.yuanjia.zhbj.base.menudetail.TopicMenuDetailPager;
import com.yuanjia.zhbj.domain.NewsData;
import com.yuanjia.zhbj.domain.NewsData.NewsMenuData;
import com.yuanjia.zhbj.fragment.LeftMenuFragment;
import com.yuanjia.zhbj.global.GlobalContants;
import com.yuanjia.zhbj.utils.CacheUtils;

public class NewsCenterPager extends BasePager {

	private NewsData mNewsData;
	private ArrayList<BaseMenuDetailPager> mPagers;
	private NewsMenuData menuData;
	
	public NewsCenterPager(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {

		System.out.println("��ʼ����������");
		
//		tv_title.setText("�ǻ۱���"); //�޸ı���
//		btn_menu.setVisibility(View.GONE);//���ز˵���ť
		setSlidingMenuEnable(true); //���ز����
		
//		TextView text = new TextView(mActivity);
//		text.setText("��������");
//		text.setTextColor(Color.RED);
//        text.setTextSize(25);
//        text.setGravity(Gravity.CENTER);
//        
//        fl_content.addView(text);//��FragmentLayout�ж�̬��Ӳ���
      
		
		String cacheSliding = CacheUtils.getCache(mActivity, GlobalContants.CATEGORIES_URL);
		if(!TextUtils.isEmpty(cacheSliding)){
			parseData(cacheSliding);//���������ڣ�ֱ�Ӷ�ȡ���棬�����������
			
		}
		getDataFromServer();//������û�л��棬����ȡ���µ����ݣ���֤��������
	}

	/**
	 * �ӷ�������ȡ����
	 */
	private void getDataFromServer() {
		HttpUtils utils = new HttpUtils();
		utils.send(HttpMethod.GET, GlobalContants.CATEGORIES_URL, new RequestCallBack<String>() {

			private String result;

			//���ʳɹ�
			@Override
			public void onSuccess(ResponseInfo responseInfo) {
				result = (String) responseInfo.result;
				System.out.println("���ؽ����"+result);
				if(result != null){
					
					parseData(result);
					
					//���û���
					CacheUtils.setCache(mActivity, GlobalContants.CATEGORIES_URL, result);
				}
			}

			//����ʧ��
			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(mActivity, msg, 0).show();
				error.printStackTrace();
			}
		});
	}
	
    /**
     * ������������
     * @param result
     */
	protected void parseData(String result) {
		Gson gson = new Gson();
		mNewsData = gson.fromJson(result, NewsData.class);
		System.out.println("���������"+mNewsData);
		
		//ˢ�²����������
		HomeActivity mainUi = (HomeActivity)mActivity;
		LeftMenuFragment leftMenuFragment = mainUi.getLeftMenuFragment();
		leftMenuFragment.setMenuData(mNewsData);
		
		//׼���ĸ��˵�����ҳ
		mPagers = new ArrayList<BaseMenuDetailPager>();
		mPagers.add(new NewsMenuDetailPager(mActivity,mNewsData.data.get(0).children));
		mPagers.add(new TopicMenuDetailPager(mActivity));
		mPagers.add(new PhotoMenuDetailPager(mActivity,btn_photo));
		mPagers.add(new InteractMenuDetailPager(mActivity));
		
		setCurrentMenuDetailPager(0);  //�������������ҳ�����ò˵�����ҳΪ��һ��
	}

	//���õ�ǰ�˵�����ҳ
	public void setCurrentMenuDetailPager(int position) {
		BaseMenuDetailPager paper = mPagers.get(position);//��ȡ��ǰҪ��ʾ�Ĳ˵�����ҳ
		fl_content.removeAllViews();//����֮ǰ�Ĳ���
		fl_content.addView(paper.mRootView);//���˵�����ҳ�Ĳ������ø�֡����
			
		//���õ�ǰҳ�ı���
		menuData = mNewsData.data.get(position);
		tv_title.setText(menuData.title);
		
		//��ʼ����ǰҳ�������
		paper.initData();
	}

	

	
	
}
