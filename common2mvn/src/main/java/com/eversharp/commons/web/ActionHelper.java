package com.eversharp.commons.web;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.eversharp.commons.date.DateUtil;
import com.eversharp.commons.util.ClassUtil;
import com.eversharp.commons.util.StringUtil;

public class ActionHelper {

	
	public static String getParameter(HttpServletRequest request,String paramName){
		
		if(request == null){
			throw new IllegalArgumentException("非法参数:request不能为空");
		}
		if(StringUtils.isEmpty(paramName)){
			throw new IllegalArgumentException("非法参数：paramName不能为空");
		}
		
		String paramVal = request.getParameter(paramName);
		if(StringUtils.isEmpty(paramVal)){return StringUtils.EMPTY;}
		
		return FilterHelper.filterString(paramVal);
	}
	
	
	public static String[] getParameterValues(HttpServletRequest request,String paramName){
		
		if(request == null){
			throw new IllegalArgumentException("非法参数:request不能为空");
		}
		if(StringUtils.isEmpty(paramName)){
			throw new IllegalArgumentException("非法参数：paramName不能为空");
		}
		
		String[] paramVals = request.getParameterValues(paramName);
		if(paramVals == null || paramVals.length == 0){
			return null;
		}
		
		for (int i = 0; i < paramVals.length; i++) {
			paramVals[i] = FilterHelper.filterString(paramVals[i]);
		}
		
		return paramVals;
	}
	
	public static String getRemoteIP(HttpServletRequest request){
		
		String forwardIP = StringUtils.isNotEmpty(request.getHeader("X-FORWARDED-FOR"))? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr();
		
		if(StringUtils.isNotEmpty(forwardIP) &&  forwardIP.indexOf(",") != -1){
        	String[] ipRouters = forwardIP.split(",");
        	String remoteIP = ipRouters[0];
        	return remoteIP;
        }else{
        	return forwardIP;
        }
	}
	
	
	public static String getForwardIP(HttpServletRequest request){
		return request.getHeader("X-FORWARDED-FOR");
	}
	
	
	/**
	 * 根据所传的cookie名称获取值
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 */
	public static String getCookieValueByName(HttpServletRequest request,String cookieName){
		Cookie[] cookies=request.getCookies();
		for(int i=0,length=cookies.length;i<length;i++){
			if(cookieName.trim().equalsIgnoreCase(cookies[i].getName().trim())){
				return cookies[i].getValue();
			}
		}
		
		return "";
	}
	
	/**
     * 根据bean或者vo对象获取参数值生成实例
     * 
     * @param request
     * @param beanName
     * @return
     */
    public static Object getBeanByName(HttpServletRequest request,String beanName){
        
        try {
            if (!(request == null ||StringUtil.isEmpty(beanName))) {
                Class<?> clazz = ClassUtil.getClass(beanName);
                Object beanObject = ClassUtil.instantiate(clazz);
                Field[] fields = clazz.getDeclaredFields();// 获得属性
                for (Field field : fields) {
                    String name = field.getName();// 得到字段名
                    if("serialVersionUID".equals(name)){
                        continue;
                    }
                    String type = field.getGenericType().toString();
                    type = type.substring(type.lastIndexOf(".")==-1?0:type.lastIndexOf(".")+1);
                    if("int".equals(type)){
                        Method metd = clazz.getMethod("set" + StringUtil.changeFirstUpperCase(name), int.class);  
                        int value = HttpRequestHelper.getIntParameter(request, name, 0);
                        metd.invoke(beanObject, value);
                    }else if("String".equals(type)){
                        Method metd = clazz.getMethod("set" + StringUtil.changeFirstUpperCase(name), String.class);  
                        String value = HttpRequestHelper.getParameter(request, name, "");
                        metd.invoke(beanObject, value);
                    }else if("Date".equals(type)){
                        Method metd = clazz.getMethod("set" + StringUtil.changeFirstUpperCase(name), Date.class);
                        String value = request.getParameter(name);
                        if(!StringUtil.isEmpty(value)){
                            metd.invoke(beanObject, DateUtil.toDate(value));
                        }
                    }else{
                        int value = HttpRequestHelper.getIntParameter(request, name, 0);
                        Class<?> fieldClazz = ClassUtil.getClass("com.eversharp.gamecenter.bean." + type);
                        Object fieldObj = ClassUtil.instantiate(fieldClazz);
                        Method fieldMetd = fieldClazz.getMethod("setCode", int.class);
                        fieldMetd.invoke(fieldObj, value);
                        Method metd = clazz.getMethod("set" + StringUtil.changeFirstUpperCase(name), fieldClazz); 
                        metd.invoke(beanObject, fieldObj);
                    }
                    
                }
                return beanObject;
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static OutputStreamWriter getResponseWriter(HttpServletResponse response)
            throws IOException {
        return new OutputStreamWriter(response.getOutputStream(),
                Charset.forName("UTF-8"));
    }
}
