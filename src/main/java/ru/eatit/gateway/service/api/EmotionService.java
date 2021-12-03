package ru.eatit.gateway.service.api;

import ru.eatit.gateway.service.api.entity.EmotionResult;


public interface EmotionService {
    EmotionResult analyse(String text);
}
