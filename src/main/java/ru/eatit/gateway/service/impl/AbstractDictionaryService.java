package ru.eatit.gateway.service.impl;

import ru.eatit.gateway.service.api.DictionaryService;
import ru.eatit.gateway.service.api.TextParseService;
import ru.eatit.gateway.service.api.entity.DictionaryAnalyseResult;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/*
   Абстрактный класс, проверяющий что текст содержит ключевые слова из словаря. Слово "Содержит" не подразумевает
   прямого поиска: можно использовать нечеткий поиск на основе триграмм, расстояния Левенштейна и прочего.

   В дальнейшем предполагалось хранение всех словарей в бд и наличие API для их создания и редактирования.

   Использование специализированных словарей и определения наличия слов из них в произвольном тексте позволит узнать
   тему текста и подобрать какой-то маркер к самому пользователю на основе следующих соображений.
   Как правило у разных групп населения есть свой дискурс (набор определенных слов-маркеров) в заисимости от его предпочтений.
   Например у коммунистов есть свои термины (Определенный набор слов-маркеров: революция, классовая борьба, пролетариат, эксплуататор)
   У представителей ЛГБТ-сообщества - свой. У релегиозных свой.
   Определение того, что текст написан самим пользователем (новость или комментарий в соцсети) позволит,
   сделав спецефический словарь, определить предполагаемую остросоциальную тему текста и (после анализа многих текстов) обозначить возможные интересы пользователя

   Вариант использования:
       1) Создаем специализированные словари (словарь матных слов/ относящихся к 18+/к религии/ к политике/ к ковиду; словарь Олбанского)
       2) Определяем что текст написан самим пользователем или от его лица
       3) Проверяем, есть ли в тексте слова из данного словаря на основании расстояния Левенштейна, триграмм и других более сложных способов в дальнейшем
       4) Это может помочь сказать кое-что о человеке (интересуется ли он политикой, использует ли мат и т.д)
       5) при добавлении еще и анализа на наличие эмоций - позволит выявить и отношение человека к данной теме.
 */
public abstract class AbstractDictionaryService implements DictionaryService {

    private final Set<String> dictionary = new HashSet<>();

    private final TextParseService textParseService;

    public AbstractDictionaryService(TextParseService textParseService, String filename) throws IOException {
        this.textParseService = textParseService;
        loadDictionary(filename);
        System.out.println(dictionary.size());
    }

    public DictionaryAnalyseResult analyse(String text) {
        text = text.toLowerCase();
        Set<String> words = textParseService.getWords(text);

        Set<String> result = new HashSet<>();
        for (String word : words) {
            //TODO: В дальнейшем этот for можно вынести MAP-REDUCE в ArenaData
            for (String dictWord : dictionary) {
                boolean eq = levenshtein_equals(word, dictWord, false);
                if (eq) {
                    result.add(word);
                    break;
                }
            }
        }
        return new DictionaryAnalyseResult(result);
    }

    protected boolean loadDictionary(String filename) throws IOException {
        dictionary.clear();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("dictionaries/" + filename).getFile());
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            return false;
        }
        InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);
        for (String line; (line = reader.readLine()) != null; ) {
            dictionary.add(line);
        }
        return !dictionary.isEmpty();
    }


    public boolean levenshtein_equals(String stringOne, String stringTwo, boolean caseSensitive) {

        if (stringOne.length() < 3) {
            return false;
        }
        if (!caseSensitive) {
            stringOne = stringOne.toLowerCase();
            stringTwo = stringTwo.toLowerCase();
        }

        // store length
        int m = stringOne.length();
        int n = stringTwo.length();

        // matrix to store differences
        int[][] deltaM = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            deltaM[i][0] = i;
        }

        for (int j = 1; j <= n; j++) {
            deltaM[0][j] = j;
        }

        for (int j = 1; j <= n; j++) {
            for (int i = 1; i <= m; i++) {
                if (stringOne.charAt(i - 1) == stringTwo.charAt(j - 1)) {
                    deltaM[i][j] = deltaM[i - 1][j - 1];
                } else {
                    deltaM[i][j] = Math.min(
                            deltaM[i - 1][j] + 1,
                            Math.min(
                                    deltaM[i][j - 1] + 1,
                                    deltaM[i - 1][j - 1] + 1
                            )
                    );
                }
            }
        }

        int delta = deltaM[m][n];
        return delta < 2; //количество замен символов в строке, чтобы получилась другая < 2.
    }
}
