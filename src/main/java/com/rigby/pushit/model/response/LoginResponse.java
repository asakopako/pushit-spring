package com.rigby.pushit.model.response;

import lombok.Data;

@Data
public class LoginResponse {

    private Long id;
    private String token;
    private String email;
    private String name;

}
