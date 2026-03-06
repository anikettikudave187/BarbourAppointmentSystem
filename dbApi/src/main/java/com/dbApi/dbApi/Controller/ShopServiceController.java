package com.dbApi.dbApi.Controller;

import com.dbApi.dbApi.modelClasses.ShopService;
import com.dbApi.dbApi.repository.ShopserviceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/db/shopService")
public class ShopServiceController {
    @Autowired
    ShopserviceRepo shopserviceRepo;

    @PostMapping("/create")
    public ResponseEntity saveService(@RequestBody ShopService shopService){
        shopserviceRepo.save(shopService);
        return new ResponseEntity(shopService, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity findServiceById(@PathVariable UUID id){
        ShopService shopService=shopserviceRepo.findById(id).orElse(null);
        return new ResponseEntity(shopService, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ShopService updateService(@RequestBody ShopService shopService){
        ShopService saved=shopserviceRepo.save(shopService);
        return saved;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceById(@PathVariable UUID id){

        shopserviceRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
