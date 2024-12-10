package com.person.lsj.stock.service.impl;

import com.person.lsj.stock.service.RedisOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisOpsServiceImpl implements RedisOpsService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void setValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getValue(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return "";
        }
        return String.valueOf(value);
    }

    @Override
    public <T> T getValueObj(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    @Override
    public void setMapValue(String mapName, String mapKey, String mapValue) {
        redisTemplate.opsForHash().put(mapName, mapKey, mapValue);
    }

    @Override
    public <T> T getMapValue(String mapName, String mapKey) {
        return (T) redisTemplate.opsForHash().get(mapName, mapKey);
    }
}
