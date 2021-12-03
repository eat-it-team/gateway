package ru.eatit.gateway.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseCacheService {

    private final Map<String, Object> cache = new HashMap<>();

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public String get(String key) {
        return (String) cache.get(key);
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
    }

    public void delete(String key) {
        cache.remove(key);
    }
}
