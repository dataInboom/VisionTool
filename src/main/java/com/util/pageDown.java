package com.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bean.header;


public class pageDown {

	private final static Logger logger = LoggerFactory.getLogger(pageDown.class);
	public static void main(String[] args)
	{
		pageDown.test();
	}
	public static void test()
	
	{
		String[] strs={"2743417569910802674","-533405062728621496","-505399083224922171","6544599813101078012","-7869873242155244735","-8096342035009976293","2969541831767987999","687726471996651412","-2847995197119748821","-5717071497243200830","-5203386547398257048","8024433324289741541","1006283721186468055","-1154468532710423927","-7521138167450157227","1030551515626467972","7645890680248364785","46468066683712475","-4841116615493929645","-4851604778193483217","-8497530232586410255","7834983483134792127","-3509972115907745433","8890786819571655920","-5002353768432936933","-1572879942741380215","-3639172622543746967","1973253577438245190","1082909509993304759","-4200301415412055546","-6205818220739187556","5350621122305172967","7288090154697672885","-2243342414546571629","-3369701836797122522","-1641541051180436930","-1811234530095396511","5463724884093935571","6387067584803736022","-3086496459115840375","-76767613666972237","-5396320318393687920","-3826857785444868592","4497181867107117274","7937424216006913936","5713575666682630154","3894467597277018095","2042971768032917110","5623672105538701777","-9154113209986796931","-8484825902098637163","-7277094534571268004","3235483685457413982","-8793625478481275671","8333498626553414197","-2177021210337342546","-1742505200634870211","4548821049804989393","4208649154372992971","5207891422381171471","8857618609496686589","422549006818654392","-4645819442282837140","-3467913149753564121","5281942482407033504","1711047736077528127","7915448925052074775","8498739335914676172","6818274023468475714","6529377851905072767","-1806221124545554774","-2016871142675178529"};
		for(int i=0;i<strs.length;i++)
		{
			String url="http://hbase.hubpd.co:9001/article/"+strs[i];
			ArrayList<header> list=new ArrayList<header>();
			list.add(new header("Accept", "application/json"));
			String msg=Pageinfo(url, list, "utf-8");
			
			if(msg.indexOf("timestamp")!=-1)
			{
				
			}else
			{
				
				System.out.println(strs[i]);
			}
		}
		
	}
	public static void test1()
	{
		String url="http://127.0.0.1:8080/tianji-web/caseAsssearch/apsearch";
		ArrayList<header> list=new ArrayList<header>();
		list.add(new header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36"));
		list.add(new header("Origin","http://127.0.0.1:8080"));
		list.add(new header("Referer","http://127.0.0.1:8080/tianji-web/welcome?rad1=on&rad1=&rad1="));
		list.add(new header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8"));
		list.add(new header("Cookie","tianji.session.id=76f87fe0ab6f444d95f45c85d0a1a427"));
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("searchword","三"));
		formparams.add(new BasicNameValuePair("sex","1"));
		formparams.add(new BasicNameValuePair("nation",""));
		formparams.add(new BasicNameValuePair("start_age","0"));
		formparams.add(new BasicNameValuePair("end_age","0"));
		formparams.add(new BasicNameValuePair("page","1"));
		formparams.add(new BasicNameValuePair("size","10"));
		String msg=Pageinfo(url, list, "utf-8", formparams);
		System.out.println(msg);
	}
	public static void test4()
	{
		String url="http://127.0.0.1:8080/tianji-web/caseAsssearch/chsearch";
		ArrayList<header> list=new ArrayList<header>();
		list.add(new header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36"));
		list.add(new header("Origin","http://127.0.0.1:8080"));
		list.add(new header("Referer","http://127.0.0.1:8080/tianji-web/welcome?rad1=on&rad1=&rad1="));
		list.add(new header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8"));
		list.add(new header("Cookie","tianji.session.id=a96842e16c60479fa997318daa5620f9"));
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("searchword","3次以上盗窃人员"));
		formparams.add(new BasicNameValuePair("size","10"));
		String msg=Pageinfo(url, list, "utf-8", formparams);
		System.out.println(msg);
	}
	public static void test5()
	{
		String url="http://127.0.0.1:8080/tianji-web/caseAsssearch/cpinfo";
		ArrayList<header> list=new ArrayList<header>();
		list.add(new header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36"));
		list.add(new header("Origin","http://127.0.0.1:8080"));
		list.add(new header("Referer","http://127.0.0.1:8080/tianji-web/welcome?rad1=on&rad1=&rad1="));
		list.add(new header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8"));
		list.add(new header("Cookie","tianji.session.id=76f87fe0ab6f444d95f45c85d0a1a427"));
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("rybh","R031"));
		String msg=Pageinfo(url, list, "utf-8", formparams);
		System.out.println(msg);
	}
	public static void test6()
	{
		String url="http://127.0.0.1:8080/tianji-web/caseAsssearch/csinfo";
		ArrayList<header> list=new ArrayList<header>();
		list.add(new header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36"));
		list.add(new header("Origin","http://127.0.0.1:8080"));
		list.add(new header("Referer","http://127.0.0.1:8080/tianji-web/welcome?rad1=on&rad1=&rad1="));
		list.add(new header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8"));
		list.add(new header("Cookie","tianji.session.id=e732677dd2b8484d89d9c68923dca935"));
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("ajbh","AJ0085"));
		String msg=Pageinfo(url, list, "utf-8", formparams);
		System.out.println(msg);
	}
	public static void test3()
	{
		String url="http://127.0.0.1:8080/tianji-web/caseAsssearch/casearch";
		ArrayList<header> list=new ArrayList<header>();
		list.add(new header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36"));
		list.add(new header("Origin","http://127.0.0.1:8080"));
		list.add(new header("Referer","http://127.0.0.1:8080/tianji-web/welcome?rad1=on&rad1=&rad1="));
		list.add(new header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8"));
		list.add(new header("Cookie","tianji.session.id=a96842e16c60479fa997318daa5620f9"));
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
		formparams.add(new BasicNameValuePair("searchword","腊梅家内衣店被盗案"));
		formparams.add(new BasicNameValuePair("ajlb",""));
		formparams.add(new BasicNameValuePair("sldw",""));
		formparams.add(new BasicNameValuePair("afdq","a"));
		formparams.add(new BasicNameValuePair("start_afsj",""));
		formparams.add(new BasicNameValuePair("end_afsj",""));
		formparams.add(new BasicNameValuePair("page","1"));
		formparams.add(new BasicNameValuePair("size","10"));
		String msg=Pageinfo(url, list, "utf-8", formparams);
		System.out.println(msg);
	}
	public static ArrayList<String> GetMidList(String html)
	{
		ArrayList<String> list=new ArrayList<String>();
		Pattern p = Pattern.compile("weibo.comp(.*?)from=faxian_huati");  
		Matcher mat = p.matcher(html);  
		HashMap<String, String> hm=new HashMap<String,String>();
		while(mat.find())
		{
			String mid=mat.group(0).replace("weibo.comp", "").replace("from=faxian_huati", "").replace("?", "");
			if(!hm.containsKey(mid))
			{
				list.add(mid);
				hm.put(mid, "");
			}
			
		}
		return list;
	}
	public static String Pageinfo(String pageUrl,ArrayList<header> list,String defaultCharset,String proxyIp,int proxyPort)
	{
		
        CloseableHttpClient httpclient = HttpClients.custom().build();
        try {
           
            
            HttpHost proxy = new HttpHost(proxyIp, proxyPort);

            RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
            HttpGet httpget = new HttpGet(pageUrl);
            httpget.setConfig(config);
            for(int i=0;i<list.size();i++)
    		{
    			header h=list.get(i);
    			httpget.setHeader(h.getName(),h.getValue());
    		}
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                
                return EntityUtils.toString(response.getEntity());
            } finally {
                response.close();
            }
        }  catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
        }
        return null;
	}
	
	public static String Pageinfomp(String url,ArrayList<header> list,String defaultCharset)
	{

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpPost=new HttpGet(url);
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		for(int i=0;i<list.size();i++)
		{
			header h=list.get(i);
			httpPost.setHeader(h.getName(),h.getValue());
		}
		
		
		String html="";
		try {
			CloseableHttpResponse response = httpclient.execute(httpPost);
			// Create a custom response handler
	        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	        	
	            public String handleResponse(
	                    final HttpResponse response) throws ClientProtocolException, IOException {
	                int status = response.getStatusLine().getStatusCode();
	                if (status >= 200 && status < 300) {
	                    HttpEntity entity = response.getEntity();
	                    return entity != null ? EntityUtils.toString(entity) : null;
	                } else {
	                    throw new ClientProtocolException("Unexpected response status: " + status);
	                }
	            }

	        };
			HttpEntity entitySort = response.getEntity();
			html=EntityUtils.toString(entitySort, defaultCharset);
			httpclient.close();
		} catch (ClientProtocolException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return html;	
	}
	public static String PageinfoChat(String url,ArrayList<header> list,String defaultCharset)
	{

		CloseableHttpClient httpclient=HttpClients.createDefault();
		HttpGet httpPost=new HttpGet(url);
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
		for(int i=0;i<list.size();i++)
		{
			header h=list.get(i);
			httpPost.setHeader(h.getName(),h.getValue());
		}
		String html="";
		try {
			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity entitySort = response.getEntity();
			html=EntityUtils.toString(entitySort, defaultCharset);
			httpclient.close();
		} catch (ClientProtocolException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return html;	
	}
	public static String Pageinfo(String url,ArrayList<header> list,String defaultCharset)
	{
		int timeout=1500;
		CloseableHttpClient httpclient=HttpClients.createDefault();
		HttpGet httpPost=new HttpGet(url);
		for(int i=0;i<list.size();i++)
		{
			header h=list.get(i);
			httpPost.setHeader(h.getName(),h.getValue());
		}
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH)
				.setRelativeRedirectsAllowed(true).setSocketTimeout(timeout).setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
		 config = RequestConfig.custom().build();
		httpPost.setConfig(config);
		String html="";
		try {
			CloseableHttpResponse response = httpclient.execute(httpPost);
			HttpEntity entitySort = response.getEntity();
			html=EntityUtils.toString(entitySort, defaultCharset);
			httpclient.close();
		} catch (ClientProtocolException e) {
			logger.error(e.toString());
			e.printStackTrace();
			return e.toString();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
			return e.toString();
		}
		return html;	
	}
	/**
	 * 构造的post请求方法体
	 * @param url 
	 * @param list
	 * @param defaultCharset
	 * @param formparams
	 * @return
	 */
	public static String Pageinfo(String url,ArrayList<header> list,String defaultCharset,List<NameValuePair> formparams)
	{
		CloseableHttpClient httpclient=HttpClients.createDefault();
		HttpPost httpPost=new HttpPost(url);
		for(int i=0;i<list.size();i++)
		{
			header h=list.get(i);
			httpPost.setHeader(h.getName(),h.getValue());
		}
		int timeout=15000;
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.BEST_MATCH)
				.setRelativeRedirectsAllowed(true).setSocketTimeout(timeout).setConnectTimeout(timeout)
				.setConnectionRequestTimeout(timeout).setStaleConnectionCheckEnabled(true).build();
		httpPost.setConfig(config);
		UrlEncodedFormEntity uefEntity;
		String html="";
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "utf-8");
			httpPost.setEntity(uefEntity);   
	         HttpResponse response =  httpclient.execute(httpPost);
	         int code=response.getStatusLine().getStatusCode();
	         HttpEntity entity = response.getEntity();  
	          //对返回的数据设置编码格式
	        // System.out.println(code);
	         if(code==200||code==301)
	         {
	        	 html=EntityUtils.toString(entity, defaultCharset);
	        }} catch (UnsupportedEncodingException e) {
	 			e.printStackTrace();
	 		}  //加入参数
	 			catch (ClientProtocolException e) {
	 			e.printStackTrace();
	 		} catch (IOException e) {
	 			e.printStackTrace();
	 		}finally {
	 			try {
					httpclient.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return html;	
	}
	public  static String PageinfoWithJSON(String url,ArrayList<header> list,String defaultCharset,String str) 
	{
		CloseableHttpClient httpclient=HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
		for(int i=0;i<list.size();i++)
		{
			header h=list.get(i);
			httpPost.setHeader(h.getName(),h.getValue());
		}
        String respContent = null;
        StringEntity entity = new StringEntity(str,"utf-8");
        entity.setContentEncoding(defaultCharset); 
        httpPost.setEntity(entity);
        HttpResponse resp;
		try {
			resp = httpclient.execute(httpPost);
			 if(resp.getStatusLine().getStatusCode() == 200) {
		            HttpEntity he = resp.getEntity();
		            respContent = EntityUtils.toString(he,"UTF-8");
		        }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
        return respContent;
    }
}
