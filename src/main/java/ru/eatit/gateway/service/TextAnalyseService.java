package ru.eatit.gateway.service;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.eatit.gateway.common.InputJSONFields;
import ru.eatit.gateway.controller.entity.response.TaskIdResponse;
import ru.eatit.gateway.service.kafka.TextAnalyseKafkaProducer;

@Service
public class TextAnalyseService {

    private final TaskIdGenerator taskIdGenerator;
    private final ResponseCacheService responseCacheService;
    private final TextAnalyseKafkaProducer kafkaProducer;

    @Autowired
    public TextAnalyseService(TaskIdGenerator taskIdGenerator, ResponseCacheService responseCacheService, TextAnalyseKafkaProducer kafkaProducer) {
        this.taskIdGenerator = taskIdGenerator;
        this.responseCacheService = responseCacheService;
        this.kafkaProducer = kafkaProducer;
    }

    public TaskIdResponse goTextToAnalyse(String text) {
        try {
            String taskId = taskIdGenerator.generate();
            //признак того что ответ не пришел - null
            responseCacheService.put(taskId, null);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(InputJSONFields.TEXT, text);
            jsonObject.put(InputJSONFields.USE_MAT_ANALYSE, true);
            jsonObject.put(InputJSONFields.USE_EMOTION_ANALYSE, true);
            kafkaProducer.sendMessage(taskId, jsonObject.toString());
            return new TaskIdResponse(taskId, 5000, null, null);
        } catch (Exception e) {

            return new TaskIdResponse(null, 5000, ExceptionUtils.getStackTrace(e), null);
        }
    }

    public TaskIdResponse findResponse(String taskId) {
        try {

            if (responseCacheService.contains(taskId)) {
                Object response = responseCacheService.get(taskId);
                if (response != null) { //пришел ответ из kafka
                    responseCacheService.delete(taskId);
                    return new TaskIdResponse(taskId, 5000, null, (String) response);

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
