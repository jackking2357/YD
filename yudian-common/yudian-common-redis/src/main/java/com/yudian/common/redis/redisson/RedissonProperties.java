package com.yudian.common.redis.redisson;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "redisson")
public class RedissonProperties {

    private String serverAddress;

    private String port;

    private String password;

    private Integer database;

    public Integer getDatabase() {
        if (null == database) {
            return 0;
        }
        return database;
    }
}
