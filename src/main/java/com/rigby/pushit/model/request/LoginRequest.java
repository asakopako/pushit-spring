package com.rigby.pushit.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 4)
    private String password;

}
