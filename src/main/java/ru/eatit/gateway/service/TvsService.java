package ru.eatit.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.eatit.gateway.controller.entity.response.TvsUserDto;
import ru.eatit.gateway.service.client.TvsClient;

@Service
@RequiredArgsConstructor
public class TvsService {

    private final TvsClient tvsClient;

    public ResponseEntity<TvsUserDto> findUser(String accessToken) {
        return tvsClient.find(accessToken);
    }
}
