package com.pdmi.tmc.util;

import java.util.ArrayList;
import java.util.List;

import com.bean.BaseNodeBean;

public class ContentScore {
	/**
	 * 获取正文，正文与标题之间的内容节点信息
	 * @param titlist  标题节点
	 * @param nodelist
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static ArrayList<BaseNodeBean> GetTypeface(BaseNodeBean titlebean,ArrayList<BaseNodeBean> nodelist,ArrayList<BaseNodeBean> node_full_list,double maxWidth,double maxHeight)
	{
		ArrayList<BaseNodeBean> contentList=new ArrayList<BaseNodeBean>();
		double x=titlebean.getX();
		double y=titlebean.getY();
		double width=titlebean.getWidth();
		double height=titlebean.getHeight();
		String path=titlebean.getPath();//xpath绝对路径
		
		double x1=x;
		double x2=x+width;
		double nc_fy=0d;//标记一个x点，用来计算每个被设定为正文得节点得x
		for(BaseNodeBean bn:nodelist)
		{
			double fx=bn.getX();
			double fy=bn.getY();
			double fwidth=bn.getWidth();
			double fheight=bn.getHeight();
			String fpath=bn.getPath();//xpath绝对路径
			int textnum=bn.getTextnum();
			String tagname=bn.getTagname();
			if("a".equals(tagname))
			{
				continue;
			}
			if(!fpath.equals(path))
			{
				double hc=fy-y-height;//垂直差
				if(hc>0)
				{
					double fx1=fx;
					double fx2=fx+fwidth;
					double c1=fx1-x1;
					double c2=fx2-x2;
					if(c1>-80d&&c1<(width-80d))
					{
						if(c2>(width*-1d)&&c2<80d)
						{
							if(textnum>3||("img".equals(tagname)||"video".equals(tagname)||"radio".equals(tagname)))
							{
								contentList.add(bn);
							}
							
						}
					}
				}
			}
		}
		ArrayList<BaseNodeBean> contentList_clear=ClearInterfere(titlebean, contentList, node_full_list);
		return contentList_clear;
	}
	/**
	 * 几何计算，对提起到的正文的节点要进行垃圾的清除，目的就是去除非正文的各个节点
	 * 
	 * 去除方式之一，与标题同宽的情况下，计算当前节点的与标题宽度的差距，差距过大则说明该节点不应该算在正文之类
	 * 
	 * 
	 * @param contentList
	 * @return
	 */
	public static ArrayList<BaseNodeBean> ClearInterfere(BaseNodeBean titlebean,ArrayList<BaseNodeBean> contentList,ArrayList<BaseNodeBean> node_full_list)
	{
		ArrayList<BaseNodeBean> contentList_clear=new ArrayList<BaseNodeBean>();
		ArrayList<BaseNodeBean> contentList_clear2=new ArrayList<BaseNodeBean>();
		double title_x=titlebean.getX();
		double title_y=titlebean.getY();
		double title_width=titlebean.getWidth();
		double title_height=titlebean.getHeight();
		for(BaseNodeBean bn:contentList)
		{
			double x=bn.getX();
			double y=bn.getY();
			double width=bn.getWidth();
			double height=bn.getHeight();
			if(title_width/width>2.5d)//节点不够宽，那么就会有问题
			{
				continue;
			}
			contentList_clear.add(bn);
		}
		double bottom_y=0d;
		for(BaseNodeBean bn:contentList_clear)
		{
			double x=bn.getX();
			double y=bn.getY();
			double width=bn.getWidth();
			double height=bn.getHeight();
			if(bottom_y==0d)
			{
				bottom_y=y+height;
			}else
			{
				double f_max_distance=bottom_y-y;
				System.out.println(f_max_distance+"\t"+bn.getTagname()+"\t"+bn.getTxt());
				if(f_max_distance<-200d)
				{
					break;
				}
			}
			bottom_y=y+height;
			contentList_clear2.add(bn);
		}
		return contentList_clear2;
	}
}
