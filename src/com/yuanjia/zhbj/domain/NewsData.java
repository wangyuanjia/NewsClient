package com.yuanjia.zhbj.domain;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 网络分类信息的分装
 *
 * 字段名必须要和服务器返回的字段名一致，方便gson解析
 * @author Administrator
 *
 */
public class NewsData {

	public int retcode;
	public ArrayList<NewsMenuData> data;
	
	//侧边栏数据对象
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
	
	//新闻页面下11个子页签的数据对象	
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
