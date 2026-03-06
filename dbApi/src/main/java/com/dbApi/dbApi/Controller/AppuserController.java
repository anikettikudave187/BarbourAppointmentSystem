package com.dbApi.dbApi.Controller;

import com.dbApi.dbApi.modelClasses.AppUser;
import com.dbApi.dbApi.repository.AppuserRepo;
import com.dbApi.dbApi.responseBody.AdminRB;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/db/user")
@Slf4j
public class AppuserController {

    private static final Logger log= LoggerFactory.getLogger(AppuserController.class);


    @Autowired
    AppuserRepo appuserRepo;

    @PostMapping("/create")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        AppUser saved=appuserRepo.save(user);
        log.info("recieved request with the requestbody:"+ user.toString());
        return new ResponseEntity<>(saved,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity findUserById(@PathVariable UUID id){
        AppUser user=appuserRepo.findById(id).orElse(null);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateUserById(@RequestBody AppUser user){
        appuserRepo.save(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUserById(@PathVariable UUID id){
        appuserRepo.deleteById(id);
        return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
    }

    @GetMapping("/admins")
    public ResponseEntity getAdmins(){
        List<AppUser> admins=appuserRepo.getAllAdmins();
        AdminRB response=new AdminRB();

        response.setAdmins(admins);
        return new ResponseEntity<>(response,HttpStatus.OK);

    }

    @GetMapping("/email/{email}")
    public AppUser getUserByEmail(@PathVariable String email){
        AppUser appUser=appuserRepo.findByEmail(email);
        return appUser;
    }
}
