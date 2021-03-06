package ru.eatit.gateway.controller;

import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.eatit.gateway.controller.entity.response.TaskIdResponse;
import ru.eatit.gateway.controller.entity.response.TvsUserDto;
import ru.eatit.gateway.service.TextAnalyseService;
import ru.eatit.gateway.service.TvsService;

import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
public class TextAnalyseController {

    private final TextAnalyseService textAnalyseService;
    private final TvsService tvsService;

    @Autowired
    public TextAnalyseController(TextAnalyseService textAnalyseService, TvsService tvsService) {
        this.textAnalyseService = textAnalyseService;
        this.tvsService = tvsService;
    }

    @PostMapping("text/analyse")
    public TaskIdResponse put(@RequestHeader("Authorization") String accessToken) {
        log.info("Request analyse: " + accessToken);
        ResponseEntity<TvsUserDto> result = tvsService.findUser(accessToken);
        if (result.getStatusCode().is2xxSuccessful()) {
            Set<String> keyWords = new HashSet<>();
            keyWords.add(result.getBody().getFamily_name());
            keyWords.add(result.getBody().getGiven_name());
            return textAnalyseService.goTextToAnalyse(keyWords);
        } else {
            return new TaskIdResponse(null, 0, "Unauthorized", Collections.emptyList());
        }
    }

    @GetMapping("text/analyse/{taskId}")
    public TaskIdResponse get(@PathVariable("taskId") String taskId) {
        log.info("Request result " + taskId);
        return textAnalyseService.findResponse(taskId);
    }
}
