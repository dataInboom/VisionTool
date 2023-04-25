package com.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.net.ChromeDriverProxy;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.map.MapBuilder;

public class WebDriverTool {
	public ChromeDriverProxy getWebDriver() {

		System.setProperty("webdriver.chrome.driver", com.util.PropertyUtil.getvalue("chromedriver"));// 指定驱动路径
		

		// 设置浏览器参数
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		options.addArguments("user-data-dir=C:/Users/Administrator/AppData/Local/Google/Chrome/zhihu");
		//options.addArguments("user-data-dir=C:/Users/10694/AppData/Local/Google/Chrome/zhihu");
		//options.addArguments("user-data-dir=C:/Users/10694/AppData/Local/Google/Chrome/zhihu_captcha");
		/**
		excludeSwitches", Arrays.asList("enable-automation")在高版本的谷歌浏览器是无法屏蔽
		window.navigator.webdriver 为false 的特征，这里写出来是为了配合其他参数来关闭浏览器上显示"正在收到自动测试软件控制"的提示
		**/
		options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
		options.addArguments("--disable-blink-features");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("useAutomationExtension", false);

		options.setExperimentalOption("prefs", prefs);
		
		// 创建驱动对象
		//WebDriver driver = new ChromeDriver(options);
		ChromeDriverProxy driver=new ChromeDriverProxy(options);
		driver.manage().window().setSize(new Dimension(1280, 1024));
		
		// 去除seleium全部指纹特征
		FileReader fileReader = new FileReader("C:\\stealth.js");
		String js = fileReader.readString();
		// MapBuilder是依赖hutool工具包的api
		Map<String, Object> commandMap = MapBuilder.create(new LinkedHashMap<String, Object>()).put("source", js)
				.build();
		// executeCdpCommand这个api在selenium3中是没有的,请使用selenium4才能使用此api
		((ChromeDriver) driver).executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", commandMap);
		return driver ;
	}
	/**
	 * 获取web驱动
	 * 
	 * @return 浏览器驱动
	 */
	public WebDriver getWebDriver(String username) {

		System.setProperty("webdriver.chrome.driver", com.util.PropertyUtil.getvalue("chromedriver"));// 指定驱动路径
		// 设置浏览器参数
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_enabled", false);
		options.addArguments("user-data-dir=C:\\chrome\\twitter_"+username);
		/**
		excludeSwitches", Arrays.asList("enable-automation")在高版本的谷歌浏览器是无法屏蔽
		window.navigator.webdriver 为false 的特征，这里写出来是为了配合其他参数来关闭浏览器上显示"正在收到自动测试软件控制"的提示
		**/
		options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
		options.addArguments("--disable-blink-features");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("useAutomationExtension", false);
		//options.addArguments("blink-settings=imagesEnabled=false");
		options.setExperimentalOption("prefs", prefs);
		
		// 创建驱动对象
		WebDriver driver = new ChromeDriver(options);
		//ChromeDriverProxy driver=new ChromeDriverProxy(options);
		driver.manage().window().setSize(new Dimension(1280, 1024));
		
		// 去除seleium全部指纹特征
		FileReader fileReader = new FileReader("C:\\stealth.js");
		String js = fileReader.readString();
		// MapBuilder是依赖hutool工具包的api
		Map<String, Object> commandMap = MapBuilder.create(new LinkedHashMap<String, Object>()).put("source", js)
				.build();
		// executeCdpCommand这个api在selenium3中是没有的,请使用selenium4才能使用此api
		((ChromeDriver) driver).executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", commandMap);
		return driver ;
	}
	/**
	 * 获取web驱动
	 * 
	 * @return 浏览器驱动
	 */
	public WebDriver getWebDriver(int task) {

		System.setProperty("webdriver.chrome.driver", com.util.PropertyUtil.getvalue("chromedriver"));// 指定驱动路径
		// 设置浏览器参数
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("profile.password_manager_enabled", false);
		//options.addArguments("user-data-dir=C:/Users/Administrator/AppData/Local/Google/Chrome/zhihu"+task);
		options.addArguments("user-data-dir=C:/zhihu/Chrome"+task);
		//options.addArguments("--headless"); //无浏览器模式
       // options.addArguments("--no-sandbox");// 为了让root用户也能执行
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		/**
		excludeSwitches", Arrays.asList("enable-automation")在高版本的谷歌浏览器是无法屏蔽
		window.navigator.webdriver 为false 的特征，这里写出来是为了配合其他参数来关闭浏览器上显示"正在收到自动测试软件控制"的提示
		**/
		options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
		options.addArguments("--disable-blink-features");
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("useAutomationExtension", false);
		//options.addArguments("blink-settings=imagesEnabled=false");//图片不加载
		//options.addArguments("blink-settings=imagesEnabled=false");
		options.setExperimentalOption("prefs", prefs);
		
		// 创建驱动对象
		WebDriver driver = new ChromeDriver(options);
		//ChromeDriverProxy driver=new ChromeDriverProxy(options);
		driver.manage().window().setSize(new Dimension(1903, 1024));
		
		// 去除seleium全部指纹特征
		FileReader fileReader = new FileReader("C:\\zhihu\\stealth.js");
		String js = fileReader.readString();
		// MapBuilder是依赖hutool工具包的api
		Map<String, Object> commandMap = MapBuilder.create(new LinkedHashMap<String, Object>()).put("source", js)
				.build();
		// executeCdpCommand这个api在selenium3中是没有的,请使用selenium4才能使用此api
		((ChromeDriver) driver).executeCdpCommand("Page.addScriptToEvaluateOnNewDocument", commandMap);
		return driver ;
	}
}
