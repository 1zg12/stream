package com.example.stream.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.config.Config;
@Slf4j
public class TestRedisson {
    Config config = new Config();
    {
        config.useSingleServer()
                .setAddress("redis://default:redispw@localhost:49153");
    }
    @Test
    public void redis() {
        RedissonClient redisson = Redisson.create(config);

// Reactive API
        RedissonReactiveClient redissonReactive = redisson.reactive();

// RxJava3 API
        RedissonRxClient redissonRx = redisson.rxJava();

        RMap<Object, Object> map = redisson.getMap("test");
        map.entrySet().stream().forEach(
                objectObjectEntry -> log.info(objectObjectEntry.getKey().toString())
        );
    }
}
