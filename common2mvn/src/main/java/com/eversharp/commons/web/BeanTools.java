package com.eversharp.commons.web;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class BeanTools {

	/**
	 * 根据传入属性名取得bean的get方法
	 * 
	 * @param cls
	 *            Bean的Class属性
	 * @param name
	 *            变量名字
	 * @return 返回一个Method
	 */
	public static Method getGetMethod(Class<?> cls, String name) {
		Method methods[] = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equalsIgnoreCase("get" + name)) {
				return methods[i];
			}
		}
		return null;
	}

	/**
	 * 获取变量值
	 * 
	 * @param cls
	 *            变量类型
	 * @param obj
	 *            invoke得到的值
	 * @return 返回变量的值(自动转成string类型)
	 * @throws Exception
	 */
	public static String getString(Class<?> cls, Object obj) throws Exception {
		String result = null;
		String str = cls.toString();
		if (obj == null)
			return null;
		if (str.indexOf("String") > -1) {
			result = obj.toString();
		}
		if (str.indexOf("Integer") > -1 || str.indexOf("int") > -1) {
			result = (obj == null ? "" : obj.toString());
		}
		if (str.indexOf("Long") > -1 || str.indexOf("long") > -1) {
			result = (obj == null ? "" : obj.toString());
		}
		if (str.indexOf("Double") > -1 || str.indexOf("double") > -1) {
			result = (obj == null ? "" : obj.toString());
		}
		if (str.indexOf("Float") > -1 || str.indexOf("float") > -1) {
			result = (obj == null ? "" : obj.toString());
		}
		if (str.indexOf("Boolean") > -1 || str.indexOf("boolean") > -1) {
			result = obj.toString();
		}
		if (str.indexOf("Character") > -1 || str.indexOf("char") > -1) {
			result = obj.toString();
		}
		if (str.indexOf("Date") > -1) {
			result = (obj == null ? "" : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj));
		}
		return result;
	}

	/**
	 * 获取javaBean中所有的变量和对应的set方法
	 * 
	 * @param cls
	 * @return
	 */
	public static Map<Object, Method> getSetMethod(Class<?> cls) {
		Map<Object, Method> map = new HashMap<Object, Method>();
		Method[] method = cls.getMethods(); // 获取类中所有方法
		for (int i = 0; i < method.length; i++) {
			if ("set".equals(method[i].getName().substring(0, 3))) {
				String str = method[i].getName().substring(3).toLowerCase();
				String returnType = method[i].getParameterTypes()[0].getName();
				int index = returnType.lastIndexOf(".") + 1;
				returnType = returnType.substring(index);
				map.put(str + "&" + returnType, method[i]); // key值是 变量名+&+变量名类型
			}
		}
		return map;
	}

	/**
	 * 获取javaBean中所有的变量和对应的get方法
	 * 
	 * @param cls
	 * @return
	 */
	public static Map<Object, Method> getGetMethod(Class<?> cls) {
		Map<Object, Method> map = new HashMap<Object, Method>();
		Method method[] = cls.getMethods(); // 获取类中所有方法
		for (int i = 0; i < method.length; i++) {
			if ("get".equals(method[i].toString().substring(0, 2))) {
				String str = method[i].toString().substring(3).toLowerCase();
				map.put(str, method[i]);
			}
		}
		return map;
	}

	/**
	 * 根据传入属性名取得bean的set方法
	 * 
	 * @param cls
	 *            Bean的Class属性
	 * @param name
	 *            属性名
	 * @return
	 */
	public static Method getSetMethod(Class<?> cls, String name) {

		Method[] methods = cls.getMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getName().equalsIgnoreCase("set" + name)) {
				return methods[i];
			}
		}
		return null;
	}

	/**
	 * 根据传入类型转换数据类型
	 * 
	 * @param cls
	 *            变量类型
	 * @param obj
	 *            invoke得到的值
	 * @return 返回变量
	 * @throws Exception
	 */
	public static Object getObject(Class<?> cls, Object obj) throws Exception {
		Object result = null;
		String str = cls.toString();
		if (obj == null)
			return null;
		if (str.indexOf("String") > -1) {
			result = obj.toString();
		}
		if (str.indexOf("Integer") > -1 || str.indexOf("int") > -1) {
			result = Integer.valueOf(obj == null ? "0" : obj.toString());
		}
		if (str.indexOf("Long") > -1 || str.indexOf("long") > -1) {
			result = Long.valueOf(obj == null ? "0" : obj.toString());
		}
		if (str.indexOf("Double") > -1 || str.indexOf("double") > -1) {
			result = Double.valueOf(obj == null ? "0.0" : obj.toString());
		}
		if (str.indexOf("Float") > -1 || str.indexOf("float") > -1) {
			result = Float.valueOf(obj == null ? "0.0" : obj.toString());
		}
		if (str.indexOf("Boolean") > -1 || str.indexOf("boolean") > -1) {
			result = Boolean.valueOf(obj.toString());
		}
		if (str.indexOf("Character") > -1 || str.indexOf("char") > -1) {
			result = (Character) obj;
		}
		if (str.indexOf("Date") > -1) {
			String dateStr = obj.toString();
			if (dateStr != null && !dateStr.equals("")) {
				if (dateStr.length() <= 10) {
					dateStr = dateStr + " 00:00:00";
					result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr.toString());
				} else {
					result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr.toString());
				}
			} else {
				result = null;
			}
		}
		return result;
	}

	/**
	 * 根据传入类型转换数据类型
	 * 
	 * @param cls
	 *            变量类型
	 * @param obj
	 *            invoke得到的值
	 * @return 返回变量
	 * @throws Exception
	 */
	public static Object getObject(Class<?> cls, Object obj, Boolean isTrim) throws Exception {
		Object result = null;
		String str = cls.toString();
		if (obj == null)
			return null;
		if (str.indexOf("String") > -1) {
			result = (isTrim == true ? obj.toString().trim() : obj.toString());
		}
		if (str.indexOf("Integer") > -1 || str.indexOf("int") > -1) {
			result = Integer.valueOf(obj == null ? "0" : obj.toString());
		}
		if (str.indexOf("Long") > -1 || str.indexOf("long") > -1) {
			result = Long.valueOf(obj == null ? "0" : obj.toString());
		}
		if (str.indexOf("Double") > -1 || str.indexOf("double") > -1) {
			result = Double.valueOf(obj == null ? "0.0" : obj.toString());
		}
		if (str.indexOf("Float") > -1 || str.indexOf("float") > -1) {
			result = Float.valueOf(obj == null ? "0.0" : obj.toString());
		}
		if (str.indexOf("Boolean") > -1 || str.indexOf("boolean") > -1) {
			result = Boolean.valueOf(obj.toString());
		}
		if (str.indexOf("Character") > -1 || str.indexOf("char") > -1) {
			result = (Character) obj;
		}
		if (str.indexOf("Date") > -1) {
			String dateStr = obj.toString();
			if (dateStr != null && !dateStr.equals("")) {
				if (dateStr.length() <= 10) {
					dateStr = dateStr + " 00:00:00";
					result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr.toString());
				} else {
					result = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr.toString());
				}
			} else {
				result = null;
			}
		}
		return result;
	}

}
