package com.auth.authApi.Controller;

import com.auth.authApi.Integrations.DbApi;
import com.auth.authApi.ResponseBody.SuccessResponseBody;
import com.auth.authApi.ResponseBody.TokenResponseBody;
import com.auth.authApi.Service.AuthService;
import com.auth.authApi.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @Autowired
    DbApi dbApi;

    @GetMapping("/token")
    public ResponseEntity getToken(@RequestParam String email, @RequestParam String password){
        String jwtToken= authService.generateToken(email,password);
        TokenResponseBody responseBody=new TokenResponseBody();

        responseBody.setToken(jwtToken);
        AppUser user=dbApi.findUserByEmail(email);
        responseBody.setUserId(user.getUserId());

        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @GetMapping("/verify_token")
    public ResponseEntity verifyToken(){
        SuccessResponseBody successResponseBody=new SuccessResponseBody();
        successResponseBody.setStatus("success");

        return new ResponseEntity<>(successResponseBody,HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public AppUser verifyAndGetUser(@RequestHeader("Authorization") String authHeader){

        AppUser user=authService.verifyAndGetUser(authHeader);
        return user;
    }
}
