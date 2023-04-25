package com.util;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream; 
import java.util.Iterator;
import java.util.Properties; 
public class PropertyUtil {
	public static String getvalue(String name) { 
	        Properties prop = new Properties();     
	        try{
	            //读取属性文件config.properties
	            InputStream in = new BufferedInputStream (new FileInputStream("config.properties"));
	            prop.load(in);     ///加载属性列表
	            Iterator<String> it=prop.stringPropertyNames().iterator();
	            while(it.hasNext()){
	                String key=it.next();
	                if(name.equals(key))
	                {
	                	return prop.getProperty(key);
	                }
	            }
	            in.close();
	        }catch(Exception e){
	            System.out.println(e);
	        }
	        return null;
	    } 
	 public static void main(String[] args)
	 {
		 System.out.println(getvalue("miaopai_FFMPEGEpath"));
	 }
}
