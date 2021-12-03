package ru.eatit.gateway.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import ru.eatit.gateway.controller.entity.response.TaskIdResponse;

@Service
public class ResponseCacheService {

    private final Map<String, TaskIdResponse> cache = new HashMap<>();

    public void put(String key, TaskIdResponse value) {
        cache.put(key, value);
    }

    public TaskIdResponse get(String key) {
        return  cache.get(key);
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    public void delete(String key) {
        cache.remove(key);
    }
}
