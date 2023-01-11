package com.yudian.common.redis.cache;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "cache")
public class SpringCacheProperties {

    private Map<String, Long> ttlMap;
}
