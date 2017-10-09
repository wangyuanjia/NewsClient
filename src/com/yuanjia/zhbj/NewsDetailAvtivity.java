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
		settings.setJavaScriptEnabled(true);// ��ʾ֧��js
		settings.setBuiltInZoomControls(true);// ��ʾ�Ŵ���С
		settings.setUseWideViewPort(true);// ֧��˫������

		wv_veb.setWebViewClient(new WebViewClient() {

			/**
			 * ������ת�����Ӷ����ڴ˷����лص�
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);
				return true;
				// return super.shouldOverrideUrlLoading(view, url);

			}

			/**
			 * ��ҳ��ʼ����
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				System.out.println("��ʼ���ء�����");
				pb_progress1.setVisibility(View.VISIBLE);
			}

			/**
			 * ��ҳ���ؽ���
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				System.out.println("���ؽ���������");
				pb_progress1.setVisibility(View.GONE);
			}

		});

		wv_veb.loadUrl(url.replace("10.0.2.2", "192.168.1.108").replace(
				"/zhbj", ""));// ������ҳ

		wv_veb.goBack();
		// wv_veb.loadUrl("http://www.baidu.com");
		wv_veb.setWebChromeClient(new WebChromeClient() {

			/**
			 * ���ȷ����仯
			 */
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				System.out.println("���ؽ��ȣ�" + newProgress);
			}

			/**
			 * ��ȡ��ҳ����
			 */
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				System.out.println("��ҳ���⣺" + title);
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



	private int mCurrentChooseItem;// ��¼��ǰѡ�е�item,���֮ǰ
	private int mCurrentItem = 2;// ��¼��ǰѡ�е�item,���֮��
	/**
	 * ��ʾѡ��Ի���
	 */
	private void showChooseDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String[] items = new String[] { "���������", "�������", "��������", "С������",
				"��С������" };
		builder.setSingleChoiceItems(items, mCurrentItem,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.out.println("ѡ��:" + which);
						mCurrentChooseItem = which;
						mCurrentItem = mCurrentChooseItem;  //���浱ǰѡ�е�״̬
					}
				}

		);
		
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

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
		
		builder.setNegativeButton("ȡ��", null);
		builder.setTitle("��������");
        builder.show();
	}

	/**
	 * ��ʾ����
	 */
	private void showShare() {
		
	}
}
