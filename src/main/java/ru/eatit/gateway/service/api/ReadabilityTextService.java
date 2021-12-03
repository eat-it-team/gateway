package ru.eatit.gateway.service.api;

/**
 * Оценка читаемости текстов необходима
 * для автоматического определения сложности текстов на русском языке.
 * https://github.com/ivbeg/readability.io/wiki/API
 * Есть открытое API.
 * Возможно даст понять насколько сложным языком пишет пользователь
 * и какая аудитория сможет его воспринять
 * Варианты текстов:
 *      тексты для внеклассного чтения;
 *      экспертно размеченные взрослые тексты;
 *      особо сложные тексты законов;
 *
 */
public interface ReadabilityTextService {
    Object analyse(String text);
}