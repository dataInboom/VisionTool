package com.bean;
/**
 * @author Administrator
 *
 */
public class ModelBean {
	int taskid=-1;
	String title="";
	String pubtime="";
	String postdata="";
	String author="";
	String urlfilter="";
	String host="";
	public int getTaskid() {
		return taskid;
	}
	public void setTaskid(int taskid) {
		this.taskid = taskid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPubtime() {
		return pubtime;
	}
	public void setPubtime(String pubtime) {
		this.pubtime = pubtime;
	}
	public String getPostdata() {
		return postdata;
	}
	public void setPostdata(String postdata) {
		this.postdata = postdata;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUrlfilter() {
		return urlfilter;
	}
	public void setUrlfilter(String urlfilter) {
		this.urlfilter = urlfilter;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
}
