package ru.eatit.gateway.service.api;

import ru.eatit.gateway.service.api.entity.DictionaryAnalyseResult;

/**
 * Анализирует текст на наличие слов из определенного словаря
 */
public interface DictionaryService {

    DictionaryAnalyseResult analyse(String text);
}
