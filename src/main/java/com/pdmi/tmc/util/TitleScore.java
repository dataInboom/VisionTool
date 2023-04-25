package com.pdmi.tmc.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.bean.BaseNodeBean;
import com.pdmi.util.ArrayUitl;

import net.sf.json.JSONObject;
/**
 * 对每个节点进行是否是标题的可能性进行打分
 *  标准维度  1.与<title></title>中内容的相似度评分；2。节点所处的页面位置是不是在头部；3.字体的大小，是不是在整个文章中单个字体最大的
 *
 * @author 10694
 *
 */
public class TitleScore {
	//public static int[][] array=ArrayUitl.getSparseArrayFromFile("G:\\code\\TmcTool\\titleconfig.txt");
	public static int[][] array=ArrayUitl.getSparseArrayFromFile("C:\\matrix\\titleconfig.txt");
	/**
	 *  通过字体是否有加粗（h1,h2,strong等标签），节点内单个文字的大小，节点所处页面的位置是否是
	 *  主要是通过视觉上取最显眼的文本内容
	 * @param ajaxhtml 异步加载完毕的html
	 * @param nodelist 最终节点信息
	 * @param maxWidth 页面最大宽
	 * @param maxHeight 页面最大高
	 * @return
	 */
	public static List<BaseNodeBean> GetTypeface(String ajaxhtml,ArrayList<BaseNodeBean> nodelist,double maxWidth,double maxHeight)
	{
		ArrayList<BaseNodeBean> firstlist=new ArrayList<BaseNodeBean>();
		ArrayList<BaseNodeBean> fontfirstlist=new ArrayList<BaseNodeBean>();
		for(BaseNodeBean bnb:nodelist)
		{
			String path=bnb.getPath();
			String tagname=bnb.getTagname();
			if("a".equalsIgnoreCase(tagname)) //去掉a标签
			{
				continue;
			}
			String txt=bnb.getTxt();
			int textnum=bnb.getTextnum();
			double x=bnb.getX();
			double y=bnb.getY();
			double width=bnb.getWidth();
			double height=bnb.getHeight();
			if(path.indexOf("H1")!=-1||path.indexOf("H2")!=-1) //标题的最明显标签特征
			{
				String fontsize=bnb.getFontSize();
				if(fontsize!=null&&fontsize.length()>0)
				{
					double fontwh=Double.parseDouble(fontsize.replace("px",""));
					bnb.setFontwh(fontwh);
					firstlist.add(bnb);
				}
				
			}else
			{
				if(textnum>5&&textnum<47)//标题最小阈值设置为7,最大阈值为37
				{
					String fontsize=bnb.getFontSize();
					if(fontsize!=null&&fontsize.length()>0)
					{
						double fontwh=Double.parseDouble(fontsize.replace("px",""));//TypefontSize(txt, textnum, width, height);
						bnb.setFontwh(fontwh);
						fontfirstlist.add(bnb);
					}
					
				}
			}
			
		}
		//取字体可能最大的四个地方
		List<BaseNodeBean> cl=fontfirstlist.stream().sorted(Comparator.comparing(BaseNodeBean::getFontwh).reversed())
		  //.sorted(Comparator.comparing(User::getAge).reversed()) 加上reversed()方法就是逆序排序
		  .collect(Collectors.toList());
		for(int k=0;k<cl.size();k++)
		{
			BaseNodeBean d=cl.get(k);
			firstlist.add(d);
			if(k>5)
			{
				break;
			}
		}
		List<BaseNodeBean> fl=firstlist.stream().sorted(Comparator.comparing(BaseNodeBean::getFontwh).reversed())
				  //.sorted(Comparator.comparing(User::getAge).reversed()) 加上reversed()方法就是逆序排序
				  .collect(Collectors.toList());
		
		//进行位置比较，排除处于犄角旮旯的节点
		/**
		 * 1903, 1024   宽和高的大小
		 * 节点的要素为 x,y,w,h
		 */
		for(int i=0;i<fl.size();i++)
		{
			BaseNodeBean bs=fl.get(i);
			double x=bs.getX();
			double y=bs.getY();
			double w=bs.getWidth();
			double h=bs.getHeight();
			//计算出节点的中心点坐标
			double pointx=x+w/2;
			double pointy=y+h/2;
			if(pointy>1024d/1.5d)  //越过了2/3的y轴，直接放弃
			{
				fl.remove(i);
				continue;
			}
		
		}
		for(BaseNodeBean bs:fl)
		{
			System.out.println("未排序筛选:    标题："+bs.getTxt().trim()+"\t字体大小："+bs.getFontSize()+"\t视觉评分："+bs.getFraction());
		}
		fl=FilterByPosition(fl);
		/***
		 * 此处进行位置评分，对选中的6个以内的节点，在标题字体大小大差不差的情况下，对所处页面位置是不是最佳位置进行评估
		 * 6取2
		 */
		for(BaseNodeBean bs:fl)
		{
			System.out.println("排序筛选:    标题："+bs.getTxt().trim()+"\t字体大小："+bs.getFontSize()+"\t视觉评分："+bs.getFraction()+"\t起始坐标：");
		}
		return fl;
	}
	/**
	 *   通过对N多个数据的经验之谈，得出了标题出现的最佳的区域，并对每个区域进行出现概率的打打分
	 *  对f1中的每个节点，对其位置进行概率性的打分，得出最优的两个节点
	 * @param f1
	 * @return
	 */
	public static List<BaseNodeBean> FilterByPosition(List<BaseNodeBean> f1)
	{
		double cx=951.5d;   //中心坐标
		double cy=512d;
		ArrayList<BaseNodeBean> f2=new ArrayList<BaseNodeBean>();
		for(int i=0;i<f1.size();i++)
		{
			BaseNodeBean bs=f1.get(i);
			double x=bs.getX();
			double y=bs.getY();
			double w=bs.getWidth();
			double h=bs.getHeight();
			//计算出节点的中心点坐标
			double pointx=x+w/2;
			double pointy=y+h/2;
			int x_int=(int)x;
			int y_int=(int)y;
			int w_int=(int)w;
			int h_int=(int)h;
			int area=w_int*h_int;
			int blank_area=0;//空白像素点数
			int fraction=0;//概率分数之和
			for(int i1=0;i1<w_int;i1++)
			{
				for(int j=0;j<h_int;j++)
				{
					int fx=x_int+i1;
					int fy=y_int+j;
					if(fx<=1903&&fy<1024)//1024,1903
					{
						int value=array[fy][fx];
						if(value==0)
						{
							blank_area++;
						}else
						{
							fraction+=value;
						}
					}else
					{
						break;
					}
					
				}
			}
			double s_area=blank_area/(double)area;
			/*if(s_area>0.35d) //如果有35%面积不在统计概率之内，直接去掉
			{
				f1.remove(i);
				continue;
			}*/
			bs.setFraction(fraction);
			f2.add(bs);
		}
		List<BaseNodeBean> fr=f2.stream().sorted(Comparator.comparing(BaseNodeBean::getFraction).reversed()).collect(Collectors.toList());
		return fr;
	}
	
	/**
	 * 已作废，该方法无法准确计算，目前该值通过js去获取
	 *  计算 文本内容的字体大小  ，设每个汉字为正方形，每个汉字边长为 X像素
	 *  本方法只能计算汉字，如果需要计算英文，则需要搞分词
	 * @param txt
	 * @param textnum
	 * @param width
	 * @param height
	 * @return
	 */
	public static double TypefontSize(String txt,int textnum,double width,double height)
	{
		double X;
		/**
		 *  标题只存在一行
		 */
		X=height;
		if(X*textnum<width)
		{
			return X;
		}else
		{
			X=height/2;
			if(X*textnum<width*2)
			{
				return X;
			}else
			{
				X=height/2;
				if(X*textnum<width*3)
				{
					return X;
				}
			}
		}
		
		return 0;
	}
	public static ArrayList<BaseNodeBean> GetPosition(String ajaxhtml,ArrayList<BaseNodeBean> nodelist,double maxWidth,double maxHeight)
	{
		List<BaseNodeBean> fontlist=GetTypeface(ajaxhtml, nodelist, maxWidth, maxHeight); 
		BaseNodeBean distance=GetDistanceTitle(ajaxhtml, nodelist); 
		
		
		return null;
	}
	/**
	 *  与<title></title>内容 编辑距离最短的节点匹配
	 * @return 标题节点最优解
	 */
	public static BaseNodeBean GetDistanceTitle(String ajaxhtml,ArrayList<BaseNodeBean> nodelist)
	{
		if(nodelist==null)
		{
			return null;
		}
		Document doc=Jsoup.parse(ajaxhtml);
		Elements es=doc.getElementsByTag("title");
		if(es.size()>0)
		{
			Element e=es.get(0);
			String title_tag=e.text().trim();
			if(title_tag.length()>12)
			{
				int min_d=14;
				BaseNodeBean rbnb=null; 
				for(BaseNodeBean bnb:nodelist)
				{
					if(bnb==null)
					{
						continue;
					}
					String txt=bnb.getTxt().trim();
					if((txt.length()-title_tag.length())<15)
					{
						int d=getDistance(txt, title_tag);
						if(min_d>d)
						{
							min_d=d;
							rbnb=bnb;
						}
					}
					
				}
				return rbnb;
			}
		}
		return null;
	}
	/**
	 * 编辑距离计算
	 * @param str1
	 * @param str2
	 * @return
	 */
	 public static int getDistance(String str1, String str2) {
	        char[] wd1 = str1.toCharArray();
	        char[] wd2 = str2.toCharArray();
	        int len1 = wd1.length;
	        int len2 = wd2.length;
	        //定义一个矩阵
	        int[][] dist = new int[len1 + 1][len2 + 1];
	        //初始状态 F(i, 0) = i; F(0, j) = j
	        for (int i = 0; i <= len1; ++i)
	            dist[i][0] = i;
	        for (int j = 0; j <= len2; ++j)
	            dist[0][j] = j;
	        for (int i = 1; i <= len1; ++i) {
	            for (int j = 1; j <= len2; ++j) {
	                //F(i,j) = min(F(i-1, j) + 1,
	                // F(i, j-1) + 1, F(i-1, j-1) + (wd1[i] == wd2[j] ? 0 : 1))
	                // 首先求出插入和删除的最小值
	                dist[i][j] = Math.min(dist[i - 1][j], dist[i][j - 1]) + 1;
	                //再和替换进行比较
	                if (wd1[i - 1] == wd2[j - 1]) {
	                    //不需要进行替换
	                    dist[i][j] = Math.min(dist[i][j], dist[i - 1][j - 1]);
	                } else {
	                    dist[i][j] = Math.min(dist[i][j], dist[i - 1][j - 1] + 1);
	                }
	            }
	        }
	        return dist[len1][len2];
	    }
	 public static void main(String[] args) {
		 String s="国家主席习近平将于11月14日至17日赴印度尼西亚巴厘岛出席二十国集团领导人第十七次峰会";
		 //double x=TypefaceSize(s,s.length(),670d,52d);
		 //System.out.println(x);
	}
}
