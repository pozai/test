/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.web;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.eversharp.commons.util.Assert;
import com.eversharp.commons.util.StringUtil;

/**
 * description:web常用的一些方法
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
public class WebUtil {

	// -------------------------------------------------------------- Constants
	public static String	WEBINF_ClASSES	= "WEB-INF/classes";

	// ----------------------------------------------------- Instance Variables

	// ------------------------------------------------------------ Constructor

	// --------------------------------------------------------- Public Methods
	/**
	 * 获取上下文路径
	 */
	public static String getRealPath(ServletContext servletContext, String path) throws FileNotFoundException {
		Assert.notNull(servletContext, "ServletContext must not be null");
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		String realPath = servletContext.getRealPath(path);
		if (realPath == null) { throw new FileNotFoundException("ServletContext resource [" + path + "] cannot be resolved to absolute file path - "
				+ "web application archive not expanded?"); }

		return realPath;
	}

	/**
	 * sessionId的获取
	 */
	public static String getSessionId(HttpServletRequest request) {
		Assert.notNull(request, "Request must not be null");
		HttpSession session = request.getSession(false);

		return (session != null ? session.getId() : null);
	}

	/**
	 * 获取session属性
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Object getSessionAttribute(HttpServletRequest request, String name) {
		Assert.notNull(request, "Request must not be null");
		HttpSession session = request.getSession(false);

		return (session != null ? session.getAttribute(name) : null);
	}

	/**
	 * 删除对应的session属性
	 * 
	 * @param request
	 * @param name
	 */
	public static void removeSessionAttribute(HttpServletRequest request, String name) {
		Assert.notNull(request, "Request must not be null");
		request.getSession().removeAttribute(name);
	}

	/**
	 * 获取session属性
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static String getSessionAttributeValue(HttpServletRequest request, String name) {

		Object value = getSessionAttribute(request, name);
		return (value != null ? (String) value : "");
	}

	/**
	 * 设置session属性
	 * 
	 * @param request
	 * @param name
	 * @param value
	 */
	public static void setSessionAttribute(HttpServletRequest request, String name, Object value) {
		Assert.notNull(request, "Request must not be null");
		if (value != null) {
			request.getSession().setAttribute(name, value);
		} else {
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.removeAttribute(name);
			}
		}
	}

	/**
	 * @param session
	 * @param name
	 * @param clazz
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Object getOrCreateSessionAttribute(HttpSession session, String name, Class<?> clazz) throws IllegalArgumentException {

		Assert.notNull(session, "Session must not be null");
		Object sessionObject = session.getAttribute(name);
		if (sessionObject == null) {
			try {
				sessionObject = clazz.newInstance();
			} catch (InstantiationException ex) {
				throw new IllegalArgumentException("Could not instantiate class [" + clazz.getName() + "] for session attribute '" + name + "': "
						+ ex.getMessage());
			} catch (IllegalAccessException ex) {
				throw new IllegalArgumentException("Could not access default constructor of class [" + clazz.getName() + "] for session attribute '" + name
						+ "': " + ex.getMessage());
			}
			session.setAttribute(name, sessionObject);
		}

		return sessionObject;
	}

	/**
	 * 获取cookie
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Assert.notNull(request, "Request must not be null");
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) { return cookie; }
			}
		}

		return null;
	}

	/**
	 * 删除指定名称的cookie
	 * 
	 * @param request
	 * @param name
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		Assert.notNull(request, "Request must not be null");
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {

					cookie.setMaxAge(0);

					response.addCookie(cookie);
				}
			}
		}

	}

	/**
	 * 删除指定名称的cookie
	 * 
	 * @param request
	 * @param name
	 */
	public static void deleteDomainCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain, String path) {
		Assert.notNull(request, "Request must not be null");
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (name.equals(cookie.getName())) {
					cookie.setDomain(domain);
					cookie.setPath(path);
					cookie.setMaxAge(0);

					response.addCookie(cookie);
				}
			}
		}

	}

	/**
	 * 从一个url路径中得到文件名
	 */
	public static String extractFullFilenameFromUrlPath(String urlPath) {
		int end = urlPath.indexOf(';');
		if (end == -1) {
			end = urlPath.indexOf('?');
			if (end == -1) {
				end = urlPath.length();
			}
		}
		int begin = urlPath.lastIndexOf('/', end) + 1;

		return urlPath.substring(begin, end);
	}

	/**
	 * 通过类来获取上下文webroot路径
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getWebRootPathByclass(Class<?> clazz) {
		Assert.notNull(clazz, "class must not be null");

		String resourcePath = clazz.getResource("/").getPath();
//		System.out.println(resourcePath);
		if (StringUtil.endsWithIgnoreCase(resourcePath, "/")) {
			WEBINF_ClASSES = WEBINF_ClASSES + "/";
		}
		String webRootPath = StringUtil.removeTailingString(resourcePath, WEBINF_ClASSES);

		return webRootPath;
	}

	/**
	 * 获取请求的全部路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestFullPath(HttpServletRequest request) {
		Assert.notNull(request, "Request must not be null");

		String url = request.getRequestURL().toString();
		String queryString = null;
		queryString = format("UTF-8", RequestUtil.getMapFromParameters(request));

		return queryString != null ? (url + "?" + queryString) : url;

	}

	/**
	 * 转换成符合规范的URL地址
	 * 
	 * @param encode
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String format(String encode, Map<String, Object> map) {

		if (map == null || map.isEmpty()) { return null; }

		// 通过参数构造发送主体
		StringBuffer sbPostBody = new StringBuffer();

		Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Object> entry = iterator.next();
			String paramName = entry.getKey();
			String paramValue = (String) entry.getValue();
			sbPostBody.append(paramName);
			sbPostBody.append("=");
			try {
				sbPostBody.append(URLEncoder.encode(paramValue, encode));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			sbPostBody.append("&");
		}

		return sbPostBody.toString();
	}

	/**
	 * 获取请求者的IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestIP(HttpServletRequest request) {
		Assert.notNull(request, "Request must not be null");

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		// 多级反向代理时，取到一个非unkonwn的IP
		if (ip!=null&&ip.length() > 15) {
			String[] ips = ip.split(",");
			for (int i = 0; i < ips.length; i++) {
				ip = ips[i];
				if (!ip.equalsIgnoreCase("unknown")) {

					break;
				}

			}
		}
		
		if(ip==null){
			ip="127.0.0.1";
		}

		return ip;
	}
	
	public static void main(String[] args) {
		System.out.println(extractFullFilenameFromUrlPath("http://ad/bb"));
	}

	// ------------------------------------------------------- Protected Methods

	// --------------------------------------------------------- Private Methods
}
