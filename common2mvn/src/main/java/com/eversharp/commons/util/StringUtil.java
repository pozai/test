/*
 *All right reserved.
 *Copyright(C) 2012 www.eversharp.cn
 */
package com.eversharp.commons.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description:字符串操作常用 类
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
public abstract class StringUtil {

	
	public static Integer getByteLength(String str){
		if(str == null){return 0;}
		try {
			byte[] a = str.getBytes("GBK");
			return a.length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 是否是数字类型
	 */
	public static boolean isNumeric(String str) {
		if (str == null) return false;

		int sz = str.length();
		for (int i = 0; i < sz; i++)
			if (Character.isDigit(str.charAt(i)) == false) return false;
		return true;
	}

	/**
	 * 用指定的字符来替换空白字符
	 * 
	 * @param src
	 * @param ch
	 * @return
	 */
	public static String replaceWhitespace(String src, char ch) {
		if (src == null) return null;
		char[] text = src.toCharArray();
		for (int i = 0; i < text.length; i++)
			if (Character.isWhitespace(text[i])) text[i] = ch;
		return String.copyValueOf(text);
	}

	/**
	 * 切分为数组。
	 * 
	 * @param fieldValue
	 * @param string
	 * @return
	 */
	public static List<String> split2List(String text, String split) {
		final ArrayList<String> list = new ArrayList<String>();
		if (StringUtil.hasText(text)) return null;

		for (String o : text.split(split))
			list.add(o);

		return list;
	}

	/**
	 * 将数组转化成字符串
	 * 
	 * @param list
	 * @param split 分隔符
	 * @param quote 是否加单引号
	 * @return
	 */
	public static String join(Object[] list, String split, boolean quote) {
		if (list == null) return "";
		if (split == null) split = "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.length; i++) {
			if (i > 0) sb.append(split);
			if (quote) sb.append("'");
			sb.append(list[i].toString());
			if (quote) sb.append("'");
		}
		return sb.toString();
	}

	/**
     * 编码的URL参数值
     * 
     * @param src
     * @return
     */
    public static String EncodeUrlPara(String src) {
        try {
            return java.net.URLEncoder.encode(src, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }
	
	/**
	 * 解开被编码的URL参数值
	 * 
	 * @param src
	 * @return
	 */
	public static String DecodeUrlPara(String src) {
		try {
			return java.net.URLDecoder.decode(src, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 长度不为0，可包含空格
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * 长度不为0，可包含空格
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasLength(String str) {
		return hasLength((CharSequence) str);
	}

	/**
	 * 是否有文本字段
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() < 1;
	}
	
	/**
	 * 判断对象是否为空. 现支持String,Long,Integer
	 * 
	 * @Description 如果是字符串所判断是否为空, 如果是数值型则判断是否为null或0
	 * @param obj
	 *            传进来的对象
	 * @return 如果为空返回false，不为空废话true
	 */
	public static boolean isEmpty2(Object obj) {
		if (obj instanceof String) {
			return (obj == null || obj.toString().trim().length() == 0);
		}
		if (obj instanceof Long) {
			return (obj == null || (Long) obj == 0);
		}
		if (obj instanceof Integer) {
			return (obj == null && (Integer) obj == 0);
		}
		return obj == null;
	}
	

	/**
	 * 是否有文本字段
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) { return false; }
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) { return true; }
		}
		return false;
	}

	/**
	 * 是否有文本字段
	 * 
	 * @param str
	 * @return
	 */
	public static boolean hasText(String str) {
		return hasText((CharSequence) str);
	}

	/**
	 * 是否包含空格
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containsWhitespace(CharSequence str) {
		if (!hasLength(str)) { return false; }
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(str.charAt(i))) { return true; }
		}
		return false;
	}

	/**
	 * 是否包含空格
	 * 
	 * @param str
	 * @return
	 */
	public static boolean containsWhitespace(String str) {
		return containsWhitespace((CharSequence) str);
	}

	/**
	 * 去掉所有的空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trimAllWhitespace(String str) {
		if (!hasLength(str)) { return str; }
		StringBuilder sb = new StringBuilder(str);
		int index = 0;
		while (sb.length() > index) {
			if (Character.isWhitespace(sb.charAt(index))) {
				sb.deleteCharAt(index);
			} else {
				index++;
			}
		}
		return sb.toString();
	}

	/**
	 * 去掉头部空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trimLeadingWhitespace(String str) {
		if (!hasLength(str)) { return str; }
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * 去除尾部空白字符
	 * 
	 * @param str
	 * @return
	 */
	public static String trimTailingWhitespace(String str) {
		if (!hasLength(str)) { return str; }
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 去除前面的字符串
	 * 
	 * @param str
	 * @param leadingCharacter
	 * @return
	 */
	public static String trimLeadingCharacter(String str, char leadingCharacter) {
		if (!hasLength(str)) { return str; }
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
			sb.deleteCharAt(0);
		}
		return sb.toString();
	}

	/**
	 * 忽略大小写的startswith
	 * 
	 * @param str
	 * @param prefix
	 * @return
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) { return false; }
		if (str.startsWith(prefix)) { return true; }
		if (str.length() < prefix.length()) { return false; }
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	/**
	 * 忽略大小写的endswith
	 * 
	 * @param str
	 * @param suffix
	 * @return
	 */
	public static boolean endsWithIgnoreCase(String str, String suffix) {
		if (str == null || suffix == null) { return false; }
		if (str.endsWith(suffix)) { return true; }
		if (str.length() < suffix.length()) { return false; }

		String lcStr = str.substring(str.length() - suffix.length()).toLowerCase();
		String lcSuffix = suffix.toLowerCase();
		return lcStr.equals(lcSuffix);
	}

	/**
	 * 字符串替换
	 * 
	 * @param inString
	 * @param oldPattern
	 * @param newPattern
	 * @return
	 */
	public static String replace(String inString, String oldPattern, String newPattern) {
		if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) { return inString; }
		StringBuilder sb = new StringBuilder();
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sb.append(inString.substring(pos, index));
			sb.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sb.append(inString.substring(pos));
		// remember to append any characters to the right of a match
		return sb.toString();
	}

	/**
	 * 删除字符串
	 * 
	 * @param inString
	 * @param pattern
	 * @return
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	/**
	 * 为字符串加引号
	 * 
	 * @param str
	 * @return
	 */
	public static String quote(String str) {
		return (str != null ? "'" + str + "'" : null);
	}

	/**
	 * 添加字符串到数组
	 * 
	 * @param array
	 * @param str
	 * @return
	 */
	public static String[] addStringToArray(String[] array, String str) {
		if (ObjectUtil.isEmpty(array)) { return new String[] { str }; }
		String[] newArr = new String[array.length + 1];
		System.arraycopy(array, 0, newArr, 0, array.length);
		newArr[array.length] = str;
		return newArr;
	}

	/**
	 * 合并字符串数组
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static String[] mergeStringArrays(String[] array1, String[] array2) {
		if (ObjectUtil.isEmpty(array1)) { return array2; }
		if (ObjectUtil.isEmpty(array2)) { return array1; }
		List<String> result = new ArrayList<String>();
		result.addAll(Arrays.asList(array1));
		for (String str : array2) {
			if (!result.contains(str)) {
				result.add(str);
			}
		}
		return toStringArray(result);
	}

	/**
	 * 字符串数组的排序
	 * 
	 * @param array
	 * @return
	 */
	public static String[] sortStringArray(String[] array) {
		if (ObjectUtil.isEmpty(array)) { return new String[0]; }
		Arrays.sort(array);
		return array;
	}

	/**
	 * 转换为字符串数组
	 * 
	 * @param collection
	 * @return
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) { return null; }
		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * 转换为字符串数组
	 * 
	 * @param collection
	 * @return
	 */
	public static String[] toStringArray(Enumeration<String> enumeration) {
		if (enumeration == null) { return null; }
		List<String> list = Collections.list(enumeration);
		return list.toArray(new String[list.size()]);
	}

	/**
	 * 字符串以指定的字符串转为数组
	 * 
	 * @param toSplit
	 * @param delimiter
	 * @return
	 */
	public static String[] split(String toSplit, String delimiter) {
		if (!hasLength(toSplit) || !hasLength(delimiter)) { return null; }
		int offset = toSplit.indexOf(delimiter);
		if (offset < 0) { return null; }
		String beforeDelimiter = toSplit.substring(0, offset);
		String afterDelimiter = toSplit.substring(offset + delimiter.length());
		return new String[] { beforeDelimiter, afterDelimiter };
	}

	/**
	 * 为javascript加上可转义的字符
	 * 
	 * @param input
	 * @return
	 */
	public static String javaScriptEscape(String input) {
		if (input == null) { return input; }

		StringBuilder filtered = new StringBuilder(input.length());
		char prevChar = '\u0000';
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (c == '"') {
				filtered.append("\\\"");
			} else if (c == '\'') {
				filtered.append("\\'");
			} else if (c == '\\') {
				filtered.append("\\\\");
			} else if (c == '/') {
				filtered.append("\\/");
			} else if (c == '\t') {
				filtered.append("\\t");
			} else if (c == '\n') {
				if (prevChar != '\r') {
					filtered.append("\\n");
				}
			} else if (c == '\r') {
				filtered.append("\\n");
			} else if (c == '\f') {
				filtered.append("\\f");
			} else {
				filtered.append(c);
			}
			prevChar = c;

		}
		return filtered.toString();
	}

	/**
	 * 从具体位置开始匹配字符串
	 * 
	 * @param str
	 * @param index
	 * @param substring
	 * @return
	 */
	public static boolean containsString(String str, String substring) {
		if (str.contains(substring)) { return true; }

		return false;
	}

	/**
	 * 去掉尾部的字符串
	 * 
	 * @param str
	 * @param tailString
	 * @return
	 */
	public static String removeTailingString(String str, String tailString) {
		if (endsWithIgnoreCase(str, tailString)) { return str.substring(0, str.lastIndexOf(tailString)); }

		return str;
	}

	/**
	 * 去掉头部字符串
	 * 
	 * @param str
	 * @param leadString
	 * @return
	 */
	public static String removeLeadingString(String str, String leadString) {
		if (startsWithIgnoreCase(str, leadString)) { return str.substring(leadString.length()); }

		return str;
	}
	
	/** 
     * @param src 
     *            源字符串 
     * @return 字符串，将src的第一个字母转换为大写，src为空时返回null 
     */  
    public static String changeFirstUpperCase(String src) {  
        if (src != null) {  
            StringBuffer sb = new StringBuffer(src);  
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));  
            return sb.toString();  
        } else {  
            return null;  
        }  
    } 
    
    /**
     * 若字符串为空，则转换为“”
     * @param text
     * @return
     */
    public static String formatString(String text){ 
        if(text == null) {
            return ""; 
        }
        return text;
    }
    
    /**
     * 若字符串为空，则转换为“”
     * @param text
     * @return
     */
    public static String changeCodingFormat(String text, String oldCoding, String newCoding){ 
        
        try {
            if(isEmpty(text)){
                return "";
            }
            return new String(text.getBytes(oldCoding),newCoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return text;
        }
    }
    
    /**
     * 若字符串为空，则转换为“”
     * @param text
     * @return
     */
    public static byte[] getStringByte(String text, String coding){ 
        
        try {
            if(isEmpty(text)){
                return null;
            }
            return text.getBytes(coding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将字符串转换为int整数
     * @param str
     * @return
     */
    public static int getIntFromString(String str){ 
        try {
            if(isEmpty(str)){
                return 0;
            }
            return Integer.parseInt(str);
        } catch (Exception e) {
            return -1;
        }
    }
    
    /**
     * 将字符串转换为BigDecimal整数
     * @param str
     * @return
     */
    public static BigDecimal getBigDecimalFromString(String str){ 
        try {
            if(isEmpty(str)){
                return null;
            }
            return new BigDecimal(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 将字符串转换为long整数
     * @param str
     * @return
     */
    public static long getLongFromString(String str){ 
        try {
            if(isEmpty(str)){
                return 0;
            }
            return Long.parseLong(str);
        } catch (Exception e) {
            return -1;
        }
    }
    
    /**
     * 将字符串转换为float浮点数
     * @param str
     * @return
     */
    public static float getFloatFromString(String str){ 
        try {
            if(isEmpty(str)){
                return 0;
            }
            return Float.parseFloat(str);
        } catch (Exception e) {
            return 0.00f;
        }
    }

    /**
     * 是否浮点数
     * @param str
     * @return
     */
    public static boolean isDouble(String str) {

        Pattern pattern = Pattern.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");

        return pattern.matcher(str).matches();

      }
    
    /** 
     * 把用户输入的数以小数点为界分割开来，并调用 numFormat() 方法 
     * 进行相应的中文金额大写形式的转换 
     * 注：传入的这个数应该是经过 roundString() 方法进行了四舍五入操作的 
     * @param s String 
     * @return 转换好的中文金额大写形式的字符串 
     */  
    public static String splitNum(String s) {  
        // 如果传入的是空串则继续返回空串  
        if("".equals(s)) {  
            return "";  
        }  
        // 以小数点为界分割这个字符串  
        int index = s.indexOf(".");  
        // 截取并转换这个数的整数部分  
        String intOnly = s.substring(0, index);  
        String part1 = numFormat(1, intOnly);  
        // 截取并转换这个数的小数部分  
        String smallOnly = s.substring(index + 1);  
        String part2 = numFormat(2, smallOnly);  
        // 把转换好了的整数部分和小数部分重新拼凑一个新的字符串  
        String newS = part1 + part2;  
        return newS;  
    }  
    
    /** 
     * 把传入的数转换为中文金额大写形式 
     * @param flag int 标志位，1 表示转换整数部分，0 表示转换小数部分 
     * @param s String 要转换的字符串 
     * @return 转换好的带单位的中文金额大写形式 
     */  
    public static String numFormat(int flag, String s) {  
        int sLength = s.length();  
        // 货币大写形式  
        String bigLetter[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};  
        // 货币单位  
        String unit[] = {"元", "拾", "佰", "仟", "万",   
                // 拾万位到仟万位  
                "拾", "佰", "仟",  
                // 亿位到万亿位  
                "亿", "拾", "佰", "仟", "万"};  
        String small[] = {"分", "角"};  
        // 用来存放转换后的新字符串  
        String newS = "";  
        // 逐位替换为中文大写形式  
        for(int i = 0; i < sLength; i ++) {  
            if(flag == 1) {  
                // 转换整数部分为中文大写形式（带单位）  
                newS = newS + bigLetter[s.charAt(i) - 48] + unit[sLength - i - 1];  
            } else if(flag == 2) {  
                // 转换小数部分（带单位）  
                newS = newS + bigLetter[s.charAt(i) - 48] + small[sLength - i - 1];  
            }  
        }  
        return newS;  
    } 
    
    
    /** 
     * 把已经转换好的中文金额大写形式加以改进，清理这个字 
     * 符串里面多余的零，让这个字符串变得更加可观 
     * 注：传入的这个数应该是经过 splitNum() 方法进行处理，这个字 
     * 符串应该已经是用中文金额大写形式表示的 
     * @param s String 已经转换好的字符串 
     * @return 改进后的字符串 
     */  
    private String cleanZero(String s) {  
        // 如果传入的是空串则继续返回空串  
        if("".equals(s)) {  
            return "";  
        }  
        // 如果用户开始输入了很多 0 去掉字符串前面多余的'零'，使其看上去更符合习惯  
        while(s.charAt(0) == '零') {  
            // 将字符串中的 "零" 和它对应的单位去掉  
            s = s.substring(2);  
            // 如果用户当初输入的时候只输入了 0，则只返回一个 "零"  
            if(s.length() == 0) {  
                return "零";  
            }  
        }  
        // 字符串中存在多个'零'在一起的时候只读出一个'零'，并省略多余的单位  
        /* 由于本人对算法的研究太菜了，只能用4个正则表达式去转换了，各位大虾别介意哈... */  
        String regex1[] = {"零仟", "零佰", "零拾"};  
        String regex2[] = {"零亿", "零万", "零元"};  
        String regex3[] = {"亿", "万", "元"};  
        String regex4[] = {"零角", "零分"};  
        // 第一轮转换把 "零仟", 零佰","零拾"等字符串替换成一个"零"  
        for(int i = 0; i < 3; i ++) {  
            s = s.replaceAll(regex1[i], "零");  
        }  
        // 第二轮转换考虑 "零亿","零万","零元"等情况  
        // "亿","万","元"这些单位有些情况是不能省的，需要保留下来  
        for(int i = 0; i < 3; i ++) {  
            // 当第一轮转换过后有可能有很多个零叠在一起  
            // 要把很多个重复的零变成一个零  
            s = s.replaceAll("零零零", "零");  
            s = s.replaceAll("零零", "零");  
            s = s.replaceAll(regex2[i], regex3[i]);  
        }  
        // 第三轮转换把"零角","零分"字符串省略  
        for(int i = 0; i < 2; i ++) {  
            s = s.replaceAll(regex4[i], "");  
        }  
        // 当"万"到"亿"之间全部是"零"的时候，忽略"亿万"单位，只保留一个"亿"  
        s = s.replaceAll("亿万", "亿");  
        return s;  
    }  


	/**
	 * 检查传入的IP是否合法。
	 * 
	 * @param ip
	 *            要传入的IP。
	 * @return true 表示合示,false 表示不合法。
	 */
	public static boolean isIp(String ip) // 判断IP
	{
		String str1[] = ip.split("\\.");
		if (str1.length != 4) {
			return false;
		}
		if (Integer.parseInt(str1[0]) < 1 || Integer.parseInt(str1[0]) > 255) {
			return false;
		}

		for (int i = 1; i < str1.length; i++) {
			if (Integer.parseInt(str1[i]) < 0
					|| Integer.parseInt(str1[i]) > 255) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * 过滤特殊符号
	 * 
	 */
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
	}

}
