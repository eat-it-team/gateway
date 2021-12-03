package ru.eatit.gateway.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eatit.gateway.controller.entity.response.TvsUserDto;
import ru.eatit.gateway.service.TvsService;

@RestController
@RequestMapping("tvs")
@RequiredArgsConstructor
@Slf4j
public class TvsController {
    private final TvsService tvsService;

    @GetMapping("user")
    @CrossOrigin
    public ResponseEntity<TvsUserDto> findUser(@RequestHeader("Authorization") String accessToken) {
        log.info(accessToken);
        ResponseEntity<TvsUserDto> result = tvsService.findUser(accessToken);
        if (result.getStatusCode().is2xxSuccessful()) {
            return result;
        } else {
            return new ResponseEntity<>(result.getStatusCode());
        }
    }

}
