/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.cache.simplecache;

import java.util.HashMap;
import java.util.Map;

import com.eversharp.commons.cache.ICache;


/**
 * description:自己创建一个简单静态缓存
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @param <K>
 * @param <V>
 * @date 2012-7-10
 */
public abstract class StaticCache<K, V> implements ICache<K, V> {
	

	/** 缓存池 */
	protected final Map<K, V>	pools	= new HashMap<K, V>();

	/** 缓存别名 */
	private String				id;

	/** 中文名称，用来提示 */
	private String				caption;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * 通过key获取值
	 */
	public V get(K key) {
		return pools.get(key);
	}

	/**
	 * 获取全部的cache
	 */
	@Override
	public Map<K, V> getMap() {
		return pools;
	}

	/**
	 * 获取缓存大小
	 * 
	 * @return
	 */
	public int size() {
		return pools.size();
	}

	/**
	 * 刷新缓存区域，重新从配置文件中加载缓存
	 */
	public void refresh() {
		try {
			synchronized (this.pools) {
				this.pools.clear(); // 清空原有数据
				load(this.pools);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从静态文件中读取数据，并使之缓存
	 * 
	 * @param pools
	 * @throws Exception
	 */
	protected abstract void load(Map<K, V> pools) throws Exception;
}

