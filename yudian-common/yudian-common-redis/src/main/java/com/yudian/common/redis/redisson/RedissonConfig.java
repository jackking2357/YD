package com.yudian.common.redis.redisson;

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(RedissonProperties.class)
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient getRedisson(RedissonProperties properties) {
        if (properties.getServerAddress() == null) {
            return null;
        }
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        singleServerConfig.setAddress(properties.getServerAddress() + ":" + properties.getPort());
        singleServerConfig.setDatabase(properties.getDatabase());
        String password = properties.getPassword();
        if (StringUtils.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
        return Redisson.create(config);
    }

}
