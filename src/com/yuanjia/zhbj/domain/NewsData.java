package com.yuanjia.zhbj.domain;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * ���������Ϣ�ķ�װ
 *
 * �ֶ�������Ҫ�ͷ��������ص��ֶ���һ�£�����gson����
 * @author Administrator
 *
 */
public class NewsData {

	public int retcode;
	public ArrayList<NewsMenuData> data;
	
	//��������ݶ���
	public class NewsMenuData{
		
		public String id;
		public String url;
		public int type;
		public String title;
		public ArrayList<NewsTabData> children;
		@Override
		public String toString() {
			return "NewsMenuData [title=" + title + ", children=" + children
					+ "]";
		}
		
		
	}
	
	//����ҳ����11����ҳǩ�����ݶ���	
	public class NewsTabData{
		public String id;
		public String url;
		public int type;
		public String title;
		@Override
		public String toString() {
			return "NewsTabData [title=" + title + "]";
		}
		
		
	}

	@Override
	public String toString() {
		return "NewsData [retcode=" + retcode + ", data=" + data + "]";
	}
	
	
	
}
