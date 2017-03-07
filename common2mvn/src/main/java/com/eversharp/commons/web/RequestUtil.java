/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.web;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import com.eversharp.commons.util.StringUtil;

/**
 * description:request常用的一些
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
public abstract class RequestUtil {

	// -------------------------------------------------------------- Constants

	public static final String DEFAULT_ENCODING = "UTF-8";

	public static final String TARGET_ENCODING = "UTF-8";

	// ----------------------------------------------------- Instance Variables

	// ------------------------------------------------------------ Constructor

	// --------------------------------------------------------- Public Methods
	/**
	 * 对HTTP接收的参数进行编码转换
	 * 
	 * @param request
	 * @param name
	 * @param encoding
	 * @param defautlValue
	 * @return
	 */
	public static String getEncodedParameter(HttpServletRequest request, String name, String encoding, String defautlValue) {
		String paramValue = request.getParameter(name);
		if (!StringUtil.hasText(paramValue)) {
			return defautlValue;
		}
		if (encoding == null) {
			return paramValue;
		}

		try {
			paramValue = new String(paramValue.getBytes(DEFAULT_ENCODING), encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return defautlValue;
		}

		return paramValue;
	}

	/**
	 * 不设置默认值
	 * 
	 * @param request
	 * @param name
	 * @param encoding
	 * @return
	 */
	public static String getEncodedParameter(HttpServletRequest request, String name, String encoding) {
		return getEncodedParameter(request, name, encoding, null);
	}

	/**
	 * 取得HTTP参数，值为空字符串时返加null
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name) {
		return getEncodedParameter(request, name, null, null);
	}

	/**
	 * 取得HTTP参数，值为空字符串或null时返回默认值
	 * 
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getParameter(HttpServletRequest request, String name, String defaultValue) {
		return getEncodedParameter(request, name, TARGET_ENCODING, defaultValue);
	}

	/**
	 * 对HTTP接收的参数数组进行编码转换
	 * 
	 * @param request
	 * @param name
	 * @param encoding
	 * @return
	 */
	public static String[] getEncodedParameters(HttpServletRequest request, String name, String encoding) {

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
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String[] getParameters(HttpServletRequest request, String name) {
		return getEncodedParameters(request, name, TARGET_ENCODING);
	}

	/**
	 * request所有数据存到javaBean中
	 * 
	 * @param request
	 * @param cla
	 *            javaBean类类型
	 * @param encoding
	 *            字符编码
	 * @return javaBean
	 */
	public static <T> T getParameters(HttpServletRequest request, Class<T> cls, String encoding) {
		T obj = null;
		try {
			obj = cls.newInstance();
			Enumeration<?> enums = request.getParameterNames(); // 获取所有上传参数名字
			while (enums.hasMoreElements()) {
				String str = (String) enums.nextElement(); // 参数名
				Method setMethod = BeanTools.getSetMethod(cls, str); // 根据参数名获取对应的set方法
				Method getMethod = BeanTools.getGetMethod(cls, str); // 根据参数名获取对应的get方法
				if (null == setMethod || null == getMethod) {
					continue;
				}
				Class<?> returnType = getMethod.getReturnType(); // 获取该变量的类型
				String value = "";
				if (null != encoding && !"".equals(encoding)) {
					value = getEncodedParameter(request, str, encoding); // 参数对应的值
				}
				value = request.getParameter(str);
				Object valueObj = BeanTools.getObject(returnType, value); // 获取转换后的值
				setMethod.invoke(obj, valueObj);
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return obj;
	}

	/**
	 * 获取request属性值转成bean
	 * 
	 * @param request
	 * @param cls
	 * @param encoding
	 * @return
	 */
	public static <T> T getAttributes(HttpServletRequest request, Class<T> cls) {
		T obj = null;
		try {
			obj = cls.newInstance();
			Enumeration<?> enums = request.getAttributeNames(); // 获取所有上传参数名字
			while (enums.hasMoreElements()) {
				String str = (String) enums.nextElement(); // 参数名
				Method setMethod = BeanTools.getSetMethod(cls, str); // 根据参数名获取对应的set方法
				Method getMethod = BeanTools.getGetMethod(cls, str); // 根据参数名获取对应的get方法
				if (null == setMethod || null == getMethod) {
					continue;
				}
				Class<?> returnType = getMethod.getReturnType(); // 获取该变量的类型
				String value = "";
				value = (String) request.getAttribute(str);
				Object valueObj = BeanTools.getObject(returnType, value); // 获取转换后的值
				setMethod.invoke(obj, valueObj);
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return obj;
	}

	/**
	 * 获取request属性值转成map
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getAttributes(HttpServletRequest request) {
		Map<String, Object> obj = new HashMap<String, Object>();
		try {
			Enumeration<?> enums = request.getAttributeNames(); // 获取所有上传参数名字
			while (enums.hasMoreElements()) {
				String str = (String) enums.nextElement(); // 参数名
				String value = "";
				value = (String) request.getAttribute(str);
				obj.put(str, value);
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return obj;
	}

	/**
	 * request所有数据存到javaBean中
	 * 
	 * @param request
	 * @param cla
	 *            javaBean类类型
	 * @return javaBean
	 */
	public static <T> T getParameters(HttpServletRequest request, Class<T> cls) {
		T obj = null;
		try {
			obj = cls.newInstance();
			Enumeration<?> enums = request.getParameterNames(); // 获取所有上传参数名字
			while (enums.hasMoreElements()) {
				String str = (String) enums.nextElement(); // 参数名
				Method setMethod = BeanTools.getSetMethod(cls, str); // 根据参数名获取对应的set方法
				Method getMethod = BeanTools.getGetMethod(cls, str); // 根据参数名获取对应的get方法
				if (null == setMethod || null == setMethod) {
					continue;
				}
				Class<?> returnType = getMethod.getReturnType(); // 获取该变量的类型
				String value = "";
				value = request.getParameter(str);// 参数对应的值
				Object valueObj = BeanTools.getObject(returnType, value); // 获取转换后的值
				setMethod.invoke(obj, valueObj);
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		return obj;
	}

	/**
	 * request所有数据存到javaBean中
	 * 
	 * @param request
	 * @param cla
	 *            javaBean类类型
	 * @param isTrim
	 *            字符串是否去空格
	 * @return javaBean
	 */
	public static <T> T getParameters(HttpServletRequest request, Class<T> cls, Boolean isTrim) {
		T obj = null;
		try {
			obj = cls.newInstance();
			Enumeration<?> enums = request.getParameterNames(); // 获取所有上传参数名字
			while (enums.hasMoreElements()) {
				String str = (String) enums.nextElement(); // 参数名
				Method setMethod = BeanTools.getSetMethod(cls, str); // 根据参数名获取对应的set方法
				Method getMethod = BeanTools.getGetMethod(cls, str); // 根据参数名获取对应的get方法
				if (null == setMethod || null == setMethod) {
					continue;
				}
				Class<?> returnType = getMethod.getReturnType(); // 获取该变量的类型
				String value = "";
				value = request.getParameter(str);// 参数对应的值
				if (value == null || value.trim().length() == 0) {
					continue;
				}
				Object valueObj = BeanTools.getObject(returnType, value, isTrim); // 获取转换后的值
				setMethod.invoke(obj, valueObj);
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		}

		return obj;
	}

	/**
	 * 把所传参数转换为Map
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getMapFromParameters(HttpServletRequest request) {

		Map<String, Object> params = new TreeMap<String, Object>();
		for (Enumeration<?> enums = request.getParameterNames(); enums.hasMoreElements();) {
			String param = (String) enums.nextElement(); // 参数名
			String value = request.getParameter(param);
			params.put(param, value);
		}

		return params;
	}

	/**
	 * 获取url参数拼接
	 * 
	 * @param request
	 * @return
	 */
	public static String returnUrlParameters(HttpServletRequest request) {
		String urlParameters = "";
		for (Enumeration<?> enums = request.getParameterNames(); enums.hasMoreElements();) {

			String param = (String) enums.nextElement(); // 参数名
			String value = request.getParameter(param);
			if (!param.equals("currentPage") && !param.equals("offset") && !param.equals("row") && !param.equals("act"))
				urlParameters = urlParameters + "&" + param + "=" + value;
		}

		return urlParameters;
	}

	/**
	 * 获取当前页面URL
	 * 
	 * @param request
	 * @return
	 */
	public static String getCurrentPageURL(HttpServletRequest request) {
		String urlParameters = "";
		for (Enumeration<?> enums = request.getParameterNames(); enums.hasMoreElements();) {
			String param = (String) enums.nextElement(); // 参数名
			String value = request.getParameter(param);
			urlParameters = urlParameters + "&" + param + "=" + value;
		}

		return request.getRequestURI() + "?" + urlParameters;
	}

	// ------------------------------------------------------- Protected Methods

	// --------------------------------------------------------- Private Methods
}
