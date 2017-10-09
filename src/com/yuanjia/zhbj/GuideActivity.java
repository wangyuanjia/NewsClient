package com.yuanjia.zhbj;

import java.util.ArrayList;

import com.yuanjia.zhbj.utils.SharedPreUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class GuideActivity extends Activity {

	private ViewPager vpGuide;
	private static final int[] imageIds = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
	private ArrayList<ImageView>  imageViewList;
	private LinearLayout llGroupPoint;
	private int mPointWidth;
	private View viewRedPoint;
	private Button btnStart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		llGroupPoint = (LinearLayout) findViewById(R.id.ll_point_group);
		viewRedPoint = findViewById(R.id.view_red_point);
		btnStart = (Button) findViewById(R.id.btn_start);
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				SharedPreferences.Editor editor = getSharedPreferences("config", MODE_PRIVATE).edit();
//				editor.putBoolean("isFirstShowGuide", true);
//				editor.commit();
				SharedPreUtils.setBoolean(GuideActivity.this, "isFirstShowGuide", true);
				Intent intent = new Intent(GuideActivity.this, HomeActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		iniViews();
		vpGuide.setAdapter(new GuideAdapter());
		vpGuide.setOnPageChangeListener(new ViewPageListener());
	}
	
	/**
	 * ��ʼ������
	 */
	private void iniViews(){
		imageViewList = new ArrayList<ImageView>();
		//��ʼ������ҳ������ҳ��
		for (int i=0;i<imageIds.length;i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(imageIds[i]);//������������
			imageViewList.add(imageView);
		}
		
		//��ʼ������ҳ������point
		for (int i=0;i<imageIds.length;i++) {
			View point = new View(this);
			point.setBackgroundResource(R.drawable.sharp_point_gray);//��������point
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30,30);
			
			if(i>0){
				params.leftMargin = 30;
			}
			point.setLayoutParams(params);
			llGroupPoint.addView(point);
		}
		

		//���ֻ������ȡԲ���ľ���
		llGroupPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				System.out.println("layout ������");
				llGroupPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//				llGroupPoint.getViewTreeObserver().removeOnGlobalFocusChangeListener(this);
				mPointWidth = llGroupPoint.getChildAt(1).getLeft()-llGroupPoint.getChildAt(0).getLeft();
			}
		});
		
	}
	
	public class GuideAdapter extends PagerAdapter {
		
		@Override
		public int getCount() {
			return imageIds.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		//��ʼ������item 
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(imageViewList.get(position));
			return imageViewList.get(position);
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	public class ViewPageListener implements OnPageChangeListener{

		//�����¼�
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			System.out.println("λ�ã�"+position+"�ٷְٱȣ�"+positionOffset+"�ƶ������أ�"+positionOffsetPixels);
			int len = (int) (mPointWidth*positionOffset + position*mPointWidth);
			//��ȡ��ǰ���Ĳ��ֲ���
			LinearLayout.LayoutParams redPointParam = (LinearLayout.LayoutParams) viewRedPoint.getLayoutParams();
			
			redPointParam.leftMargin = len;//��ȡ��߾�
	
			viewRedPoint.setLayoutParams(redPointParam);//���¸�С������ò��ֲ���
//			// ���ö�������
//			AlphaAnimation alpha = new AlphaAnimation(0, positionOffset);
//			alpha.setDuration(3200);
//			btnStart.startAnimation(alpha);
			
		}
     
		//ĳ��ҳ�汻ѡ��
		@Override
		public void onPageSelected(int position) {
			if(position == imageIds.length - 1){
				btnStart.setVisibility(View.VISIBLE);
			}else{
                btnStart.setVisibility(View.INVISIBLE);				
			}
		}

		//����״̬�����仯
		@Override
		public void onPageScrollStateChanged(int state) {
			
		}

			
		
	}
}
