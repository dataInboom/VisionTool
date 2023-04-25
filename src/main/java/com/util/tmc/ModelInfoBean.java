package com.util.tmc;
/**
 * 存放模板的信息
 * @author Administrator
 *
 */
public class ModelInfoBean {
	String url="";//站点host
	String titlepath="";//标签路径
	String pubtimepath="";//发布时间路径
	String contentpath="";//正文路径
	String regular="";//文章url的正则
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitlepath() {
		return titlepath;
	}
	public void setTitlepath(String titlepath) {
		this.titlepath = titlepath;
	}
	public String getPubtimepath() {
		return pubtimepath;
	}
	public void setPubtimepath(String pubtimepath) {
		this.pubtimepath = pubtimepath;
	}
	public String getContentpath() {
		return contentpath;
	}
	public void setContentpath(String contentpath) {
		this.contentpath = contentpath;
	}
	public String getRegular() {
		return regular;
	}
	public void setRegular(String regular) {
		this.regular = regular;
	}
	
}
