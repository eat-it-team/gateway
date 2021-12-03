package ru.eatit.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eatit.gateway.service.HadoopService;

@RestController
public class HadoopController {

    private final HadoopService hadoopService;

    @Autowired
    public HadoopController(HadoopService hadoopService) {
        this.hadoopService = hadoopService;
    }

    @GetMapping("findBy")
    public String all() {

        return "ok";
    }


}
