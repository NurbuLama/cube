package com.cubesize.cube.config;

import com.cubesize.cube.entity.Cube;
import com.cubesize.cube.repository.CubeRepository;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class SpringCachingConfig {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(SpringCachingConfig.class);

    @Bean
    public CaffeineCacheManager caffeineCacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCacheNames(List.of("cubeCache"));
        caffeineCacheManager.setCaffeine(caffeineCacheBuilder());
        return caffeineCacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        logger.debug("Cache expiration time called!!!");
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(5))
                .maximumSize(100);
    }
}
