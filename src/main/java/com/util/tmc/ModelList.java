package com.util.tmc;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import net.sf.json.JSONObject;

public class ModelList {
	/**
	 * json格式的各个节点的path获取
	 * @param html
	 * @return
	 */
	public JSONObject ParserModel(String html)
	{
		Document doc=Jsoup.parse(html);
		NodeBean nb=NdInit(doc, 0, "#root:0");
		JSONObject json=JSONObject.fromObject(nb);
		return json;
	}
	/**
	 * bean格式的各个节点的path获取
	 * @param html
	 * @return
	 */
	public NodeBean ParserModelNb(String html)
	{
		Document doc=Jsoup.parse(html);
		NodeBean nb=NdInit(doc, 0, "");
		return nb;
	}
	/**
	 * @param e 节点数据
	 * @param deep 深度
	 * @param path 路径 like html:0|body:0|div:5|div:0|div:0|div:1|div:0|span:0
	 * @return
	 */
	public NodeBean NdInit(Element e,int deep,String path)
	{
		NodeBean nb=new NodeBean();
		nb.setDeep(deep);
		nb.setPath(path);
		String tagname=e.tagName();//标签名
		nb.setTagname(tagname);
		int ChildrenodeNum=e.children().size();//子节点个数
		nb.setChildrenodeNum(ChildrenodeNum);
		String txt=e.text().trim();
		String txt_childre=e.children().text().trim();
		String txt_only=txt.replace(txt_childre, "");
		txt=TxtUtil.replaceBlank(txt);
		txt_only=TxtUtil.replaceBlank(txt_only);
		nb.setTxt(e.text());  //对文本的处理上，要进行去空格，去特殊字符
		nb.setTxt_only(txt_only);
		int textnum=e.text().trim().length();//节点的text长度，只有不包含子节点的节点才给此出赋值
		nb.setTextnum(textnum);
		//if(textnum>0)
		{
			ArrayList<NodeBean> nodetag=new ArrayList<NodeBean>();//各个子节点的标签
			Elements es=e.children();
			for(int i=0;i<es.size();i++)
			{
				Element nexte=es.get(i);
				String nextpath=getPath(path, es, i);
				NodeBean nextnb=NdInit(nexte, ++deep,nextpath);
				nodetag.add(nextnb);
			}
			nb.setNodetag(nodetag);
		}
		return nb;
	}
	/**
	 * 
	 * @param path 父节点的path路径
	 * @param es  兄弟节点的元素列表
	 * @param i  本节点所处顺序
	 * @return
	 */
	public String getPath(String path,Elements es,int i)
	{
		String tagname=es.get(i).tagName();
		int num=1;
		for(int k=0;k<i;k++)
		{
			String tagnamef=es.get(k).tagName();
			if(tagname.equals(tagnamef))
			{
				num++;
			}
		}
		return path+"/"+tagname+"["+num+"]";
	}
}
