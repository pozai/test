/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.web;

import org.apache.commons.lang.StringUtils;


/**
 * description:request常用的一些操作
 * 
 * @author huaye
 * @version 1.0
 * @date 2012-7-10
 */
public class FilterHelper {

	
	/**
	 * 过虑特殊字符
	 * @param strSrc
	 * @return
	 */
	public static String replaceSpeicalChar(String strSrc) {

		if (StringUtils.isEmpty(strSrc)) {
			return StringUtils.EMPTY;
		}
		
		String strDest = strSrc;
		
		// 替换html标签
		strDest = strDest.replace("<", "&lt;");
		strDest = strDest.replace(">", "&gt;");
		strDest = strDest.replace("\r\n", "<br/>");
		strDest = strDest.replace("\n", "<br/>");
		
		// 将半角字符替换成全角字符
		strDest = strDest.replace("'", "‘");
		strDest = strDest.replace("\\|", "");
		//strDest = strDest.replace("$", "＄");
		strDest = strDest.replace("\\", "、");
 
		return strDest;
	} 
	
	
	/**
	 * 清除html标签
	 * @param htmlString
	 * @return
	 */
    public static String trimHtmlTag(String htmlString){
    	
		if (StringUtils.isEmpty(htmlString)) {
			return StringUtils.EMPTY;
		}
    	
          // Remove HTML tag from java String    
        String noHTMLString = htmlString.replaceAll("\\<.*?\\>", "");

        // Remove Carriage return from java String
        noHTMLString = noHTMLString.replaceAll("\r", "<br/>");

        // Remove New line from java string and replace html break
        noHTMLString = noHTMLString.replaceAll("\n", " ");
        noHTMLString = noHTMLString.replaceAll("\'", "&#39;");
        noHTMLString = noHTMLString.replaceAll("\"", "&quot;");
        return noHTMLString;
    }
    
    /**
     * 剔除所有的URL链接
     * @param strSource
     * @return
     */
    public static String trimUrl(String strSource){
    	
    	if(StringUtils.isEmpty(strSource)){return StringUtils.EMPTY;}
    	// 替换ubb 代码
    	String ubbURLPattern = "(?i)\\[url=([^\\]]+)\\][^\\[]*?\\[\\/url\\]";
    	String resultString = strSource.replaceAll(ubbURLPattern, "");
    	// 替换正常URL
    	String pattern = "(?i)(https?|ftp|file)://[-A-Z0-9+&@#/%?=~_|!:,.;]*[-A-Z0-9+&@#/%=~_|]";
    	resultString = resultString.replaceAll(pattern, "");
    	return resultString;
    }
	
	/**
	 * 进行各种数字表现形式的替换
	 * @param string
	 * @return
	 */
	public static String regularizeNumber(String string){
		
		if (StringUtils.isEmpty(string)) {
			return StringUtils.EMPTY;
		}
		
		// 重点封杀：QQ号、YY号、Email、网址
		
		// 软键盘输入
		String[] numString = new String[8];
		numString[0] = "①②③④⑤⑥⑦⑧⑨⑩"; 
		numString[1] = "⑴⑵⑶⑷⑸⑹⑺⑻⑼⑽⑾⑿⒀⒁⒂⒃⒄⒅⒆⒇";
		numString[2] = "㈠㈡㈢㈣㈤㈥㈦㈧㈨㈩" ;
		numString[3] = "ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩⅪⅫ" ;
		numString[4] = "⒈⒉⒊⒋⒌⒍⒎⒏⒐⒑⒒⒓⒔⒕⒖⒗⒘⒙⒚⒛"; 
		numString[5] = "０１２３４５６７８９";
		numString[6]=  "〇一二三四五六七八九" ;
		numString[7] = "零壹贰叁肆伍陆柒捌玖";
		
		String result = string;
		for (int i = 0; i < numString.length; i++) {
			for(int pos=0;pos<numString[i].length();pos++){
				String  val = i <5 ? String.valueOf(pos + 1) :String.valueOf(pos); 
				result = result.replace(String.valueOf(numString[i].charAt(pos)), val);
			}
		}
		
		return result;
	}
	
	/**
	 * 把全角的英文、数字转化成半角
	 * @param string
	 * @return
	 */
	public static String regularizeLetter(String string){
		
		if (StringUtils.isEmpty(string)) {
			return StringUtils.EMPTY;
		}
		
		// 全角字符串
		String sbcString = "０１２３４５６７８９ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ";
		String dbcString = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		String result = string;
		for (int i = 0; i < sbcString.length(); i++) {
			char srcChar = sbcString.charAt(i);
			char destChar = dbcString.charAt(i);
			result = result.replace(srcChar, destChar);
		}
		
		return result;
	}
	
	/**
	 * 判断是否含有sql注入
	 * @param str
	 * @return
	 */
	public static boolean isSqlInject(String str) {
		
		if (StringUtils.isEmpty(str)) {
			return false;
		}
		
		// JDK1.5的API的例子是用的“:”分隔的，所以我也用这个符号分隔，就可以把每个字符串拆分开来。
		String inj_str = "':and:exec:insert:select:delete:update:count:*:%:chr:mid:master:truncate:char:declare:;:or:-:+:,";
		String inj_stra[] = inj_str.split(":");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) != -1) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 过虑特殊字符
	 * @param strSrc
	 * @return
	 */
	public static String filterString(String strSrc) {

		if (StringUtils.isEmpty(strSrc)) {
			return StringUtils.EMPTY;
		}
		
		String strDest = replaceSpeicalChar(strSrc);		
		
		return strDest;
	}
	
	/**
	 * 替换非中文、英文、数字字符
	 * @return
	 */
	public static String regularizeString(String strSource){
		
		if (StringUtils.isEmpty(strSource)) {
			return StringUtils.EMPTY;
		}
		
		String resultString = strSource.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5]", "");
		return resultString;
	}
	
	
	/**
	 * 替换中文字符
	 * @return
	 */
	public static String getChinsesString(String strSource){
		
		if (StringUtils.isEmpty(strSource)) {
			return StringUtils.EMPTY;
		}
		
		String resultString = strSource.replaceAll("[^\u4e00-\u9fa5]", "");
		return resultString;
	}
	
	public static String getNumberString(String strSource){
		
		if (StringUtils.isEmpty(strSource)) {
			return StringUtils.EMPTY;
		}
		
		String resultString = strSource.replaceAll("[^0-9]", "");
		return resultString;
	}
	
	public static String getEnglishString(String strSource){
		
		if (StringUtils.isEmpty(strSource)) {
			return StringUtils.EMPTY;
		}
		
		String resultString = strSource.replaceAll("[^a-zA-Z]", "");
		return resultString;
	}
 
	
	public static void main(String[] args) {

		String todo = "⑴⑵⑷⑸⑹⑺⑻⑼⑽⑴㈠㈡㈢㈤㈥㈦㈧㈨㈩㈩⒈⒊⒋⒌⒎⒏⒑ⅡⅢⅤⅥⅧⅨⅪⅫ⒈ⅠⅠⅠⅠⅠⅡⅢⅣⅤⅥⅦⅩⅪⅫ⒈⒊⒋⒏⒑⒑⒐㈤㈢㈠㈡①㈠㈠㈠㈠㈠①①②③③④⑤⑥⑥⑦⑧⑨⑨⑩⑩⑧⑤⑾⑾⑿⒀⒁⒅⒆⒆⒆⑴⑵⑶⑸⑹⑺㈦⑽⑽⑽⑼⑼㈥";
		String result = FilterHelper.regularizeNumber(todo);
		System.out.println(todo);
		System.out.println(result);
		
	
		String temp = FilterHelper.trimUrl("http://www.notrock.com/&gt; private health insurance &lt;/a&gt;  [url=http://www.notrock.com/] private health insurance [/url]  http://www.notrock.com/ ");
		System.out.println(temp);
		
		
		String hello = "１８５９８０６５６　ｔｈｉｓ　ａ　ｗｏｒｌｄ　ｗｉｌｌ　ｕｎｄｅｒ　ｍｙ　ｃｏｎｔｒｏｌ．ｈａｈａ　ｙｏｕ　ｍｕｓｔ　ｂｅ　ａｆｒａｉｄ　ｎｏｗ．" +
				"ｍａｙｂｅ　ｙｏｕ　ｔｈｉｎｋ　ｔｈｉｓ　ａｓ　ａ　ｊｏｋｉｎｇ．Ｉ＇ｍ　ｒｅａｌｌｙ　ｍｅａｎ　ｉｔ．Ｄｅａn．ＧＯＤ　ＨＥＬＰ　ＭＥ！" +
				"ＷＨＯ　ＷＩＬＬ　ＳＡＶＥ　ＭＥ．ＣＯＤＥＲ　ＷＩＬＬ　ＮＯＴ　ＥＳＣＡＰＥ　ＦＯＲＭ　ＴＨＩＳ　ＦＵＣＫ　ＬＩＦＥ．ＨＥ　ＭＵＳＴ　ＷＯＲＫ　ＥＶＥＲＹ　ＤＡＹ　" +
				"ＥＶＥＲＹ　ＮＩＧＨＴ　ＧＯＯＤ　ＴＥＳＴ　";
		String destHello = FilterHelper.regularizeLetter(hello) ;
		System.out.println(destHello);
	}
}
