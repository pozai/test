/*
 *Copyright(C) 2012 www.eversharp.cn
 *All right reserved.
 */
package com.eversharp.commons.cache.memcached;

import java.util.HashMap;
import java.util.Map;

import com.eversharp.commons.cache.ICache;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;


/**
 * description:mencache管理类
 * 
 * @author qingpozhang(fz.qingpozhang@gmail.com)
 * @version 1.0
 * @date 2013-1-9
 */
public class MemCacheManager {
    
	/** memcached客户端map集合 */
    private static Map<String, MemCachedClient> memCacheManagerMap = new HashMap<String, MemCachedClient>();

	/** 缓存池名称 */
	private static final String POOL_NAME = "3595_pool";
	
	/** 默认缓存名称 */
    private static final String DEFAULT_CACHE_NAME = "3595_cache";
	
	static {
		// 设置缓存服务器列表，当使用分布式缓存的时，可以指定多个缓存服务器
		// 外网memcached服务器117.27.142.135 对应内网IP为 "10.3.1.32:11211"
		// 测试的时候需要改为测试机的memcached服务器地址
	    String[] servers =
	    {
	       "10.3.52.105:11211"
	    };
		// 与服务器列表中对应的各服务器的权重
	    Integer[] weights = {3};

		// 创建Socked连接池
	    SockIOPool pool = SockIOPool.getInstance(POOL_NAME);

		// 向连接池设定服务器和权重
	    pool.setServers( servers );
	    pool.setWeights( weights );
	    
		// 连接池参数
	    pool.setInitConn( 50 );
	    pool.setMinConn( 50 );
	    pool.setMaxConn( 500 );
	    pool.setMaxIdle( 1000 * 60 * 60 );

	    // set the sleep for the maint thread
	    // it will wake up every x seconds and
	    // maintain the pool size
	    pool.setMaintSleep( 30000 );

	    // set some TCP settings
	    // disable nagle
	    // set the read timeout to 3 secs
	    // and don't set a connect timeout
	    pool.setNagle( false );
	    pool.setSocketTO( 3000 );
	    pool.setSocketConnectTO( 0 );

	    // initialize the connection pool
	    pool.initialize();

	}
	
	/**
	 * 默认构造器
	 * 
	 */
    public MemCacheManager() {
        this(DEFAULT_CACHE_NAME);
    }
	
	/**
	 * 构造器
	 *
	 * @param cacheName
	 */
	public MemCacheManager(String cacheName){
	    if(!memCacheManagerMap.containsKey(cacheName)){
	        createMemCacheManager(cacheName);
	    }
	}

	/**
	 * 获取一个MemCachedClient
	 * 
	 * @param cacheName
	 * @return
	 */
	private MemCachedClient createMemCacheManager(String cacheName) {
	    
	    MemCachedClient memCachedClient = new MemCachedClient(POOL_NAME);
        memCacheManagerMap.put(cacheName, memCachedClient);
		return memCachedClient;
	}

	/**
	 * 根据名称获取一个cache
	 * 
	 * @param cacheName
	 * @return
	 */
	public <K, V> ICache<K, V> createCache(String cacheName) {
	    
	    MemCachedClient memCachedClient = memCacheManagerMap.get(cacheName);
		ICache<K, V> ehcache = new MemCacheProxy<K, V>(memCachedClient);
		return ehcache;
	}
}
