package ru.eatit.gateway.service;

import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.eatit.gateway.controller.entity.response.TvsUserDto;
import ru.eatit.gateway.service.client.TvsClient;

@Service
@RequiredArgsConstructor
public class TvsService {

    private final TvsClient tvsClient;

    public ResponseEntity<TvsUserDto> findUser(String accessToken) {
        try {
            return tvsClient.find(accessToken);
        } catch (FeignClientException exc) {
            return new ResponseEntity<>(HttpStatus.resolve(exc.status()));
        }
    }
}
