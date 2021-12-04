package ru.eatit.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eatit.gateway.service.api.TextParseService;

import java.io.IOException;

/**
 * Пример словаря матных слов - лучший из нагугленных в интернете на эту тему.
 * Оставили реальный - так как сам по себе он несет хорошую ценность и может быть применен вами где-то еще.
 */
@Service
public class MatDictionaryService extends ru.eatit.gateway.service.impl.AbstractDictionaryService {

    @Autowired
    public MatDictionaryService(TextParseService textParseService) throws IOException {
        super(textParseService, "mat.txt");
    }


}
