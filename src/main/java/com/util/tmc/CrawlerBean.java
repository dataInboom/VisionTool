package com.util.tmc;

public class CrawlerBean {
	String url="";
	String defaultCharset="";
	boolean Ajaxflag=false;//是否采用浏览器采集的方式进行采集，true 是，false 否
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDefaultCharset() {
		return defaultCharset;
	}
	public void setDefaultCharset(String defaultCharset) {
		this.defaultCharset = defaultCharset;
	}
	public boolean isAjaxflag() {
		return Ajaxflag;
	}
	public void setAjaxflag(boolean ajaxflag) {
		Ajaxflag = ajaxflag;
	}
	
}
