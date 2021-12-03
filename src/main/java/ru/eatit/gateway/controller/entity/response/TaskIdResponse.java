package ru.eatit.gateway.controller.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskIdResponse {
    private String taskId;
    private int delayMs;
    private String error;
    private List<String> files;
}
