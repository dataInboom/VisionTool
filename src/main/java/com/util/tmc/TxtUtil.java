package com.util.tmc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtUtil {
	public static String replaceBlank(String str) {
		String dest ="";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		dest=dest.replaceAll("&nbsp;", "").replaceAll("&gt;","").replaceAll("&lt;","").replaceAll("&quot;", "").replaceAll("&middot;", "");
		dest=dest.replaceAll(" ","").replaceAll("	","").replaceAll(" ","");
		return dest;
	}
}
