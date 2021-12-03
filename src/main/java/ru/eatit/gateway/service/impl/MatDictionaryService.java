package ru.eatit.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eatit.gateway.service.api.TextParseService;

import java.io.IOException;

@Service
public class MatDictionaryService extends ru.eatit.gateway.service.impl.AbstractDictionaryService {

    @Autowired
    public MatDictionaryService(TextParseService textParseService) throws IOException {
        super(textParseService, "mat.txt");
    }


}
