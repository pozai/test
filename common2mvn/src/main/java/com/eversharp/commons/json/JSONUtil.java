package com.eversharp.commons.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;

/**
 * description:json的基本操作
 * 
 * @author huaye
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
@SuppressWarnings("deprecation")
public class JSONUtil {

	/** 全局对象映射（线程安全） */
	private static final ObjectMapper	mapper	= new ObjectMapper();

	static {
		mapper.getSerializationConfig().setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 对象转json
	 * 
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "{}";
	}

	/**
	 * json转对象
	 * 
	 * @param json
	 * @param classType
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> classType) {
		try {
			return mapper.readValue(json, classType);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * json转数组
	 * 
	 * @param json
	 * @param elementType
	 * @return
	 */
	public static <T> T toArray(String json, Class<T> elementType) {
		try {
			return mapper.readValue(json, TypeFactory.arrayType(elementType));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 还没有实现
	 * 
	 * @param json
	 * @return
	 */
	public static String toXML(String json) {
		return "";
	}

	/**
	 * 转换为相应的集合
	 * 
	 * @param json
	 * @param collectionType
	 * @param elementType
	 * @return
	 */
	public static <C extends Collection<T>, T> C toCollection(String json, Class<C> collectionType, Class<T> elementType) {
		try {
			return mapper.readValue(json, TypeFactory.collectionType(collectionType, elementType));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> toSimpleMap(String json) {

		try {
			return (Map<String, String>) mapper.readValue(json, TypeFactory.mapType(Map.class, String.class, String.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new HashMap<String, String>();
	}
	
	@SuppressWarnings("unchecked")
    public static Map<String, Object> toSimpleObjMap(String json) {

        try {
            return (Map<String, Object>) mapper.readValue(json, TypeFactory.mapType(Map.class, String.class, Object.class));
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<String, Object>();
    }

	/**
	 * 将json数组转换为list<map>形式
	 * 
	 * @param arrayJson
	 * @return
	 */
	public static List<Map<String, String>> toListMap(String arrayJson) {

		List<Map<String, String>> beanList = null;;
		try {
			beanList = mapper.readValue(arrayJson, new TypeReference<List<Map<String, String>>>() {});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return beanList;
	}
	
	public static void main(String[] args) {
	    Map<String, String> resultMap = toSimpleMap("{\"Status\":-101,\"RoleInfo\":null}");
	    System.out.println(resultMap.get("Status"));
    }
}
