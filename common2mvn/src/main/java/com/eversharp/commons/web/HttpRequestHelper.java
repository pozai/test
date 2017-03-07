/**
 * 
 */
package com.eversharp.commons.web;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *  
 * HTTP requst参数读取辅助类
 * 实现各种常用数据类型的参数转换及空值处理
 */

public class HttpRequestHelper {

	private static String NULLSTRING = "";
	
	public static final String DEFAULT_ENCODING = "ISO-8859-1";
	
	public static final String TARGET_ENCODING = "UTF-8";
	
	
	/**
     * 对HTTP接收的参数提供默认值
     * @param request
     * @param name
     * @param defautlValue
     * @return
     */
    public static String getDefautParameter(HttpServletRequest request,
            String name, String defautlValue) {
        String temp = request.getParameter(name);
        if (temp == null || temp.trim().equals(NULLSTRING)) {
            return defautlValue;
        }
        return temp;
    }
	
	/**
	 * 对HTTP接收的参数进行编码转换 
	 * @param request
	 * @param name
	 * @param encoding
	 * @param defautlValue
	 * @return
	 */
	public static String getEncodedParameter(HttpServletRequest request,
			String name, String encoding, String defautlValue) {
		String temp = request.getParameter(name);
		if (temp == null || temp.trim().equals(NULLSTRING)) {
			return defautlValue;
		}
		if (encoding == null) {
			return temp;
		}
		try {
		    String oldecoding = getEncoding(temp);
		    if(!encoding.equals(oldecoding)){
		        if("GBK".equals(oldecoding) || "GB2312".equals(oldecoding)){
		            temp = new String(convertHighByte(temp), encoding);
		        }else{
		            temp = new String(temp.getBytes(oldecoding), encoding);
		        }
		    }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return defautlValue;
		}
		return temp;
	}

	public static String getEncodedParameter(HttpServletRequest request,
			String name, String encoding) {
		return getEncodedParameter(request, name, encoding, null);
	}
	
	/**
	 * 取得HTTP参数，值为空字符串时返加null
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		return getEncodedParameter(request, name, TARGET_ENCODING, null);
	}

	/**
	 * 取得HTTP参数，值为空字符串或null时返回默认值
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name,
			String defaultValue) {
		return getEncodedParameter(request, name, TARGET_ENCODING, defaultValue);
	}

	/**
	 * 对HTTP接收的参数数组进行编码转换
	 * @param request
	 * @param name
	 * @param encoding
	 * @return
	 */
	public static String[] getEncodedParameters(HttpServletRequest request,
			String name, String encoding) {

		String[] temp = request.getParameterValues(name);
		if (temp == null) {
			return null;
		}
		if (encoding == null) {
			return temp;
		}
		try {
			for (int i = 0; i < temp.length; i++) {
				if (temp[i] != null) {
					temp[i] = new String(temp[i].getBytes(DEFAULT_ENCODING), encoding);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	/**
	 * 对HTTP接收的参数数组进行编码转换
	 * @param request
	 * @param name
	 * @return
	 */
	public static String[] getParameters(HttpServletRequest request,
			String name) {
		return getEncodedParameters(request, name, TARGET_ENCODING);
	}

	/**
	 * 值为"trur"或'y"时返回true，否则返回false
	 * @param request
	 * @param name
	 * @param defaultVal
	 * @return
	 */
	public static boolean getBooleanParameter(HttpServletRequest request,
			String name, boolean defaultVal) {
		String temp = request.getParameter(name);
		if ("true".equalsIgnoreCase(temp) || "y".equalsIgnoreCase(temp)) {
			return true;
		} else if ("false".equalsIgnoreCase(temp) || "n".equalsIgnoreCase(temp)) {
			return false;
		} else {
			return defaultVal;
		}
	}
	
	/**
	 * 把取得的参数传化为char类型
	 * @param request
	 * @param name
	 * @param defaultNum
	 * @return
	 */
	public static char getCharParameter(HttpServletRequest request, String name,
			char defaultChar) {
		String temp = request.getParameter(name);
		if (temp == null || temp.trim().equals(NULLSTRING)) {
			return defaultChar;
		}
		try {
			defaultChar = temp.charAt(0);
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
		return defaultChar;
	}	
	
	/**
	 * 把取得的参数传化为int类型
	 * @param request
	 * @param name
	 * @param defaultNum
	 * @return
	 */
	public static int getIntParameter(HttpServletRequest request, String name,
			int defaultNum) {
		String temp = request.getParameter(name);
		if (temp == null || temp.trim().equals(NULLSTRING)) {
			return defaultNum;
		}
		try {
			defaultNum = Integer.parseInt(temp);
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
		return defaultNum;
	}
	
	/**
	 * 把取得的参数传化为float类型
	 * @param request
	 * @param name
	 * @param defaultNum
	 * @return
	 */
	public static float getFloatParameter(HttpServletRequest request,
			String name, float defaultNum) {
		String temp = request.getParameter(name);
		if (temp == null || temp.trim().equals(NULLSTRING)) {
			return defaultNum;
		}
		try {
			defaultNum = Float.parseFloat(temp);
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
		return defaultNum;
	}
	

	/**
	 * 把取得的参数传化为double类型
	 * @param request
	 * @param name
	 * @param defaultNum
	 * @return
	 */
	public static double getDoubleParameter(HttpServletRequest request,
			String name, double defaultNum) {
		String temp = request.getParameter(name);
		if (temp == null || temp.trim().equals(NULLSTRING)) {
			return defaultNum;
		}
		try {
			defaultNum = Double.parseDouble(temp);
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
		return defaultNum;
	}

	/**
	 * 把取得的参数传化为long类型
	 * @param request
	 * @param name
	 * @param defaultNum
	 * @return
	 */
	public static long getLongParameter(HttpServletRequest request,
			String name, long defaultNum) {
		String temp = request.getParameter(name);
		if (temp == null || temp.trim().equals(NULLSTRING)) {
			return defaultNum;
		}
		try {
			defaultNum = Long.parseLong(temp);
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
		return defaultNum;
	}
	
	/**
	 * 把取得的参数传化为Date类型
	 * @param request
	 * @param name
	 * @return
	 */
	public static Date getDateParameter(HttpServletRequest request, String name) {
		Date param = null;
		String temp = request.getParameter(name);
		if (temp == null || temp.trim().equals(NULLSTRING)) {
			return param;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			param = format.parse(temp) ;
		} catch (Exception ex) {
			System.err.println(ex.toString());
		}
		return param;
	}
	
	/**
	 * request所有数据存到javaBean中
	 * @param request
	 * @param cla 
	 *		javaBean类类型
	 *@param encoding 
	 *		字符编码
	 * @return
	 * 		javaBean
	 */
	public static Object getParameters(HttpServletRequest request,Class<?> cls,String encoding){
		Object obj = null ;
		try {
			obj = cls.newInstance();
			Enumeration<?> enums =  request.getParameterNames() ; //获取所有上传参数名字
			while(enums.hasMoreElements()){
				String str = (String)enums.nextElement();  //参数名
				Method setMethod = BeanTools.getSetMethod(cls,str); //根据参数名获取对应的set方法
				Method getMethod = BeanTools.getGetMethod(cls, str);  //根据参数名获取对应的get方法
				Class<?> returnType = getMethod.getReturnType() ; //获取该变量的类型
				String value = "" ;
				if(null!=encoding && !"".equals(encoding)){
					value = getEncodedParameter(request,str,encoding) ; //参数对应的值
				}
				Object valueObj = BeanTools.getObject(returnType, value); //获取转换后的值
				setMethod.invoke(obj, valueObj);
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return obj ;
	}
	
	/**
	 * request所有数据存到javaBean中
	 * @param request
	 * @param cla 
	 *		javaBean类类型
	 * @return
	 * 		javaBean
	 */
	public static Object getParameters(HttpServletRequest request,Class<?> cls){
		Object obj = null ;
		try {
			obj = cls.newInstance();
			Enumeration<?> enums =  request.getParameterNames() ; //获取所有上传参数名字
			while(enums.hasMoreElements()){
				String str = (String)enums.nextElement();  //参数名
				Method setMethod = BeanTools.getSetMethod(cls,str); //根据参数名获取对应的set方法
				Method getMethod = BeanTools.getGetMethod(cls, str);  //根据参数名获取对应的get方法
				Class<?> returnType = getMethod.getReturnType() ; //获取该变量的类型
				String value = "" ;
				value = getParameter(request,str) ; //参数对应的值
				Object valueObj = BeanTools.getObject(returnType, value); //获取转换后的值
				setMethod.invoke(obj, valueObj);
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return obj ;
	}
	
	/** 
     * 判断字符串的编码 
     * 
     * @param str 
     * @return 
     */  
    public static String getEncoding(String str) {  
        String encode = "ISO-8859-1";  
        try {  
            if (str.equals(new String(str.getBytes(encode), encode))) {  
                String s1 = encode;  
                return s1;  
            }  
        } catch (Exception exception1) {  
        }  
        encode = "UTF-8";
        try {  
            if (str.equals(new String(str.getBytes(encode), encode))) {  
                String s2 = encode;  
                return s2;  
            }  
        } catch (Exception exception2) {  
        }  
        encode = "GBK";  
        try {  
            if (str.equals(new String(str.getBytes(encode), encode))) {  
                String s3 = encode;  
                return s3;  
            }  
        } catch (Exception exception3) {  
        }  
        return "";  
    }  
    
    /**
     * 将GBK字符串转成UTF-8匹配的字符流
     * 
     * @param chenese 
     * 
     * @return the byte[]
     */
    public static byte[] convertHighByte(String chenese) {
        
        // Step 1: 得到GBK编码下的字符数组，一个中文字符对应这里的一个c[i]
        char c[] = chenese.toCharArray();
        
        // Step 2: UTF-8使用3个字节存放一个中文字符，所以长度必须为字符的3倍
        byte[] fullByte = new byte[3 * c.length];
        
        // Step 3: 循环将字符的GBK编码转换成UTF-8编码
        for (int i = 0; i < c.length; i++) {
            
            // Step 3-1：将字符的ASCII编码转换成2进制值
            int m = (int) c[i];
            String word = Integer.toBinaryString(m);
            // Step 3-2：将2进制值补足16位(2个字节的长度) 
            StringBuffer sb = new StringBuffer();
            int len = 16 - word.length();
            for (int j = 0; j < len; j++) {
                sb.append("0");
            }
            // Step 3-3：得到该字符最终的2进制GBK编码
            // 形似：1000 0010 0111 1010
            sb.append(word);
            
            // Step 3-4：最关键的步骤，根据UTF-8的汉字编码规则，首字节
            // 以1110开头，次字节以10开头，第3字节以10开头。在原始的2进制
            // 字符串中插入标志位。最终的长度从16--->16+3+2+2=24。
            sb.insert(0, "1110");
            sb.insert(8, "10");
            sb.insert(16, "10");
            // Step 3-5：将新的字符串进行分段截取，截为3个字节
            String s1 = sb.substring(0, 8);
            String s2 = sb.substring(8, 16);
            String s3 = sb.substring(16);

            // Step 3-6：最后的步骤，把代表3个字节的字符串按2进制的方式
            // 进行转换，变成2进制的整数，再转换成16进制值
            byte b0 = Integer.valueOf(s1, 2).byteValue();
            byte b1 = Integer.valueOf(s2, 2).byteValue();
            byte b2 = Integer.valueOf(s3, 2).byteValue();
            
            // Step 3-7：把转换后的3个字节按顺序存放到字节数组的对应位置
            byte[] bf = new byte[3];
            bf[0] = b0;
            bf[1] = b1;
            bf[2] = b2;
            
            fullByte[i * 3] = bf[0];            
            fullByte[i * 3 + 1] = bf[1];            
            fullByte[i * 3 + 2] = bf[2];
            
            // Step 3-8：返回继续解析下一个中文字符
        }
        return fullByte;
    }
}
