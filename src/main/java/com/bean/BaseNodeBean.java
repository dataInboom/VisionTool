package com.bean;

public class BaseNodeBean {
	String tagname;  //标签名
	int deep;  //树图层级数
	String path;//xpath绝对路径
	int childrenodeNum; //子节点个数
	int textnum;  //文本内容长度
	String txt;//内容
	double x;
	double y;
	double width;
	double height;
	double fontwh;
	String fontSize="";
	String fontWeight="";
	int fraction=0;
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
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getFontwh() {
		return fontwh;
	}
	public void setFontwh(double fontwh) {
		this.fontwh = fontwh;
	}
	public String getFontSize() {
		return fontSize;
	}
	public void setFontSize(String fontSize) {
		this.fontSize = fontSize;
	}
	public String getFontWeight() {
		return fontWeight;
	}
	public void setFontWeight(String fontWeight) {
		this.fontWeight = fontWeight;
	}
	public int getFraction() {
		return fraction;
	}
	public void setFraction(int fraction) {
		this.fraction = fraction;
	}
	
}
