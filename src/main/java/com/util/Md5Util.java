package com.util;


import java.security.MessageDigest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Md5Util {
 private static MessageDigest md5 = null;
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * 用于获取一个String的md5值
     * @param string
     * @return
     */
    public static String getMd5(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }
    public static String getURLMD5Value(String html)
    {
    	Elements es=Jsoup.parse(html).getElementsByTag("a");
    	StringBuffer sb=new StringBuffer();
    	for(Element e:es)
    	{
    		String url=e.attr("href");
    		System.out.println(url);
    		sb.append(url);
    	}
    	return getMd5(sb.toString());
    }
    
    public static void main(String[] args) {
    	//String sql="select listpageurl from hostlist_filter_bbs limit 1,200";
    	//DBUtil dbu=new DBUtil();
    	
    	String url = "http://www.renminbao.com/rmb/articles/2016/10/29/64415.html";
       /* Document document = null;
		try {
			document = Jsoup.parse(new URL(url), 20 * 1000);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        String html = document.html();*/
       String u=Md5Util.getURLMD5Value(url);
       System.out.println(u);
    }
}