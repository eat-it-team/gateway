package ru.eatit.gateway.service.api;

/**
 * Оценка читаемости текстов необходима для автоматического определения сложности
 * текстов на русском языке, написанных самим пользователем
 * Есть открытое API. https://github.com/ivbeg/readability.io/wiki/API
 *
 * Возможно, даст понять насколько сложным языком пишет пользователь
 * и какая аудитория сможет его воспринять. Изначально оценки, представленные в API
 * рзрабатывались и применялись для оценки текстов инструкций к технике военными.
 * Позволяют сказать насколько сложен текст и для какой возрастной группы
 * ( школьники, студенты, специалисты доступен для понимания)
 * Варианты текстов:
 *      тексты для внеклассного чтения;
 *      экспертно размеченные взрослые тексты;
 *      особо сложные тексты законов;
 *      и т.д.
 *
 */
public interface ReadabilityTextService {
    Object analyse(String text);
}
