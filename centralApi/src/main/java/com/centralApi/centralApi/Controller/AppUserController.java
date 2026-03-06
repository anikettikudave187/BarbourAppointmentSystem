package com.centralApi.centralApi.Controller;

import com.centralApi.centralApi.Exceptions.UserNotFoundException;
import com.centralApi.centralApi.UtilClasses.Mapper;
import com.centralApi.centralApi.modelClasses.AppUser;
import com.centralApi.centralApi.requestBodyClasses.CreateUserRB;
import com.centralApi.centralApi.serviceClasses.AuthenticationService;
import com.centralApi.centralApi.serviceClasses.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/appUser")
public class AppUserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    Mapper mapper;

    @PostMapping("/create")
    public ResponseEntity createAppUser(@RequestBody CreateUserRB userRB){
        AppUser appUser=userService.createUser(userRB);
        return new ResponseEntity<>(appUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUserById(@RequestHeader("Authorization") String token,@PathVariable UUID userId){
        authenticationService.verifyToken(token);
        AppUser appUser=userService.getUserById(userId);
        if(appUser==null)throw new UserNotFoundException("User Not Valid");

        return new ResponseEntity<>(appUser,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(@RequestHeader("Authorization") String token,@RequestBody CreateUserRB userRB){
        authenticationService.verifyToken(token);
        AppUser appUser=userService.updateUser(userRB);
        return new ResponseEntity<>(appUser,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUserById(@RequestHeader("Authorization") String token,@PathVariable UUID userId){
        try{
            authenticationService.verifyToken(token);
            return new ResponseEntity<>(userService.deleteUserById(userId),HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }

    @GetMapping("/isOwner/{userId}")
    public boolean isUserShopOwner(@RequestHeader("Authorization") String token,@PathVariable UUID userId){
        try{
            authenticationService.verifyToken(token);
            boolean isOwner= userService.isShopOwner(userId);
            return isOwner;
        } catch (Exception e) {
            throw new UserNotFoundException(e.getMessage());
        }
    }
}
