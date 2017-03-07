package com.eversharp.commons.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Bean工具类，用于Bean以及Map 的相关操作。
 * 
 * @author wsb。
 * 
 * @version V1:2009-9-24(建)
 * @version V2:2010-1-14(改) 对所有方法进行改进,通过field进行改进
 */
public class BeanUtils {

	/**
	 * 
	 * 把javaBean对象传进来,去除javaBean对象里面所有字符串的空格.
	 * 
	 * @param o
	 *            传进去的对象,对象必须是javaBean
	 * @return 返回javaBean里面去除空格后的javaBean对象
	 */
	public static <T> void entityTrim(T t) {
		try {
			Field[] fields = t.getClass().getDeclaredFields(); // 获取该对象所有的方法
			// 该对象的方法是get开头的方法,则下一步
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getType() == String.class) {
					fields[i].setAccessible(true);
					if (fields[i].get(t) != null) {
						fields[i].set(t, fields[i].get(t).toString().trim());
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 把Map对象传进来,去除Map对象里面所有字符串的空格.
	 * 
	 * @param map
	 *            传进去的Map,对象必须是Map
	 * @return 返回Map里面去除空格后的Map对象
	 */
	public static HashMap<String, Object> mapTrim(Map<String, Object> map) {
		for (Iterator<?> it = map.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry) it.next();
			entry.setValue(entry.getValue() instanceof String ? entry.getValue().toString().trim() : entry.getValue());
		}
		return new HashMap<String, Object>(map);
	}

	/**
	 * map值拷贝到标准javaBean。 最后的结果是,javaBean里面数据要跟map里面的对应才有相应的数据.
	 * 
	 * @param map
	 *            存放map的值。
	 * @param c
	 *            要拷贝的标准实体class类对象。
	 * @return Object javabean对象。
	 */
	public static <T> T mapCopyBean(Map<String, Object> map, Class<T> c) {
		Method[] methods = c.getDeclaredMethods();
		T t = null;
		String methodName = null;
		Class<?> fieldType = null;
		String field = null;
		try {
			t = c.newInstance();
			for (int i = 0; i < methods.length; i++) {
				methodName = methods[i].getName();
				field = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
				// 判断是否是set开头并参数个数大于0
				if (methodName.startsWith("set") && methods[i].getParameterTypes().length == 1) {
					fieldType = methods[i].getParameterTypes()[0];

					if (map.get(field) == null)
						continue;

					if (fieldType == String.class) {

						methods[i].invoke(t, String.valueOf(map.get(field)));

					} else if (fieldType == int.class || fieldType == Integer.class) {

						methods[i].invoke(t, Integer.valueOf(map.get(field).toString()));

					} else if (fieldType == long.class || fieldType == Long.class) {

						methods[i].invoke(t, Long.valueOf(map.get(field).toString()));

					} else if (fieldType == float.class || fieldType == Float.class) {

						methods[i].invoke(t, Float.valueOf(map.get(field).toString()));

					} else if (fieldType == double.class || fieldType == Double.class) {

						methods[i].invoke(t, Double.valueOf(map.get(field).toString()));

					} else if (fieldType == boolean.class || fieldType == Boolean.class) {

						methods[i].invoke(t, Boolean.valueOf(map.get(field).toString()));

					} else if (fieldType == Date.class) {

						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
						methods[i].invoke(t, formatter.parse(map.get(field).toString()));

					} else {
						throw new Exception(field + "字段未知类型:" + fieldType);
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 将大写的map转换成小写的 map. 一般是在从数据库取出数据时,存在map里的是大写,为了方便转为小写的
	 * 
	 * @param map
	 *            小写字段的map。
	 * @return 返回小写字段的map。
	 * 
	 */
	public static Map<String, Object> mapToLowerCase(Map<String, Object> map) {
		Map<String, Object> map1 = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			map1.put(key.toLowerCase(), map.get(key));
		}

		return map1;
	}

	/**
	 * 将大写的list转换成小写的list
	 * 
	 * @param list
	 *            大写的list
	 * @return 返回 小写list
	 */
	public static List<Map<String, Object>> listToLowerCase(List<Map<String, Object>> list) {
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			Map<String, Object> map2 = BeanUtils.mapToLowerCase(map);
			list2.add(map2);
		}
		return list2;
	}

	/**
	 * javaBean拷贝到map.
	 * 
	 * @param o
	 *            要拷贝的bean,必须是标准javaBean。
	 * @param map
	 *            存放map的值。
	 * @param isTrim
	 *            是否去空格
	 * @Description 把javaBean里面的所有属性对象全部拷贝到map中.
	 * @return map<String,Object> 返回map的值
	 * 
	 */
	public static Map<String, Object> beanCopyMap(Object o, Map<String, Object> map, boolean isTrim) {
		try {
			Method[] methods = o.getClass().getDeclaredMethods(); // 获取对象o中的所有字段
			Object fieldValue = null;
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					fieldValue = method.invoke(o);
					if (isTrim && fieldValue instanceof String)
						fieldValue = fieldValue.toString().trim();
					map.put(method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4), fieldValue);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * javaBean拷贝到map.
	 * 
	 * @param o
	 *            要拷贝的bean,必须是标准javaBean。
	 * @param map
	 *            存放map的值。
	 * @param isTrim
	 *            是否去空格
	 * @Description 把javaBean里面的所有属性对象全部拷贝到map中.
	 * @return map<String,Object> 返回map的值
	 * 
	 */
	public static Map<String, String> beanCopyMap2(Object o, Map<String, String> map, boolean isTrim) {
		try {
			Method[] methods = o.getClass().getDeclaredMethods(); // 获取对象o中的所有字段
			Object fieldValue = null;
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					fieldValue = method.invoke(o);
					if (isTrim && fieldValue instanceof String)
						fieldValue = fieldValue.toString().trim();
					map.put(method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4), fieldValue!= null ? fieldValue.toString() : "");
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 根据request给实体类设值
	 * 
	 * @author 翁斯波 2011-1-14
	 * @param objClass
	 * @param request
	 * @return
	 */
	public static <T> T getEntityByRequest(Class<T> objClass, HttpServletRequest request) {
		T obj = null;
		try {
			obj = objClass.newInstance();
			Field[] fields = objClass.getDeclaredFields(); // 获取自定义字段
			Class<?> fieldType; // 字段声明类型
			String fieldName; // 字段名称

			for (int i = 0; i < fields.length; i++) {
				fieldName = fields[i].getName();
				// 如果为空则跳过该字段
				if (request.getParameter(fieldName) == null) {
					continue;
				}
				fieldType = fields[i].getType(); // 表示字段的声明类型
				fields[i].setAccessible(true); // 设置不检查该字段是否可访问

				// 根据字段不同的类型,转换数据
				if (fieldType == String.class) {

					fields[i].set(obj, request.getParameter(fieldName));

				} else if (fieldType == int.class || fieldType == Integer.class) {

					fields[i].set(obj, Integer.valueOf(request.getParameter(fieldName)));

				} else if (fieldType == long.class || fieldType == Long.class) {

					fields[i].set(obj, Long.valueOf(request.getParameter(fieldName)));

				} else if (fieldType == float.class || fieldType == Float.class) {

					fields[i].set(obj, Float.valueOf(request.getParameter(fieldName)));

				} else if (fieldType == double.class || fieldType == Double.class) {

					fields[i].set(obj, Double.valueOf(request.getParameter(fieldName)));

				} else if (fieldType == boolean.class || fieldType == Boolean.class) {

					fields[i].set(obj, Boolean.valueOf(request.getParameter(fieldName)));

				} else if (fieldType == Date.class) {

					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
					fields[i].set(obj, formatter.parse(request.getParameter(fieldName)));

				} else {
					throw new Exception(fieldName + "字段未知类型:" + fieldType.toString());
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**
	 * 将传进来的Bean转成字符串输出
	 * @param obj
	 * @return
	 */
	public static String toLog(Object obj){
		StringBuffer sb = new StringBuffer("{");
		try {
			Method[] methods = obj.getClass().getDeclaredMethods(); // 获取对象o中的所有字段
			Object fieldValue = null;
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					fieldValue = method.invoke(obj);
					String column = method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4);
					sb.append(column+"="+fieldValue+",");
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if(sb.length()>1){
			sb.deleteCharAt(sb.length()-1);
		}
		sb.append("}");
		return sb.toString();
	}
}
