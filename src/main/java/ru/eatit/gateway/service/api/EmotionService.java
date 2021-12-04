package ru.eatit.gateway.service.api;

import ru.eatit.gateway.service.api.entity.EmotionResult;

/**
 * Анализирует текст на наличие эмоционального окраса
 */
public interface EmotionService {
    EmotionResult analyse(String text);
}
