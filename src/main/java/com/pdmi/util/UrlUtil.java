package com.pdmi.util;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlUtil {

	private String domain;

	private String path = "/";

	private String protocol = "http";

	private Map<String, String> paramList = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());

	public static UrlUtil create() {
		return new UrlUtil();
	}

	public static UrlUtil create(String url) {
		return new UrlUtil(url);
	}

	public UrlUtil() {

	}

	public UrlUtil(String url) {
		protocol = url.substring(0, url.indexOf("://"));
		String noPro = url.substring(url.indexOf("://") + "://".length());
		domain = noPro.substring(0, noPro.indexOf("/"));
		String noHost = noPro.substring(noPro.indexOf("/"));
		if (!noHost.contains("?"))
			path = noHost;
		else {
			path = noHost.substring(0, noHost.indexOf("?"));
			String paramString = noHost.substring(noHost.indexOf("?")
					+ "?".length());
			String[] paramSplit = paramString.split("&");
			for (String param : paramSplit) {
				if (param.contains("=")) {
					String paramKey = param.substring(0, param.indexOf("="));
					String paramValue = param.substring(param.indexOf("=")
							+ "=".length());
					paramList.put(paramKey, paramValue);
				} else {
					paramList.put(param, "");
				}
			}
		}
	}

	public String toString() {
		String url = "";
		if (domain == null) {
			throw new NullPointerException("url must have domain");
		}
		StringBuffer urlBuffer = new StringBuffer();
		urlBuffer.append(protocol).append("://").append(domain).append(path);
		if (paramList.size() > 0) {
			urlBuffer.append("?");
			for (Map.Entry<String, String> param : paramList.entrySet()) {
				urlBuffer.append(param.getKey()).append("=")
						.append(param.getValue()).append("&");
			}
			url = urlBuffer.toString();
			url = url.substring(0, urlBuffer.lastIndexOf("&"));
		} else {
			url = urlBuffer.toString();
		}
		return url;

	}

	public String getDomain() {
		return domain;
	}

	public UrlUtil setDomain(String domain) {
		this.domain = domain;
		return this;
	}

	public String getPath() {
		return path;
	}

	public UrlUtil setPath(String path) {
		this.path = path;
		return this;
	}

	public String getProtocol() {
		return protocol;
	}

	public UrlUtil setProtocol(String protocol) {
		this.protocol = protocol;
		return this;
	}

	public Map<String, String> getParamList() {
		return paramList;
	}

	public UrlUtil setParamList(Map<String, String> paramList) {
		this.paramList = paramList;
		return this;
	}

	public UrlUtil put(String key, String value) {
		paramList.put(key, value);
		return this;
	}
	
	public UrlUtil putEncode(String key, String value) {
		paramList.put(key, URLEncoder.encode(value));
		return this;
	}
	
	public static String postMap2Str(Map<String,String> postMap){
		int count = 0;
		StringBuilder strb = new StringBuilder();
		for(Map.Entry<String, String> entry : postMap.entrySet()){
			String key = entry.getKey();
			String value = URLEncoder.encode(entry.getValue());
			
			if(count == postMap.size() - 1){
				strb.append(key).append("=").append(value);
			}else {
				strb.append(key).append("=").append(value).append("&");
			}
			count = count + 1;
		}
		return strb.toString();
	}
}
