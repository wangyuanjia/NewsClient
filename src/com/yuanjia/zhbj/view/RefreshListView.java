package com.yuanjia.zhbj.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnScrollStateChanged;
import com.yuanjia.zhbj.R;

import android.content.Context;
import android.os.SystemClock;
import android.renderscript.ProgramFragmentFixedFunction.Builder.Format;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
public class RefreshListView extends ListView implements OnScrollListener, android.widget.AdapterView.OnItemClickListener {

	private static final int START_PULL_REFRESH = 0;// 下拉刷新
	private static final int START_RELEASE_REFRESH = 1;// 松开刷新
	private static final int START_REFRESHING = 2;// 正在刷新

	private int mCurrentState = START_PULL_REFRESH;

	private int startY = -1;
	private int high;
	private int mFooterViewHeight;
	private View mHeaderView;
	private View mFooterView;

	private int padding;
	private RotateAnimation animUp;
	private RotateAnimation animDown;
	private RotateAnimation a;

	@ViewInject(R.id.iv_arrow)
	private ImageView iv_arrow;
	@ViewInject(R.id.pb_progress)
	private ProgressBar pb_progress;
	@ViewInject(R.id.tv_shua)
	private TextView tv_shua;
	@ViewInject(R.id.tv_time)
	private TextView tv_time;

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeaderView();
		initFooterView();
	}

	public RefreshListView(Context context) {
		super(context);
		initHeaderView();
		initFooterView();
	}

	/**
	 * 初始化头布局
	 */
	private void initHeaderView() {
		mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
		this.addHeaderView(mHeaderView);

		ViewUtils.inject(this, mHeaderView);
		// 隐藏头布局
		mHeaderView.measure(0, 0);
		high = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -high, 0, 0);
		
		initArrowAnim();
		tv_time.setText("最后刷新时间：" + getCurrentTime());
	}

	/**
	 * 初始化脚布局
	 */
	
	public void initFooterView(){
		mFooterView = View.inflate(getContext(), R.layout.refresh_listview_footer, null);
		this.addFooterView(mFooterView);
		mFooterView.measure(0, 0);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);//隐藏
		
		this.setOnScrollListener(this);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (startY == -1) { // 确保startY数据有效
				startY = (int) ev.getRawY();
			}

			if (mCurrentState == START_REFRESHING) {// 正在刷新时，不做处理理
				break;
			}

			int endY = (int) ev.getRawY();
			int dy = endY - startY;// 偏移量
			if (dy > 0 && getFirstVisiblePosition() == 0) { // 只有下拉且是第一个it么的时候，才允许下拉
				padding = dy - high;// 计算padding
				mHeaderView.setPadding(0, padding, 0, 0);// 设置当前padding

				if (padding > 0 && mCurrentState != START_RELEASE_REFRESH) {// 状态改为松开刷新
					mCurrentState = START_RELEASE_REFRESH;
					refreshState();

				} else if (padding < 0 && mCurrentState != START_PULL_REFRESH) {// 状态改为下拉刷新
					mCurrentState = START_PULL_REFRESH;
					refreshState();
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:

			if (padding > 0 && mCurrentState == START_RELEASE_REFRESH) {
				
				mHeaderView.setPadding(0, 0, 0, 0);// 显示
				mCurrentState = START_REFRESHING;// 正在刷新
				refreshState();

			} else if (padding < 0 && mCurrentState == START_PULL_REFRESH) {
				mHeaderView.setPadding(0, -high, 0, 0);// 隐藏
			}
			startY = -1;
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 刷新下拉控件的布局
	 */
	private void refreshState() {
		switch (mCurrentState) {
		case START_PULL_REFRESH:
			tv_shua.setText("下拉刷新");
			iv_arrow.setVisibility(View.VISIBLE);
			pb_progress.setVisibility(View.INVISIBLE);
			iv_arrow.startAnimation(animDown);
			break;
		case START_RELEASE_REFRESH:
			tv_shua.setText("松开刷新");
			iv_arrow.setVisibility(View.VISIBLE);
			pb_progress.setVisibility(View.INVISIBLE);
			iv_arrow.startAnimation(animUp);
			break;
		case START_REFRESHING:
			tv_shua.setText("正在刷新。。。");
			iv_arrow.setVisibility(View.INVISIBLE);
			pb_progress.setVisibility(View.VISIBLE);
//			SystemClock.sleep(2000);
			iv_arrow.clearAnimation();//清除动画才能隐藏
			if(mListener != null){
				mListener.onRefresh(); 
			}
		
			break;

		default:
			break;
		}
	}

	/**
	 * 初始化箭头动画
	 */
	public void initArrowAnim() {
		// 箭头向上动画
		animUp = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animUp.setDuration(200);
		animUp.setFillAfter(true);

		// 箭头向下动画
		animDown = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animDown.setDuration(200);
		animDown.setFillAfter(true);
		
//		a = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
//				Animation.RELATIVE_TO_SELF, 0.5f);
//		a.setDuration(2000);
	}

	/**
	 * 下来刷新监听
	 */
	onRefreshListener mListener;
	
	public void setOnRefreshListener(onRefreshListener listener){
		
		mListener = listener;
	}
	
	public interface onRefreshListener{
		public void onRefresh();
		public void onLoadMore();  //加载更多
	}
	
	/**
	 * 收起下拉刷新的控件
	 */
	public void onRefreshComplete(boolean success){
		if(isLoadingMore){
			mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
			isLoadingMore = false;
		}else{
			mCurrentState = START_PULL_REFRESH;
			tv_shua.setText("下拉刷新");
			iv_arrow.setVisibility(View.VISIBLE);
			pb_progress.setVisibility(View.INVISIBLE);
			mHeaderView.setPadding(0, -high, 0, 0);
			if(success){
				tv_time.setText("最后刷新时间：" + getCurrentTime());
			}
		}
	
	}
	
	/**
	 * 获取当前时间
	 */
	public String getCurrentTime(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Date());
		return time;
	}

	private boolean isLoadingMore = false;
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(scrollState ==SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING){
			if(getLastVisiblePosition() == getCount() - 1&&!isLoadingMore){//划到最后
				mFooterView.setPadding(0, 0, 0, 0);//显示
				System.out.println("到底了。。。。。");
				setSelection(getCount()-1);//改变listView的显示位置
				
				isLoadingMore = true;
				if(mListener != null){
					mListener.onLoadMore();
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}
	
	
	OnItemClickListener mClickListener;
    @Override
    public void setOnItemClickListener(
    		android.widget.AdapterView.OnItemClickListener listener) {
    	super.setOnItemClickListener(this);
    	mClickListener = listener;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mClickListener.onItemClick(parent, view, position-getHeaderViewsCount(), id);
	}
	

}
