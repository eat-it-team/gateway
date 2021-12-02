package ru.eatit.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.eatit.gateway.controller.entity.request.AnalyseTextDto;
import ru.eatit.gateway.controller.entity.response.TaskIdResponse;
import ru.eatit.gateway.service.TextAnalyseService;

@RestController
public class TextAnalyseController {

    private final TextAnalyseService textAnalyseService;

    @Autowired
    public TextAnalyseController(TextAnalyseService textAnalyseService) {
        this.textAnalyseService = textAnalyseService;
    }

    @PostMapping("text/analyse")
    public TaskIdResponse put(@RequestBody AnalyseTextDto analyseTextDto) {
        return textAnalyseService.goTextToAnalyse(analyseTextDto.getText());
    }

    @GetMapping("text/analyse/{taskId}")
    public TaskIdResponse get(@PathVariable("taskId") String taskId) {
        return textAnalyseService.findResponse(taskId);
    }
}
