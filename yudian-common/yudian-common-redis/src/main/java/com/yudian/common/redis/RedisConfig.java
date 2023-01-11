package com.yudian.common.redis;

import com.yudian.common.redis.cache.SpringCacheProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Configuration
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisConfig {


    @Resource
    private SpringCacheProperties springCacheProperties;


























    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);


        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    
    public GenericJackson2JsonRedisSerializer serializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    
    @Bean("redisCacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration

                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer.UTF_8))

                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer()))

                .disableCachingNullValues()

                .prefixCacheNameWith("")

                .entryTtl(Duration.ofMinutes(30L));

        Map<String, RedisCacheConfiguration> map = new HashMap<>();
        Map<String, Long> ttlMap = springCacheProperties.getTtlMap();
        if (null != ttlMap) {
            Set<Map.Entry<String, Long>> entries = ttlMap.entrySet();
            for (Map.Entry<String, Long> entry : entries) {

                map.put("user", redisCacheConfiguration.entryTtl(Duration.ofSeconds(40)));
                map.put(entry.getKey(), redisCacheConfiguration.entryTtl(Duration.ofSeconds(entry.getValue())));
            }
        }
        return RedisCacheManager
                .builder(redisConnectionFactory)
                .enableStatistics()
                .cacheDefaults(redisCacheConfiguration)
                .withInitialCacheConfigurations(map)
                .build();
    }


    
    @Bean
    public KeyGenerator keyGenerator() {
        
        return (target, method, params) -> {

            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getSimpleName()).append(":");
            sb.append(method.getName()).append(":");

            Object key = SimpleKeyGenerator.generateKey(params);
            return sb.append(key);
        };
    }


}
