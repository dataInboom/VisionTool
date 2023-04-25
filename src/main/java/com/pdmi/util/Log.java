package com.pdmi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	
	private final static Logger logger = LoggerFactory.getLogger(Log.class);
	
	public static void i(String log){
		logger.info(log);
	}
	public static void d(String log){
		logger.debug(log);
	}
	public static void w(String log){
		logger.warn(log);
	}
	public static void e(String log){
		logger.error(log);
	}
	
	public static void i(String log, Exception e){
		logger.info(log, e);
	}
	public static void d(String log, Exception e){
		logger.debug(log, e);
	}
	public static void w(String log, Exception e){
		logger.warn(log, e);
	}
	public static void e(String log, Exception e){
		logger.error(log, e);
	}
	
}
