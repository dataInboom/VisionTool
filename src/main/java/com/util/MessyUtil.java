package com.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 识别中文，识别乱码
 * 
 * @author Administrator
 *
 */
public class MessyUtil {
	 /**
     * 判断字符是否是中文
     *
     * @param c 字符
     * @return 是否是中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
    /**
     * 判断文章是否是中文
     * 按比例算
     * @param strName 字符串
     * @return true 中文  false 非中文
     */
    public static boolean isChinese(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
                if (isChinese(c)) {
                    count = count + 1; //统计中文字数数量
                }
        }
        float result = count / chLength;
        if (result > 0.2) {
            return true;
        } else {
            return false;
        }
 
    }
    /**
     * 判断字符串是否是乱码
     *
     * @param strName 字符串
     * @return 是否是乱码
     */
    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }
 
    }
 
    public static void main(String[] args) {
        System.out.println(isMessyCode("다. 국민들에게 읊조리며 ‘당선되면 고통받는 사람들 편에 서겠다’던 후보자들이 흘렸던 눈물은 악어의 눈물에 불과했다.\r\n필자는 20대 국회의원 선거 전날까지 우리 지역에 누가 출마하는지 잘 몰랐다. 길거리에서 "));
        System.out.println(isMessyCode("Ë¯Ç°Ô­Ïë×ÅÓê²»´ó£¬Ã»¹Ø´°£¬Ã»Ïëµ½°é×ÅÓêÉùÕâÃ´ºÃË¯£¬Ò»¾õÐÑÀ´£¬·¿±»ÑÍÁË¡£ÔÙÒ»¾õÐÑÀ´£¬Õû¸ö³ÇÊÐ¶¼±»Ñ"));
    }
}
