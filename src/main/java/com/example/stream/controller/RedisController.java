package com.example.stream.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RedissonReactiveClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("redis")
@Slf4j
public class RedisController {

    RedissonReactiveClient client;
    ReactiveRedisTemplate template;

    RedisController(RedissonReactiveClient client, @Qualifier("reactiveRedisTemplate") ReactiveRedisTemplate template){
        this.client = client;
        this.template = template;
    }

    @GetMapping("get")
    public Flux<Map.Entry<Object, Object>> get(String name){
        RMapCacheReactive<Object, Object> mapCache = client.getMapCache(name);
        return mapCache.entryIterator();
//        return mapCache.keyIterator()
//                .log()
//                .map(s -> mapCache.get(s))
//                .collectList()
//                .block();
    }
    @PostMapping("post")
    public Mono<Object> post(String name, String key, String value){
        RMapCacheReactive<Object, Object> mapCache = client.getMapCache(name);
        return mapCache.put(key, value);
    }
}
