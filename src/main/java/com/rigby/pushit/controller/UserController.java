package com.rigby.pushit.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rigby.pushit.model.User;
import com.rigby.pushit.service.UserService;
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

}
