package com.util.extractunion;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

public class UnionTemplateUtils {
	/**
	 * 去掉所有的html标签
	 * @param htmlStr
	 * @return
	 */
	 public static String getTextFromHtml(String htmlStr){  
	    	String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // script
	        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // style
	        String regEx_html = "<[^>]+>"; // HTML tag
	      

	        Pattern p_script = Pattern.compile(regEx_script,
	                Pattern.CASE_INSENSITIVE);
	        Matcher m_script = p_script.matcher(htmlStr);
	        htmlStr = m_script.replaceAll("");

	        Pattern p_style = Pattern
	                .compile(regEx_style, Pattern.CASE_INSENSITIVE);
	        Matcher m_style = p_style.matcher(htmlStr);
	        htmlStr = m_style.replaceAll("");

	        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
	        Matcher m_html = p_html.matcher(htmlStr);
	        htmlStr = m_html.replaceAll("");

	       

	        return htmlStr;
	    }  
	/**
	 * 本方法替换原有的Element.text()方法，原因是由于使用Element.text()方法无法获取段落格式
	 * @param Ele
	 * @return
	 */
	public static String getRltext(Element Ele)
	{
		String html=Jsoup.clean(Ele.html(), Whitelist.basic());
		html=html.replaceAll("&nbsp;", " ").replaceAll("&gt;"," ").replaceAll("&lt;"," ").replaceAll("&quot;", " ").replaceAll("&middot;", " ");
		return getTextFromHtml(html);
	}
	public static String extract(String template,Element myEle){
		try {
			String[] tmpTemplArr = template.split("\\|");
			 
			   for(int i =0;i<tmpTemplArr.length;i++){
				   String tmpItem = tmpTemplArr[i];
	           	String tmpTagName = tmpItem.split(":")[0].trim();
	           	String tmpTagIndex =  "";
	           	String tmpSelector = null;
	           	String tmpSelectorName = null;
	           	if(tmpItem.contains(",")){
	           		tmpTagIndex = tmpItem.split(":")[1].split(",")[0].trim();
	           		tmpSelector = tmpItem.split(":")[1].split(",")[1].split("=")[0].trim();
	           		tmpSelectorName = tmpItem.split(":")[1].split(",")[1].split("=")[1].replace("\"", "").trim();
	           	}else{
	           		if(tmpItem.split(":").length>1)
	           		{
	           			tmpTagIndex = tmpItem.split(":")[1].trim();
	           		}
	           		
	           	}

	           	int tmpCount = 0;
	           	String tmpResult = "";
	        	boolean bFound = false;
	        	if(myEle.children().isEmpty()){
	        		return null;
	        	}
	           	for(Element tmpEle:myEle.children()){
	           		if(tmpTagIndex.equals("*")){
	           			String tmpTemplate = "";
	   					for(int j=i+1;j<tmpTemplArr.length;j++){
	   						tmpTemplate = tmpTemplate+"|"+tmpTemplArr[j];
	   					}
	   					tmpTemplate = tmpTemplate.replaceFirst("\\|", "");
	   					Element tmpElement = null;
	           			if(tmpEle.tagName().equals(tmpTagName)){
	               			if(tmpSelector!=null&&tmpSelectorName!=null){
	               				if(tmpEle.attr(tmpSelector).trim().startsWith(tmpSelectorName)||tmpEle.attr(tmpSelector).trim().equalsIgnoreCase(tmpSelectorName)||tmpEle.attr(tmpSelector).trim().startsWith(tmpSelectorName.replace(" ",""))||tmpEle.attr(tmpSelector).trim().equalsIgnoreCase(tmpSelectorName.replace(" ",""))){
	               					tmpElement = tmpEle;
	               				}
	               			}else{
	               				tmpElement = tmpEle;
	               			}               			
	               		}
	           			
	           			if(tmpElement!=null&&!tmpTemplate.equals("")){
	           				String result = extract(tmpTemplate,tmpElement);
	           				if(result !=null){
	           					return result ;
	           				}
	           			}
	           			if(tmpTemplate.equals("")&&tmpElement!=null){
	           				if(tmpElement.text().trim().length()>0){
	           					tmpResult = tmpResult+getRltext(tmpElement);
	           				}
	           			}
	           		}else{
	           		if(tmpEle.tagName().equals(tmpTagName)){
	           			if(tmpCount==Integer.parseInt(tmpTagIndex)){
	           				myEle = tmpEle;
	           				bFound = true;
	           				break;
	           			}else{
	           				tmpCount ++;
	           			}
	           		}
	           	}
	           		
	           	}
	            if(!tmpResult.equals("")){
	          		 return tmpResult;
	          	 }
	           	if(!bFound){
	       			return null;
	       		}
	           	
	           	
	           }
			   if(myEle!=null){
	         		 if(myEle.text().length()>0){
	         			// System.out.println(myEle.cssSelector()); Parser.xmlParser()
	         			//String html=Jsoup.clean(myEle.html(), Whitelist.basic());
	         			//html=html.replace("<strong>", "").replace("</strong>", "").replace("<br>", "").replace("<br />", "").replace("<b>", "").replace("</b>", "").replace("<p>", "").replace("</p>", "").replace("<li>", "").replace("</li>", "").replace("<span>", "").replace("</span>", "").replace("&nbsp;", "");
	         			//return html;
	         			 return getRltext(myEle);
	         			 
	         		 }else{
	         			 return null;
	         		 }
	         	 }else{
	         		 return null;
	         	 }
		} catch (Exception e1) {
			
		}
		return null;
         		 
	}
	public static String extracttext(String template,Element myEle){
		String[] tmpTemplArr = template.split("\\|");
	 
		   for(int i =0;i<tmpTemplArr.length;i++){
			   String tmpItem = tmpTemplArr[i];
           	String tmpTagName = tmpItem.split(":")[0].trim();
           	String tmpTagIndex =  "";
           	String tmpSelector = null;
           	String tmpSelectorName = null;
           	if(tmpItem.contains(",")){
           		tmpTagIndex = tmpItem.split(":")[1].split(",")[0].trim();
           		tmpSelector = tmpItem.split(":")[1].split(",")[1].split("=")[0].trim();
           		tmpSelectorName = tmpItem.split(":")[1].split(",")[1].split("=")[1].replace("\"", "").trim();
           	}else{
           		tmpTagIndex = tmpItem.split(":")[1].trim();
           	}

           	int tmpCount = 0;
           	String tmpResult = "";
        	boolean bFound = false;
        	if(myEle.children().isEmpty()){
        		return null;
        	}
           	for(Element tmpEle:myEle.children()){
           		if(tmpTagIndex.equals("*")){
           			String tmpTemplate = "";
   					for(int j=i+1;j<tmpTemplArr.length;j++){
   						tmpTemplate = tmpTemplate+"|"+tmpTemplArr[j];
   					}
   					tmpTemplate = tmpTemplate.replaceFirst("\\|", "");
   					Element tmpElement = null;
           			if(tmpEle.tagName().equals(tmpTagName)){
               			if(tmpSelector!=null&&tmpSelectorName!=null){
               				if(tmpEle.attr(tmpSelector).trim().replace(" ", "").equalsIgnoreCase(tmpSelectorName.replace(" ", ""))){
               					tmpElement = tmpEle;
               				}
               			}else{
               				tmpElement = tmpEle;
               			}               			
               		}
           			
           			if(tmpElement!=null&&!tmpTemplate.equals("")){
           				String result = extract(tmpTemplate,tmpElement);
           				if(result !=null){
           					return result ;
           				}
           			}
           			if(tmpTemplate.equals("")&&tmpElement!=null){
           				if(tmpElement.text().trim().length()>0){
           					tmpResult = tmpResult+tmpElement.text();
           				}
           			}
           		}else{
           		if(tmpEle.tagName().equals(tmpTagName)){
           			if(tmpCount==Integer.parseInt(tmpTagIndex)){
           				myEle = tmpEle;
           				bFound = true;
           				break;
           			}else{
           				tmpCount ++;
           			}
           		}
           	}
           		
           	}
            if(!tmpResult.equals("")){
          		 return tmpResult;
          	 }
           	if(!bFound){
       			return null;
       		}
           	
           	
           }
		   if(myEle!=null){
         		 if(myEle.text().length()>0){
         			// System.out.println(myEle.cssSelector()); Parser.xmlParser()
         			//String html=Jsoup.clean(myEle.html(), Whitelist.basic());
         			//html=html.replace("<strong>", "").replace("</strong>", "").replace("<br>", "").replace("<br />", "").replace("<b>", "").replace("</b>", "").replace("<p>", "").replace("</p>", "").replace("<li>", "").replace("</li>", "").replace("<span>", "").replace("</span>", "").replace("&nbsp;", "");
         			//return html;
         			 return myEle.text();
         			 
         		 }else{
         			 return null;
         		 }
         	 }else{
         		 return null;
         	 }
         		 
	}
	/**
	 * 获得的正文中包含Img标签
	 * @param template
	 * @param myEle
	 * @return
	 */
	public static String extracthImg(String template,Element myEle)
	{
		String conetenthtml=extracthtml(template,myEle);
		if(conetenthtml==null)
			return "";
		//System.out.println("html:"+conetenthtml);
		Elements es=Jsoup.parse(conetenthtml).getElementsByTag("img");
		ArrayList srclist=new ArrayList();
		ArrayList altlist=new ArrayList();
		for(int i=0;i<es.size();i++)
		{
			String outhtml=es.get(i).outerHtml();
			String src=es.get(i).attr("src");
			String alt=es.get(i).attr("alt");
			srclist.add(src);
			altlist.add(alt);
			conetenthtml=conetenthtml.replace(outhtml,"{img "+i+"}");
		}
		String conetent=Jsoup.parse(conetenthtml).text();
		for(int i=0;i<es.size();i++)
		{
			String src=(String)srclist.get(i);
			String alt=(String)altlist.get(i);
			conetent=conetent.replace("{img "+i+"}", "<br/><br/><img src=\""+src+"\" alt=\""+alt+"\"/><br/><br/>");
			
		}
		return conetent;
	}
	public static String extracthtml(String template,Element myEle){
		//System.out.println("template:"+template);
		String[] tmpTemplArr = template.split("\\|");
		 
		   for(int i =0;i<tmpTemplArr.length;i++){
			   String tmpItem = tmpTemplArr[i];
        	String tmpTagName = tmpItem.split(":")[0].trim();
        	String tmpTagIndex =  "";
        	String tmpSelector = null;
        	String tmpSelectorName = null;
        	if(tmpItem.contains(",")){
        		tmpTagIndex = tmpItem.split(":")[1].split(",")[0].trim();
        		tmpSelector = tmpItem.split(":")[1].split(",")[1].split("=")[0].trim();
        		tmpSelectorName = tmpItem.split(":")[1].split(",")[1].split("=")[1].replace("\"", "").trim();
        	}else{
        		tmpTagIndex = tmpItem.split(":")[1].trim();
        	}

        	int tmpCount = 0;
        	String tmpResult = "";
     	boolean bFound = false;
     	if(myEle.children().isEmpty()){
     		return null;
     	}
        	for(Element tmpEle:myEle.children()){
        		if(tmpTagIndex.equals("*")){
        			String tmpTemplate = "";
					for(int j=i+1;j<tmpTemplArr.length;j++){
						tmpTemplate = tmpTemplate+"|"+tmpTemplArr[j];
					}
					tmpTemplate = tmpTemplate.replaceFirst("\\|", "");
					Element tmpElement = null;
        			if(tmpEle.tagName().equals(tmpTagName)){
            			if(tmpSelector!=null&&tmpSelectorName!=null){
            				if(tmpEle.attr(tmpSelector).trim().replace(" ", "").equalsIgnoreCase(tmpSelectorName.replace(" ", ""))){
            					tmpElement = tmpEle;
            				}
            			}else{
            				tmpElement = tmpEle;
            			}               			
            		}
        			
        			if(tmpElement!=null&&!tmpTemplate.equals("")){
        				String result = extract(tmpTemplate,tmpElement);
        				if(result !=null){
        					return result ;
        				}
        			}
        			if(tmpTemplate.equals("")&&tmpElement!=null){
        				if(tmpElement.text().trim().length()>0){
        				tmpResult = tmpResult+tmpElement.outerHtml()+"\r\n";
        				}
        			}
        		}else{
        		if(tmpEle.tagName().equals(tmpTagName)){
        			if(tmpCount==Integer.parseInt(tmpTagIndex)){
        				myEle = tmpEle;
        				bFound = true;
        				break;
        			}else{
        				tmpCount ++;
        			}
        		}
        	}
        		
        	}
         if(!tmpResult.equals("")){
       		 return tmpResult;
       	 }
        	if(!bFound){
    			return null;
    		}
        	
        	
        }
		   if(myEle!=null){
      		 if(myEle.outerHtml().length()>0){
      			// System.out.println(myEle.cssSelector());
      			// return myEle.cssSelector();
      			 return myEle.outerHtml();
      			 
      		 }else{
      			 return null;
      		 }
      	 }else{
      		 return null;
      	 }
      }
	public static void main(String[] args) throws IOException
	{
		String url="http://hn.cnr.cn/hngbxwzx/20161219/t20161219_523357443.shtml";
		URL s;
		try {
			s = new URL(url);
			String template="html:0|body:0|div:*,id=\"Area\"|div:*,id=\"cntl\"|div:*,class=\"wh645 left\"|div:*,class=\"sanji_left\"";
			Document  doc=Jsoup.parse(s, 1000);
			String html=Jsoup.clean(doc.html(), Whitelist.basic());
			System.out.println(html);
			String htmlimg=extract(template, doc);
			System.out.println(htmlimg);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

