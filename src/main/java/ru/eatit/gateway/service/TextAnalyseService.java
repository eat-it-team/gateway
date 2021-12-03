package ru.eatit.gateway.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eatit.gateway.controller.entity.response.TaskIdResponse;

import java.util.List;
import java.util.Set;

@Service
public class TextAnalyseService {

    private final TaskIdGenerator taskIdGenerator;
    private final ResponseCacheService responseCacheService;
    private final HadoopService hadoopService;

    @Autowired
    public TextAnalyseService(TaskIdGenerator taskIdGenerator, ResponseCacheService responseCacheService, HadoopService hadoopService) {
        this.taskIdGenerator = taskIdGenerator;
        this.responseCacheService = responseCacheService;
        this.hadoopService = hadoopService;
    }

    public TaskIdResponse goTextToAnalyse(Set<String> keyWords) {
        try {
            String taskId = taskIdGenerator.generate();
            //признак того что ответ не пришел - null
            responseCacheService.put(taskId, null);
            hadoopService.analyseFiles(taskId, keyWords);
            return new TaskIdResponse(taskId, 5000, null, null);
        } catch (Exception e) {
            return new TaskIdResponse(null, 5000, ExceptionUtils.getStackTrace(e), null);
        }
    }

    public TaskIdResponse findResponse(String taskId) {
        try {

            if (responseCacheService.contains(taskId)) {
                TaskIdResponse response = responseCacheService.get(taskId);
                if (response != null) { //пришел ответ из kafka
                    return response;
                } else { //просим прийти позже
                    return new TaskIdResponse(taskId, 5000, null, null);
                }
            } else {
                return new TaskIdResponse(null, 0, "not_found", null);
            }

        } catch (Exception e) {
            return new TaskIdResponse(null, 5000, ExceptionUtils.getStackTrace(e), null);
        }
    }


}
