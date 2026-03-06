package com.centralApi.centralApi.Controller;

import com.centralApi.centralApi.serviceClasses.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    AuthenticationService authService;

    @GetMapping("/test")
    public String test(@RequestHeader("Authorization") String authorization) {

        String token = authorization.substring(7); // remove "Bearer "

        authService.verifyToken(token);

        return "Token is valid!";

    }

}
