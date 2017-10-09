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

	private static final int START_PULL_REFRESH = 0;// ����ˢ��
	private static final int START_RELEASE_REFRESH = 1;// �ɿ�ˢ��
	private static final int START_REFRESHING = 2;// ����ˢ��

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
	 * ��ʼ��ͷ����
	 */
	private void initHeaderView() {
		mHeaderView = View.inflate(getContext(), R.layout.refresh_header, null);
		this.addHeaderView(mHeaderView);

		ViewUtils.inject(this, mHeaderView);
		// ����ͷ����
		mHeaderView.measure(0, 0);
		high = mHeaderView.getMeasuredHeight();
		mHeaderView.setPadding(0, -high, 0, 0);
		
		initArrowAnim();
		tv_time.setText("���ˢ��ʱ�䣺" + getCurrentTime());
	}

	/**
	 * ��ʼ���Ų���
	 */
	
	public void initFooterView(){
		mFooterView = View.inflate(getContext(), R.layout.refresh_listview_footer, null);
		this.addFooterView(mFooterView);
		mFooterView.measure(0, 0);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);//����
		
		this.setOnScrollListener(this);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (startY == -1) { // ȷ��startY������Ч
				startY = (int) ev.getRawY();
			}

			if (mCurrentState == START_REFRESHING) {// ����ˢ��ʱ������������
				break;
			}

			int endY = (int) ev.getRawY();
			int dy = endY - startY;// ƫ����
			if (dy > 0 && getFirstVisiblePosition() == 0) { // ֻ���������ǵ�һ��itô��ʱ�򣬲���������
				padding = dy - high;// ����padding
				mHeaderView.setPadding(0, padding, 0, 0);// ���õ�ǰpadding

				if (padding > 0 && mCurrentState != START_RELEASE_REFRESH) {// ״̬��Ϊ�ɿ�ˢ��
					mCurrentState = START_RELEASE_REFRESH;
					refreshState();

				} else if (padding < 0 && mCurrentState != START_PULL_REFRESH) {// ״̬��Ϊ����ˢ��
					mCurrentState = START_PULL_REFRESH;
					refreshState();
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:

			if (padding > 0 && mCurrentState == START_RELEASE_REFRESH) {
				
				mHeaderView.setPadding(0, 0, 0, 0);// ��ʾ
				mCurrentState = START_REFRESHING;// ����ˢ��
				refreshState();

			} else if (padding < 0 && mCurrentState == START_PULL_REFRESH) {
				mHeaderView.setPadding(0, -high, 0, 0);// ����
			}
			startY = -1;
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * ˢ�������ؼ��Ĳ���
	 */
	private void refreshState() {
		switch (mCurrentState) {
		case START_PULL_REFRESH:
			tv_shua.setText("����ˢ��");
			iv_arrow.setVisibility(View.VISIBLE);
			pb_progress.setVisibility(View.INVISIBLE);
			iv_arrow.startAnimation(animDown);
			break;
		case START_RELEASE_REFRESH:
			tv_shua.setText("�ɿ�ˢ��");
			iv_arrow.setVisibility(View.VISIBLE);
			pb_progress.setVisibility(View.INVISIBLE);
			iv_arrow.startAnimation(animUp);
			break;
		case START_REFRESHING:
			tv_shua.setText("����ˢ�¡�����");
			iv_arrow.setVisibility(View.INVISIBLE);
			pb_progress.setVisibility(View.VISIBLE);
//			SystemClock.sleep(2000);
			iv_arrow.clearAnimation();//���������������
			if(mListener != null){
				mListener.onRefresh(); 
			}
		
			break;

		default:
			break;
		}
	}

	/**
	 * ��ʼ����ͷ����
	 */
	public void initArrowAnim() {
		// ��ͷ���϶���
		animUp = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animUp.setDuration(200);
		animUp.setFillAfter(true);

		// ��ͷ���¶���
		animDown = new RotateAnimation(180, 0, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animDown.setDuration(200);
		animDown.setFillAfter(true);
		
//		a = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f,
//				Animation.RELATIVE_TO_SELF, 0.5f);
//		a.setDuration(2000);
	}

	/**
	 * ����ˢ�¼���
	 */
	onRefreshListener mListener;
	
	public void setOnRefreshListener(onRefreshListener listener){
		
		mListener = listener;
	}
	
	public interface onRefreshListener{
		public void onRefresh();
		public void onLoadMore();  //���ظ���
	}
	
	/**
	 * ��������ˢ�µĿؼ�
	 */
	public void onRefreshComplete(boolean success){
		if(isLoadingMore){
			mFooterView.setPadding(0, -mFooterViewHeight, 0, 0);
			isLoadingMore = false;
		}else{
			mCurrentState = START_PULL_REFRESH;
			tv_shua.setText("����ˢ��");
			iv_arrow.setVisibility(View.VISIBLE);
			pb_progress.setVisibility(View.INVISIBLE);
			mHeaderView.setPadding(0, -high, 0, 0);
			if(success){
				tv_time.setText("���ˢ��ʱ�䣺" + getCurrentTime());
			}
		}
	
	}
	
	/**
	 * ��ȡ��ǰʱ��
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
			if(getLastVisiblePosition() == getCount() - 1&&!isLoadingMore){//�������
				mFooterView.setPadding(0, 0, 0, 0);//��ʾ
				System.out.println("�����ˡ���������");
				setSelection(getCount()-1);//�ı�listView����ʾλ��
				
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
