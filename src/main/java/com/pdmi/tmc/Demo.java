package com.pdmi.tmc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.bean.BaseNodeBean;
import com.pdmi.tmc.util.ContentScore;
import com.pdmi.tmc.util.PubtimeScore;
import com.pdmi.tmc.util.TitleScore;
import com.pdmi.util.MD5Helper;
import com.util.WebDriverTool;
import com.util.tmc.NodeBean;
import com.util.tmc.ParserHTML;
import net.sf.json.JSONObject;

public class Demo {
	ArrayList<BaseNodeBean> nodelist=new ArrayList<BaseNodeBean>();
	ArrayList<BaseNodeBean> node_full_list=new ArrayList<BaseNodeBean>();
	String ajaxhtml="";
	public static void main(String[] args) {
		Demo d=new Demo();
		d.core();
	}
	public  void core() {
		System.setProperty("webdriver.chrome.driver", com.util.PropertyUtil.getvalue("chromedriver"));// 指定驱动路径
		System.setProperty("webdriver.http.factory", "jdk-http-client");
		WebDriverTool w=new WebDriverTool();
		WebDriver dr =w.getWebDriver(100);
		String url="http://cpc.people.com.cn/n1/2023/0207/c164113-32619269.html";
		String key=MD5Helper.encrypt32(url);
		dr.get(url);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		for(int i=0;i<1;i++)
		{
			String s=ReadFile();
			JavascriptExecutor j = (JavascriptExecutor) dr;
			Object  b=j.executeScript(s);
			//System.out.println(JSONObject.fromObject(b).toString());
			ajaxhtml=dr.getPageSource();
			ajaxhtml=ajaxhtml.replaceAll("<br>", "").replaceAll("<BR>", "");
			//System.out.println(ajaxhtml);
			double maxWidth = 0;
			double maxHeight = 0;
			NodeBean nb = ParserHTML.GetModelListNb(ajaxhtml);
			JSONObject info=JSONObject.fromObject(b);
			HashMap<String,JSONObject> nodemap=new HashMap<String,JSONObject>();
			for(int k=0;;k++)
			{
				JSONObject nodeinfo= info.getJSONObject(k+"");
				if(nodeinfo==null||"null".equals(nodeinfo.toString()))
				{
					break;
				}
				String fullxpath=nodeinfo.getString("fullxpath");
				nodemap.put(fullxpath, nodeinfo);
			}
			readSv(nb, nodemap);
			readSvFull(nb, nodemap);
			BaseNodeBean bnb=TitleScore.GetDistanceTitle(ajaxhtml, nodelist); //通过mate 里面包含了标题的内容的可能性去去标题
			if(bnb!=null)
			{
				System.out.println(bnb.getPath()+"\t"+bnb.getTxt());
			}
			
			List<BaseNodeBean> titlist=TitleScore.GetTypeface(ajaxhtml, nodelist, maxWidth, maxHeight);
			System.out.println("-----:"+JSONObject.fromObject(titlist.get(0)).toString());
			ArrayList<BaseNodeBean> conlist=ContentScore.GetTypeface(titlist.get(0), node_full_list,node_full_list, maxWidth, maxHeight);
			String putime=PubtimeScore.GetTypeface(titlist.get(0), node_full_list, maxWidth, maxHeight);
			System.out.println("时间:"+putime+"\r\n");
			for(BaseNodeBean bn:conlist)
			{
				//System.out.println(JSONObject.fromObject(bn).toString());
				System.out.println(bn.getTxt().trim()+"\r\n");
			}
			
		}
		
		dr.quit();
	}
	public  void readSv(NodeBean nb,HashMap<String,JSONObject> nodemap)
	{
		String tagname=nb.getTagname();//标签名
		int deep=nb.getDeep();//节点深度
		String path=nb.getPath();//本节点的路径
		path=path.replace("html[1]/body[1]", "html/body").toUpperCase();//转换为与js获取的一致的格式
		int childrenodeNum=nb.getChildrenodeNum();//子节点个数
		ArrayList<NodeBean> nodetag=nb.getNodetag();
		int textnum=nb.getTextnum();//节点的text长度
		String txt=nb.getTxt();//文本内容
		JSONObject node=nodemap.get(path);
		if(node!=null)
		{
			if(childrenodeNum==0)
			{
				double x=node.getDouble("x");
				double y=node.getDouble("y");
				double  width=node.getDouble("width");
				double  height=node.getDouble("height");
				String fontSize=node.getString("fontSize");
				String fontWeight=node.getString("fontWeight");
				if(width>0&&height>0&&!"a".equals(tagname))
				{
					JSONObject s=new JSONObject();
					s.put("tagname", tagname);
					s.put("deep", deep);
					s.put("path", path);
					s.put("childrenodeNum", childrenodeNum);
					s.put("textnum", textnum);
					s.put("txt", txt);
					s.put("x", x);
					s.put("y", y);
					s.put("width", width);
					s.put("height", height);
					s.put("fontSize", fontSize);
					s.put("fontWeight", fontWeight);
					BaseNodeBean bnb=new BaseNodeBean();
					bnb.setTagname(tagname);
					bnb.setDeep(deep);
					bnb.setPath(path);
					bnb.setChildrenodeNum(childrenodeNum);
					bnb.setTextnum(textnum);
					bnb.setTxt(txt);
					bnb.setX(x);
					bnb.setY(y);
					bnb.setWidth(width);
					bnb.setHeight(height);
					bnb.setFontSize(fontSize);
					bnb.setFontWeight(fontWeight);
					nodelist.add(bnb);
					System.out.println(s.toString());
				}
				
			}
			
		}
		for(int i=0;i<nodetag.size();i++)
		{
			NodeBean fnb=nodetag.get(i);
			readSv(fnb, nodemap);
		}
	}
	public  void readSvFull(NodeBean nb,HashMap<String,JSONObject> nodemap)
	{
		String tagname=nb.getTagname();//标签名
		int deep=nb.getDeep();//节点深度
		String path=nb.getPath();//本节点的路径
		path=path.replace("html[1]/body[1]", "html/body").toUpperCase();//转换为与js获取的一致的格式
		int childrenodeNum=nb.getChildrenodeNum();//子节点个数
		ArrayList<NodeBean> nodetag=nb.getNodetag();
		int textnum=nb.getTextnum();//节点的text长度
		String txt=nb.getTxt();//文本内容
		JSONObject node=nodemap.get(path);
		if(node!=null)
		{
			//if(childrenodeNum==0)
			{
				double x=node.getDouble("x");
				double y=node.getDouble("y");
				double  width=node.getDouble("width");
				double  height=node.getDouble("height");
				String fontSize=node.getString("fontSize");
				String fontWeight=node.getString("fontWeight");
				if(width>0&&height>0&&!"a".equals(tagname))
				{
					JSONObject s=new JSONObject();
					s.put("tagname", tagname);
					s.put("deep", deep);
					s.put("path", path);
					s.put("childrenodeNum", childrenodeNum);
					s.put("textnum", textnum);
					s.put("txt", txt);
					s.put("x", x);
					s.put("y", y);
					s.put("width", width);
					s.put("height", height);
					s.put("fontSize", fontSize);
					s.put("fontWeight", fontWeight);
					BaseNodeBean bnb=new BaseNodeBean();
					bnb.setTagname(tagname);
					bnb.setDeep(deep);
					bnb.setPath(path);
					bnb.setChildrenodeNum(childrenodeNum);
					bnb.setTextnum(textnum);
					bnb.setTxt(txt);
					bnb.setX(x);
					bnb.setY(y);
					bnb.setWidth(width);
					bnb.setHeight(height);
					bnb.setFontSize(fontSize);
					bnb.setFontWeight(fontWeight);
					node_full_list.add(bnb);
					System.out.println(s.toString());
				}
				
			}
			
		}
		for(int i=0;i<nodetag.size();i++)
		{
			NodeBean fnb=nodetag.get(i);
			readSvFull(fnb, nodemap);
		}
	}
	public static String GetRealtxt(String txt,ArrayList<NodeBean> nodetag)
	{
		for(NodeBean nb:nodetag)
		{
			String chtxt=nb.getTxt();
			txt=txt.replace(chtxt,"");
		}
		return txt;
	}
	/**
	 * 在画布上写入
	 * @param nb
	 * @param nodemap
	 */
	public static void readTree(NodeBean nb,HashMap<String,JSONObject> nodemap,BufferedImage image)
	{
		String tagname=nb.getTagname();//标签名
		int deep=nb.getDeep();//节点深度
		String path=nb.getPath();//本节点的路径
		path=path.replace("html[1]/body[1]", "html/body").toUpperCase();//转换为与js获取的一致的格式
		System.out.println("jsoup:"+path);
		int childrenodeNum=nb.getChildrenodeNum();//子节点个数
		ArrayList<NodeBean> nodetag=nb.getNodetag();
		int textnum=nb.getTextnum();//节点的text长度
		String txt=nb.getTxt();//文本内容
		JSONObject node=nodemap.get(path);
		if(node!=null)
		{
			System.out.println("匹配中："+node.toString());
			System.out.println(txt);
			double x=node.getDouble("x");
			double y=node.getDouble("y");
			double scrollWidth=node.getDouble("scrollWidth");
			double  scrollHeight=node.getDouble("scrollHeight");
			if("img".equalsIgnoreCase(tagname)||"video".equalsIgnoreCase(tagname))//只有在有图片或者内容的地方才进行画框   图片蓝色，应该为中性
			{
				Graphics2D  g = image.createGraphics();
		        g.setColor(Color.blue);//画笔颜色
		        g.drawRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
		       
		        g.fillRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//画着色块
		        g.dispose();
			}
			
			if("p".equalsIgnoreCase(tagname)&&txt.length()>0||"span".equalsIgnoreCase(tagname)&&txt.length()>0)//
			{
				Graphics2D  g = image.createGraphics();
		        g.setColor(Color.black);//画笔颜色
		        g.drawRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
		       
		        g.fillRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//画着色块
		        g.dispose();
			}else
			{
				if(txt.length()>0&&childrenodeNum==0)
				{
					Graphics2D  g = image.createGraphics();
			        g.setColor(Color.orange);//画笔颜色
			        g.drawRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
			       
			        g.fillRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//画着色块
			        g.dispose();
				}
				if(txt.length()>0)//需要解决  br等 单标签问题 
				{
					boolean brall=true;
					for(NodeBean fnb:nodetag)
					{
						String ftagname=fnb.getTagname();
						if(!"br".equalsIgnoreCase(ftagname))
						{
							brall=false;
						}
					}
					if(brall)
					{
						Graphics2D  g = image.createGraphics();
				        g.setColor(Color.BLACK);//画笔颜色
				        g.drawRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
				       
				        g.fillRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//画着色块
				        g.dispose();
					}
					
				}
			}
			
			if("a".equalsIgnoreCase(tagname)&&txt.length()>0)//a标签应该属于在内容里面剔除，标记为垃圾颜色
			{
				Graphics2D  g = image.createGraphics();
		        g.setColor(Color.yellow);//画笔颜色
		        g.drawRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
		       
		        g.fillRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//画着色块
		        g.dispose();
			}
			if("h1".equalsIgnoreCase(tagname)||"h2".equalsIgnoreCase(tagname)||"strong".equalsIgnoreCase(tagname))//a标签应该属于在内容里面剔除，标记为垃圾颜色
			{
				Graphics2D  g = image.createGraphics();
		        g.setColor(Color.green);//画笔颜色
		        g.drawRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
		       
		        g.fillRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//画着色块
		        g.dispose();
			}
			//红色的为节点分割线
			{
				Graphics2D  g = image.createGraphics();
			    g.setColor(Color.RED);//画笔颜色
			    g.drawRect((int)x, (int)y, (int)scrollWidth, (int)scrollHeight);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
			    g.dispose();
			}
			
		}
		
		
		for(int i=0;i<nodetag.size();i++)
		{
			NodeBean fnb=nodetag.get(i);
			readTree(fnb, nodemap,image);
		}
	}
	public static void WriteFile(String html,String path)
	{
		try {

			FileWriter fw = new FileWriter(new File(path), true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(html);
			pw.flush();
			} catch (IOException e) {
			e.printStackTrace();
			}
	}
	public static String  ReadFile()
	{
		 StringBuffer script = new StringBuffer();
	        File file = new File("G:\\code\\TmcTool\\Nitrogen.js");
	        FileReader filereader;
			try {
				filereader = new FileReader(file);
				 BufferedReader bufferedReader = new BufferedReader(filereader);
			        String tempString = null;
			        while ((tempString = bufferedReader.readLine()) != null) {
			            script.append(tempString).append("\n");
			        }
			        bufferedReader.close();
			        filereader.close();
			        return script.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	     return null;
	}
}

