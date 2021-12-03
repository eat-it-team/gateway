package ru.eatit.gateway.service.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;


public class EmotionResult {

    public int code; //200
    public Emotion emotion;

    public EmotionResult(int code, Emotion emotion) {
        this.code = code;
        this.emotion = emotion;
    }

    public static class Probabilities {
        @JsonProperty("Angry")
        public double angry;
        @JsonProperty("Bored")
        public double bored;
        @JsonProperty("Excited")
        public double excited;
        @JsonProperty("Fear")
        public double fear;
        @JsonProperty("Happy")
        public double happy;
        @JsonProperty("Sad")
        public double sad;
    }

    public static class Emotion {
        public String emotion;
        public Probabilities probabilities;
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}



