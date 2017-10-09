package com.yuanjia.zhbj;

import com.yuanjia.zhbj.utils.SharedPreUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

	private RelativeLayout relative;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		relative = (RelativeLayout) findViewById(R.id.rt_root);
		// 显示动画
		showAnimation();

	}

	private void showAnimation() {
		// 动画集合
		AnimationSet set = new AnimationSet(false);

		// 设置动画旋转
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(1200);
		rotate.setFillAfter(true);

		// 设置动画缩放
		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(1200);
		scale.setFillAfter(true);

		// 设置动画渐变
		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		alpha.setDuration(1200);
		alpha.setFillAfter(true);

		set.addAnimation(rotate);
		set.addAnimation(scale);
		set.addAnimation(alpha);

		set.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				
				showGuidePager();
			}

			private void showGuidePager() {
//				SharedPreferences sp = getSharedPreferences("config",MODE_PRIVATE );
//				Boolean isFalse = sp.getBoolean("isFirstShowGuide", false);
				Boolean isFalse = SharedPreUtils.getBoolean(SplashActivity.this, "isFirstShowGuide", false);
				if(!isFalse){
					Intent intent = new Intent(SplashActivity.this, GuideActivity.class);
					startActivity(intent);
					
				}else{
					Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
					startActivity(intent);
				}
				finish();
			}
		});

		relative.startAnimation(set);

	}

}
