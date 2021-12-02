package ru.eatit.gateway.controller.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskIdResponse {
    private String taskId;
    private int delayMs;
    private String error;
    private String response;
}
