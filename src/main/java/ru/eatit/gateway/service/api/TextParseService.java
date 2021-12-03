package ru.eatit.gateway.service.api;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextParseService {

    public Set<String> getWords(String text) {
        Pattern pattern =
                Pattern.compile("\\w+", Pattern.UNICODE_CHARACTER_CLASS
                        | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        SortedSet<String> words = new TreeSet<>();

        while (matcher.find()) {
            words.add(matcher.group().toLowerCase());
        }
        return words;
    }
}
