package com.util.tmc;


import java.util.ArrayList;

import org.jsoup.nodes.Element;


public class NodeBean {
	String tagname;//标签名
	int deep;//节点深度
	String path="";//本节点的路径
	int childrenodeNum;//子节点个数
	int textnum=0;//节点的text长度，只有不包含子节点的节点才给此出赋值
	String txt="";//文本内容，只有无子节点的才有
	String txt_only="";//文本节点，去除子节点内容后得内容
	ArrayList<NodeBean> nodetag=new ArrayList<NodeBean>();//各个子节点的标签
	public String getTagname() {
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
	}
	public int getDeep() {
		return deep;
	}
	public void setDeep(int deep) {
		this.deep = deep;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public int getChildrenodeNum() {
		return childrenodeNum;
	}
	public void setChildrenodeNum(int childrenodeNum) {
		this.childrenodeNum = childrenodeNum;
	}
	public int getTextnum() {
		return textnum;
	}
	public void setTextnum(int textnum) {
		this.textnum = textnum;
	}
	public ArrayList<NodeBean> getNodetag() {
		return nodetag;
	}
	public void setNodetag(ArrayList<NodeBean> nodetag) {
		this.nodetag = nodetag;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public String getTxt_only() {
		return txt_only;
	}
	public void setTxt_only(String txt_only) {
		this.txt_only = txt_only;
	}
	
	
}

