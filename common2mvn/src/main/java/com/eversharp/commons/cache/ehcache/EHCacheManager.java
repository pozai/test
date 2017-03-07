/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.cache.ehcache;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import com.eversharp.commons.cache.ICache;
import com.eversharp.commons.util.ClassLoaderUtil;


/**
 * description:ehcache管理类相对于多个配置文件而存在的管理
 * 
 * @author wu_quanyin(wuquanyin1201@gmail.com)
 * @version 1.0
 * @date 2012-7-10
 */
public class EHCacheManager {

	/** ehaceh提供的管理类 */
	private static CacheManager										cacheManager;

	/** 存放多个cacheManager */
	private static final ConcurrentHashMap<String, CacheManager>	concurrentHashMap		
																	= new ConcurrentHashMap<String, CacheManager>();

	/** 配置文件路径 */
	private static final String										DEFAULT_EHCACHE_CONFIG	= "ehcache.xml";

	/** 通过配置文件 */
	public EHCacheManager(String ehcacheConfigFile) {
		cacheManager = concurrentHashMap.get(ehcacheConfigFile);

		if (cacheManager == null) {
			cacheManager = createEHCacheManager(ehcacheConfigFile);
			concurrentHashMap.put(ehcacheConfigFile, cacheManager);
		}
	}

	/** 创建默认xml的ehcache管理*/
	public EHCacheManager() {
		this(DEFAULT_EHCACHE_CONFIG);
	}

	/**
	 * 根据配置文件，创建一个ehcache
	 * 
	 * @param ehcacheConfigFile
	 * @return
	 */
	private CacheManager createEHCacheManager(String ehcacheConfigFile) {
		InputStream inputStream = ClassLoaderUtil
								.getResourceAsStream(ehcacheConfigFile, EHCacheManager.class);
		cacheManager = CacheManager.create(inputStream);

		return cacheManager;
	}

	/**
	 * 根据名称获取一个cache
	 * 
	 * @param cacheName
	 * @return
	 */
	public <K, V> ICache<K, V> createCache(String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		ICache<K, V> ehcache = new com.eversharp.commons.cache.ehcache.EHCacheProxy<K, V>(cache);

		return ehcache;
	}
}
