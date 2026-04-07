package com.brundhavan.foodapi.controller;

import com.brundhavan.foodapi.IO.UserRequest;
import com.brundhavan.foodapi.IO.UserResponse;
import com.brundhavan.foodapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponse UserRegister(@RequestBody UserRequest userRequest){
        return userService.userRegister(userRequest);
    }
}
