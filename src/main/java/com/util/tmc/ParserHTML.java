package com.util.tmc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.util.extractunion.UnionTemplateUtils;

import net.sf.json.JSONObject;

public class ParserHTML {
	/**
	 * 获得各个节点的模板路径和各种信息
	 * @return
	 */
	public static JSONObject GetModelList(String html)
	{
		ModelList ml=new ModelList();
		return ml.ParserModel(html);
	}
	/**
	 * 获得各个节点的模板路径和各种信息
	 * @return
	 */
	public static NodeBean GetModelListNb(String html)
	{
		ModelList ml=new ModelList();
		return ml.ParserModelNb(html);
	}
	/**
	 * 返回对应path下的文本内容
	 * @param path 节点路径
	 * @param html doc内容
	 * @return
	 */
	public static String GetTextinfo(String path,String html)
	{
		Document doc=Jsoup.parse(html);
		return UnionTemplateUtils.extract(path, doc);
	}
}
