package ru.eatit.gateway.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskIdGenerator {

    public String generate() {
        return UUID.randomUUID().toString().replace("-","");
    }
}
