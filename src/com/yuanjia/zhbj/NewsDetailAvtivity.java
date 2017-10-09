package com.yuanjia.zhbj;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;

public class NewsDetailAvtivity extends Activity implements OnClickListener {

	@ViewInject(R.id.ib_back)
	private ImageButton ib_back;
	@ViewInject(R.id.ib_menu)
	private ImageButton ib_menu;
	@ViewInject(R.id.ib_share)
	private ImageButton ib_share;
	@ViewInject(R.id.wv_veb)
	private WebView wv_veb;
	@ViewInject(R.id.pb_progress1)
	private ProgressBar pb_progress1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_news_detail);
		ViewUtils.inject(this);

		String url = getIntent().getStringExtra("url");
		WebSettings settings = wv_veb.getSettings();
		settings.setJavaScriptEnabled(true);// 表示支持js
		settings.setBuiltInZoomControls(true);// 显示放大缩小
		settings.setUseWideViewPort(true);// 支持双击缩放

		wv_veb.setWebViewClient(new WebViewClient() {

			/**
			 * 所有跳转的链接都会在此方法中回调
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);
				return true;
				// return super.shouldOverrideUrlLoading(view, url);

			}

			/**
			 * 网页开始加载
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				System.out.println("开始加载。。。");
				pb_progress1.setVisibility(View.VISIBLE);
			}

			/**
			 * 网页加载结束
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				System.out.println("加载结束。。。");
				pb_progress1.setVisibility(View.GONE);
			}

		});

		wv_veb.loadUrl(url.replace("10.0.2.2", "192.168.1.108").replace(
				"/zhbj", ""));// 加载网页

		wv_veb.goBack();
		// wv_veb.loadUrl("http://www.baidu.com");
		wv_veb.setWebChromeClient(new WebChromeClient() {

			/**
			 * 进度发生变化
			 */
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				System.out.println("加载进度：" + newProgress);
			}

			/**
			 * 获取网页标题
			 */
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				System.out.println("网页标题：" + title);
			}

		});
		ib_back.setOnClickListener(this);
		ib_share.setOnClickListener(this);
		ib_menu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_back:
			Intent intent = new Intent(NewsDetailAvtivity.this,
					HomeActivity.class);
			finish();
			break;

		case R.id.ib_share:
            showShare();
			break;

		case R.id.ib_menu:
			showChooseDialog();
			break;

		default:
			break;
		}
	}



	private int mCurrentChooseItem;// 记录当前选中的item,点击之前
	private int mCurrentItem = 2;// 记录当前选中的item,点击之后
	/**
	 * 显示选择对话框
	 */
	private void showChooseDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
				"超小号字体" };
		builder.setSingleChoiceItems(items, mCurrentItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("选中:" + which);
						mCurrentChooseItem = which;
						mCurrentItem = mCurrentChooseItem;  //保存当前选中的状态
					}
				}

		);
		
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				 WebSettings setting = wv_veb.getSettings();
				switch (mCurrentChooseItem) {
				case 0:
                   
                    setting.setTextSize(TextSize.LARGEST);
					break;
				case 1:
					   setting.setTextSize(TextSize.LARGER);
					break;
				case 2:
					   setting.setTextSize(TextSize.NORMAL);
					break;
				case 3:
					   setting.setTextSize(TextSize.SMALLER);
					break;
				case 4:
					   setting.setTextSize(TextSize.SMALLEST);
					break;
				default:
					break;
				}
				
			}
		});
		
		builder.setNegativeButton("取消", null);
		builder.setTitle("字体设置");
        builder.show();
	}

	/**
	 * 显示分享
	 */
	private void showShare() {
		
	}
}
