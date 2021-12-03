package ru.eatit.gateway.service.impl;

import org.springframework.stereotype.Service;
import ru.eatit.gateway.service.api.EmotionService;
import ru.eatit.gateway.service.api.entity.EmotionResult;

@Service
public class MockEmotionServiceImpl implements EmotionService {

    @Override
    public EmotionResult analyse(String text) {
        EmotionResult.Emotion emotion = new EmotionResult.Emotion();
        emotion.emotion = "Happy";
        emotion.probabilities = new EmotionResult.Probabilities();
        emotion.probabilities.happy = 0.74d;
        return new EmotionResult(200, emotion);
    }
}
