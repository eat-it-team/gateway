package ru.eatit.gateway.controller.entity.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TvsUserDto {

    private String sub;
    private String given_name;
    private String family_name;
    private String username;
}
