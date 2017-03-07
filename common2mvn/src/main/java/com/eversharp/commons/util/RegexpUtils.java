package com.eversharp.commons.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类简介: 使用正则表达式验证数据或提取数据,类中的方法全为静态的 主要方法:1. isHardRegexpValidate(String source, String regexp) 区分大小写敏感的正规表达式批配 * *
 */
public final class RegexpUtils {

	private RegexpUtils() {

	}

	/**
	 * 匹配图象 格式: /相对路径/文件名.后缀 (后缀为gif,dmp,png) 匹配 : /forum/head_icon/admini2005111_ff.gif 或 admini2005111.dmp 不匹配: c:/admins4512.gif
	 */
	public static final String	icon_regexp								= "^(/{0,1}\\w){1,}\\.(gif|dmp|png|jpg)$|^\\w{1,}\\.(gif|dmp|png|jpg)$";

	/**
	 * 匹配匹配并提取url 格式: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX 匹配 : http://www.suncer.com 或news://www 提取(MatchResult matchResult=matcher.getMatch()):
	 * matchResult.group(0)= http://www.suncer.com:8080/index.html?login=true matchResult.group(1) = http matchResult.group(2) = www.suncer.com
	 * matchResult.group(3) = :8080 matchResult.group(4) = /index.html?login=true 不匹配: c:\window
	 */
	public static final String	url_regexp								= "(\\w+)://([^/:]+)(:\\d*)?([^#\\s]*)";

	/**
	 * 匹配并提取http 格式: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX 或 ftp://XXX.XXX.XXX 或 https://XXX 匹配 : http://www.suncer.com:8080/index.html?login=true
	 * 提取(MatchResult matchResult=matcher.getMatch()): matchResult.group(0)= http://www.suncer.com:8080/index.html?login=true matchResult.group(1) = http
	 * matchResult.group(2) = www.suncer.com matchResult.group(3) = :8080 matchResult.group(4) = /index.html?login=true 不匹配: news://www
	 */
	public static final String	http_regexp								= "(http|https|ftp)://([^/:]+)(:\\d*)?([^#\\s]*)";

	/**
	 * 匹配日期 格式(首位不为0): XXXX-XX-XX 或 XXXX XX XX 或 XXXX-X-X 范围:1900--2099
	 */
	public static final String	date_regexp								= "^((((19){1}|(20){1})d{2})|d{2})[-\\s]{1}[01]{1}d{1}[-\\s]{1}[0-3]{1}d{1}$";																																																																// 匹配日期

	/**
	 * 匹配电话 格式为: 0XXX-XXXXXX(10-13位首位必须为0) 或0XXX XXXXXXX(10-13位首位必须为0) 或 (0XXX)XXXXXXXX(11-14位首位必须为0) 或 XXXXXXXX(6-8位首位不为0) 或 XXXXXXXXXXX(11位首位不为0) 匹配 :
	 * 0371-123456 或 (0371)1234567 或 (0371)12345678 或 010-123456 或 010-12345678 或 12345678912 不匹配: 1111-134355 或 0123456789
	 */
	public static final String	phone_regexp							= "^(?:0[0-9]{2,3}[-\\s]{1}|\\(0[0-9]{2,4}\\))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

	/**
	 * 不包括特殊字符的匹配 (字符串中不包括符号 数学次方号^ 单引号' 双引号" 分号; 逗号, 帽号: 数学减号- 右尖括号> 左尖括号< 反斜杠\ 即空格,制表符,回车符等 ) 格式为: x 或 一个一上的字符
	 */
	public static final String	non_special_char_regexp					= "^[^'\"\\;,:-<>\\s].+$";

	// 匹配email地址
	public static final String	email_regexp							= "(?:\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,10}$)";

	//public static String email = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\\d|-|\\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\\d|-|\\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))$";
	
	// 匹配手机
	public static final String	mobile_regexp							= "^((\\(\\d{3}\\))|(\\d{3}\\-))?1\\d{10}$";

	// 匹配身份证
	public static final String	ID_card_regexp							= "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";

	// 匹配邮编代码
	public static final String	ZIP_regexp								= "^[0-9]{6}$";

	// 匹配汉字
	public static final String	chinese_regexp							= "^[\u0391-\uFFE5]+$";

	// 匹配非负整数（正整数 + 0)
	public static final String	non_negative_integers_regexp			= "^\\d+$";

	// 匹配不包括零的非负整数（正整数 > 0)
	public static final String	non_zero_negative_integers_regexp		= "^[1-9]+\\d*$";

	// 匹配正整数
	public static final String	positive_integer_regexp					= "^[0-9]*[1-9][0-9]*$";

	// 匹配非正整数（负整数 + 0）
	public static final String	non_positive_integers_regexp			= "^((-\\d+)|(0+))$";

	// 匹配负整数
	public static final String	negative_integers_regexp				= "^-[0-9]*[1-9][0-9]*$";

	// 匹配整数
	public static final String	integer_regexp							= "^-?\\d+$";

	// 匹配非负浮点数（正浮点数 + 0）
	public static final String	non_negative_rational_numbers_regexp	= "^\\d+(\\.\\d+)?$";

	// 匹配正浮点数
	public static final String	positive_rational_numbers_regexp		= "^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$";

	// 匹配非正浮点数（负浮点数 + 0）
	public static final String	non_positive_rational_numbers_regexp	= "^((-\\d+(\\.\\d+)?)|(0+(\\.0+)?))$";

	// 匹配负浮点数
	public static final String	negative_rational_numbers_regexp		= "^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

	// 匹配浮点数
	public static final String	rational_numbers_regexp					= "^(-?\\d+)(\\.\\d+)?$";

	// 匹配由26个英文字母组成的字符串
	public static final String	letter_regexp							= "^[A-Za-z]+$";

	// 匹配由26个英文字母的大写组成的字符串
	public static final String	upward_letter_regexp					= "^[A-Z]+$";

	// 匹配由26个英文字母的小写组成的字符串
	public static final String	lower_letter_regexp						= "^[a-z]+$";

	// 匹配由数字和26个英文字母组成的字符串
	public static final String	letter_number_regexp					= "^[A-Za-z0-9]+$";

	// 匹配由数字、26个英文字母或者下划线组成的字符串
	public static final String	letter_number_underline_regexp			= "^\\w+$";

	// 检查帐号是否正确4－16位
	public static final String	account_name_regexp						= "^[a-zA-Z0-9_@.]{3,32}$";

	// 检查密码是否正确6-20位,特殊字符也要查看是否被xss过滤
	public static final String	account_password_regexp					= "^[a-zA-Z0-9_$!@#$%^*()]{5,20}$";

	// 昵称
	public static final String	nickname_regexp							= "^[\\w\\W]{2,12}$";

	// 时间
	public static final String	time_regexp								= "[0-9]{4,4}-[0-9]{2,2}-[0-9]{2,2}";

	/**
	 * 正规表达式批配
	 * 
	 * @param source 批配的源字符串
	 * @param regexp 批配的正规表达式
	 * @return 如果源字符串符合要求返回真,否则返回假 如: Regexp.isRegexpValidate("ygj@suncer.com.cn",email_regexp) 返回真
	 */
	public static boolean isRegexpValidate(String source, String regexp) {
		Pattern p = Pattern.compile(regexp);
		Matcher m = p.matcher(source);
		return m.find();
	}

	public static void main(String[] args) {
		System.out.println(isRegexpValidate("ztyjchang@163.colm", RegexpUtils.email_regexp));
	}
}