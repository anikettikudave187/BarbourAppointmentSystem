package com.centralApi.centralApi.Controller;

import com.centralApi.centralApi.Exceptions.ShopServiceNotFoundException;
import com.centralApi.centralApi.modelClasses.Shop;
import com.centralApi.centralApi.modelClasses.ShopService;
import com.centralApi.centralApi.requestBodyClasses.ShopServiceRB;
import com.centralApi.centralApi.serviceClasses.AuthenticationService;
import com.centralApi.centralApi.serviceClasses.ShopServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/shopService")
public class ShopServiceController {
    @Autowired
    ShopServiceClass shopServiceClass;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity createShopService(@RequestBody ShopServiceRB shopServiceRB){
        return new ResponseEntity<>(shopServiceClass.createShopService(shopServiceRB), HttpStatus.CREATED);
    }

    @PutMapping("/addService/{shopId}")
    public ResponseEntity addServiceToShop(@PathVariable UUID shopId,@RequestBody ShopServiceRB shopServiceRB){
        return new ResponseEntity<>(shopServiceClass.addService(shopId,shopServiceRB),HttpStatus.OK);
    }

    @GetMapping("/{shopServiceId}")
    public ResponseEntity getShopServiceById(/*@RequestHeader("Authorization") String token,*/ @PathVariable UUID shopServiceId){
        //authenticationService.verifyToken(token);
        ShopService shopService=shopServiceClass.getShopServiceById(shopServiceId);
        if(shopService==null)throw new ShopServiceNotFoundException("this Service Not Available");
        return new ResponseEntity<>(shopService,HttpStatus.OK);
    }

    @PutMapping("/update/{serviceId}")
    public ResponseEntity updateShopService(@RequestHeader("Authorization") String token,@PathVariable UUID serviceId,@RequestBody ShopServiceRB shopServiceRB){
        token=token.replace("Bearer ","");
        authenticationService.verifyToken(token);

        //ShopService shopService=getShopServiceById(id);
        return new ResponseEntity<>(shopServiceClass.updateShopService(shopServiceRB), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{shopServiceId}")
    public ResponseEntity<Void> deleteShopServiceById(/*@RequestHeader("Authorization") String token,*/@PathVariable UUID shopServiceId){
        //authenticationService.verifyToken(token);
        ResponseEntity<Void> resp=(shopServiceClass.deleteShopServiceById(shopServiceId));
        return resp;
    }

    @PutMapping("/removeService/shop/{shopId}/service/{serviceId}")
    public void removeServiceFromShop(@PathVariable UUID shopId,@PathVariable UUID serviceId){
        shopServiceClass.removeService(shopId,serviceId);

    }
}
