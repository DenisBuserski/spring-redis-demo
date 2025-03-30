package com.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CacheServiceImpl implements CacheService {
    private final CacheManager cacheManager;

    @Value("${redis.cache.product}")
    public String PRODUCT_CACHE;

    @Autowired
    public CacheServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void clearCache() {
        Cache cache = cacheManager.getCache(PRODUCT_CACHE);  // Use the cache name you want to clear
        if (cache != null) {
            cache.clear();  // Clear all cache entries for "PRODUCT_CACHE"
            log.info("Cache cleared successfully!");
        } else {
            log.info("Cache not found!");
        }
    }
}
