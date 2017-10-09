package com.yuanjia.zhbj.domain;

import java.util.ArrayList;

/**
 * 页签详情页数据
 * @author Administrator
 *
 */
public class TabData {
 
	public int retcode;
	public TabDetail data;	
	
	public class TabDetail	{
		public String title;
		public String more;
		public ArrayList<TabNewsData> news;
		public ArrayList<TopNewsData> topnews;
		@Override
		public String toString() {
			return "TabDetail [title=" + title + ", news=" + news
					+ ", topnews=" + topnews + "]";
		}
		
		
	}
	
	/**
	 * 新闻列表对象
	 * @author Administrator
	 *
	 */
	public class TabNewsData {
		public String id;
		public String listimage;
		public String title;
		public String pubdate;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "TabNewsData [title=" + title + "]";
		}
		
		
	}
	
	/**
	 * 头条新闻
	 * @author Administrator
	 *
	 */
	public class TopNewsData {
		public String id;
		public String topimage;
		public String title;
		public String pubdate;
		public String type;
		public String url;
		@Override
		public String toString() {
			return "TopNewsData [title=" + title + "]";
		}
		
	}

	@Override
	public String toString() {
		return "TabData [data=" + data + "]";
	}
	
	
}
