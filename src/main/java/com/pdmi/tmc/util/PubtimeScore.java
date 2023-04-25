package com.pdmi.tmc.util;

import java.util.ArrayList;
import java.util.List;

import com.bean.BaseNodeBean;

public class PubtimeScore {
	/**
	 * 获取发布时间
	 * @param titlist  标题节点
	 * @param nodelist
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static String GetTypeface(BaseNodeBean titlebean,ArrayList<BaseNodeBean> nodelist,double maxWidth,double maxHeight)
	{
		
		double x=titlebean.getX();
		double y=titlebean.getY();
		double width=titlebean.getWidth();
		double height=titlebean.getHeight();
		String path=titlebean.getPath();//xpath绝对路径
		
		double x1=x;
		double x2=x+width;
		
		for(BaseNodeBean bn:nodelist)
		{
			double fx=bn.getX();
			double fy=bn.getY();
			double fwidth=bn.getWidth();
			double fheight=bn.getHeight();
			String fpath=bn.getPath();//xpath绝对路径
			int childrenodeNum=bn.getChildrenodeNum();
			int textnum=bn.getTextnum();
			
			if(!fpath.equals(path))
			{
				double hc=fy-y-height;//垂直差
				if(hc>0&&hc<140)
				{
					double fx1=fx;
					double fx2=fx+fwidth;
					double c1=fx1-x1;
					double c2=fx2-x2;
					if(c1>-80d&&c1<(width-80d))
					{
						if(c2>(width*-1d)&&c2<80d)
						{
							if(textnum>1)
							{
								String txt=bn.getTxt();
								System.out.println(txt);
								String pubtime=txt;//此处是阉割版本，如果你能从 标题和正文间的内容抽出发布时间，那就在这里替换
								if(pubtime!=null&&pubtime.length()>0)
								{
									return pubtime;
								}
							}
						}
					}
				}
				
				
			}
		}
		return null;
	}
}
