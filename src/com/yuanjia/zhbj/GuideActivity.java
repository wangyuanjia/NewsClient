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
	 * 初始化界面
	 */
	private void iniViews(){
		imageViewList = new ArrayList<ImageView>();
		//初始化引导页的三个页面
		for (int i=0;i<imageIds.length;i++) {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundResource(imageIds[i]);//设置引导背景
			imageViewList.add(imageView);
		}
		
		//初始化引导页的三个point
		for (int i=0;i<imageIds.length;i++) {
			View point = new View(this);
			point.setBackgroundResource(R.drawable.sharp_point_gray);//设置引导point
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(30,30);
			
			if(i>0){
				params.leftMargin = 30;
			}
			point.setLayoutParams(params);
			llGroupPoint.addView(point);
		}
		

		//布局绘制完获取圆点间的距离
		llGroupPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				System.out.println("layout 结束！");
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
		
		//初始化布局item 
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

		//滑动事件
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			System.out.println("位置："+position+"百分百比："+positionOffset+"移动的像素："+positionOffsetPixels);
			int len = (int) (mPointWidth*positionOffset + position*mPointWidth);
			//获取当前红点的布局参数
			LinearLayout.LayoutParams redPointParam = (LinearLayout.LayoutParams) viewRedPoint.getLayoutParams();
			
			redPointParam.leftMargin = len;//获取左边距
	
			viewRedPoint.setLayoutParams(redPointParam);//重新给小红点设置布局参数
//			// 设置动画渐变
//			AlphaAnimation alpha = new AlphaAnimation(0, positionOffset);
//			alpha.setDuration(3200);
//			btnStart.startAnimation(alpha);
			
		}
     
		//某个页面被选中
		@Override
		public void onPageSelected(int position) {
			if(position == imageIds.length - 1){
				btnStart.setVisibility(View.VISIBLE);
			}else{
                btnStart.setVisibility(View.INVISIBLE);				
			}
		}

		//滑动状态发生变化
		@Override
		public void onPageScrollStateChanged(int state) {
			
		}

			
		
	}
}
