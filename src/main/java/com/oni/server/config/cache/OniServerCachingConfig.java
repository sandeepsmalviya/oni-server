package com.oni.server.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;

@Configuration
@EnableCaching
public class OniServerCachingConfig {

	@Bean
	public EhCacheManagerFactoryBean cacheManager() {
		return new EhCacheManagerFactoryBean();
	}

//	@Bean
//	public EhCacheCacheManager myEhCacheConfig() {
//		// testEhCache Configuration - create configuration of cache that previous
//		// required XML
//		CacheConfiguration myEhCacheConfig = new CacheConfiguration()
//				.eternal(true) // if true, timeouts are ignored
//				.timeToIdleSeconds(5) // time since last accessed before item is marked for removal
//				.timeToLiveSeconds(10) // time since inserted before item is marked for removal
//				.maxEntriesLocalHeap(4) // total items that can be stored in cache
//				.memoryStoreEvictionPolicy("LRU") // eviction policy for when items exceed cache. LRU = Least Recently
//													// Used
//				.name("my-cache");
//
//		Cache myEhCache = new Cache(myEhCacheConfig);
//
//		cacheManager().getObject().addCache(myEhCache);
//		return new EhCacheCacheManager(cacheManager().getObject());
//	}
	
	
	@Bean
	@Primary
	public CacheManager oniServerEhCacheCacheManager() {
		// testEhCache Configuration - create configuration of cache that previous
		// required XML
		CacheConfiguration userEhCacheConfig = new CacheConfiguration()
				.eternal(false) // if true, timeouts are ignored
				.timeToIdleSeconds(30) // time since last accessed before item is marked for removal
				.timeToLiveSeconds(60) // time since inserted before item is marked for removal
				.maxEntriesLocalHeap(100) // total items that can be stored in cache
				.memoryStoreEvictionPolicy("LRU") // eviction policy for when items exceed cache. LRU = Least Recently
													// Used
				.name("user-cache");

		
		CacheConfiguration myEhCacheConfig = new CacheConfiguration()
				.eternal(false) // if true, timeouts are ignored
				.timeToIdleSeconds(30) // time since last accessed before item is marked for removal
				.timeToLiveSeconds(60) // time since inserted before item is marked for removal
				.maxEntriesLocalHeap(100) // total items that can be stored in cache
				.memoryStoreEvictionPolicy("LRU") // eviction policy for when items exceed cache. LRU = Least Recently
													// Used
				.name("my-cache");

		
		
		Cache userEhCache = new Cache(userEhCacheConfig);
		Cache myEhCache = new Cache(myEhCacheConfig);

		cacheManager().getObject().addCache(userEhCache);
		cacheManager().getObject().addCache(myEhCache);
		return new EhCacheCacheManager(cacheManager().getObject());
	}

	
//	@Bean	
//	public CacheManager myEhCacheCacheManager() {
//		// testEhCache Configuration - create configuration of cache that previous
//		// required XML
//		CacheConfiguration userEhCacheConfig = new CacheConfiguration()
//				.eternal(false) // if true, timeouts are ignored
//				.timeToIdleSeconds(30) // time since last accessed before item is marked for removal
//				.timeToLiveSeconds(60) // time since inserted before item is marked for removal
//				.maxEntriesLocalHeap(100) // total items that can be stored in cache
//				.memoryStoreEvictionPolicy("LRU") // eviction policy for when items exceed cache. LRU = Least Recently
//													// Used
//				.name("user-cache1");
//
//		
//		CacheConfiguration myEhCacheConfig = new CacheConfiguration()
//				.eternal(false) // if true, timeouts are ignored
//				.timeToIdleSeconds(30) // time since last accessed before item is marked for removal
//				.timeToLiveSeconds(60) // time since inserted before item is marked for removal
//				.maxEntriesLocalHeap(100) // total items that can be stored in cache
//				.memoryStoreEvictionPolicy("LRU") // eviction policy for when items exceed cache. LRU = Least Recently
//													// Used
//				.name("my-cache1");
//
//		
//		
//		Cache userEhCache = new Cache(userEhCacheConfig);
//		Cache myEhCache = new Cache(myEhCacheConfig);
//
//		cacheManager().getObject().addCache(userEhCache);
//		cacheManager().getObject().addCache(myEhCache);
//		return new EhCacheCacheManager(cacheManager().getObject());
//	}

	
}
