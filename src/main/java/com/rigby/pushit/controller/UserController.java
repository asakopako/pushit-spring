package com.rigby.pushit.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rigby.pushit.model.User;
import com.rigby.pushit.model.request.LoginRequest;
import com.rigby.pushit.model.response.LoginResponse;
import com.rigby.pushit.service.UserService;
import com.rigby.pushit.service.tool.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserController {

    @Autowired private UserService userService;


    @PostMapping("/api/users/register")
    public ResponseEntity<Void> postRegister(@Valid @RequestBody User user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @PostMapping("/api/users/login")
    public ResponseEntity<LoginResponse> postLogin(@Valid @RequestBody LoginRequest loginRequest) {

        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setId(user.getId());
        loginResponse.setEmail(user.getEmail());
        loginResponse.setName(user.getName());
        loginResponse.setToken(JwtTool.createToken(user.getEmail()));

        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

}
