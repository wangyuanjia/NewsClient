package com.yuanjia.zhbj.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * fragment ����
 * 
 * @author Administrator
 * 
 */
public abstract class BaseFragment extends Fragment {

	Activity mActivity;

	// fragment �Ĵ���
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
	}

	// ����fragment�Ĳ���
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return initView();
	}

	// ������activity�������
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	// �������ʵ�ֳ�ʼ�����ֵķ���
	public abstract View initView();

	// ��ʼ�����ݣ����Բ�ʵ��
	public void initData() {

	};
}
