package ru.eatit.gateway.service.api.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Set;

@Getter
@Setter
public class DictionaryAnalyseResult {

    private final boolean conatains;
    private final Set<String> dictionaryWords;

    public DictionaryAnalyseResult(Set<String> dictionaryWords) {
        this.conatains = !dictionaryWords.isEmpty();
        this.dictionaryWords = dictionaryWords;
    }

    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
