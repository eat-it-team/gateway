package ru.eatit.gateway.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.eatit.gateway.controller.entity.response.TvsUserDto;

@FeignClient(url = "https://tvscp.tionix.ru/realms/master/protocol/openid-connect/userinfo", name = "TvsAuthFeign")
public interface TvsClient {

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<TvsUserDto> find(@RequestHeader("Authorization") String accessToken);
}
