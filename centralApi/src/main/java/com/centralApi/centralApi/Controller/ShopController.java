package com.centralApi.centralApi.Controller;

import com.centralApi.centralApi.Exceptions.ShopNotFoundException;
import com.centralApi.centralApi.UtilClasses.Mapper;
import com.centralApi.centralApi.modelClasses.AppUser;
import com.centralApi.centralApi.modelClasses.Shop;
import com.centralApi.centralApi.modelClasses.ShopService;
import com.centralApi.centralApi.requestBodyClasses.ShopRB;
import com.centralApi.centralApi.requestBodyClasses.ShopServiceRB;
import com.centralApi.centralApi.serviceClasses.AuthenticationService;
import com.centralApi.centralApi.serviceClasses.ShopServ;
import com.centralApi.centralApi.serviceClasses.UserService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/shop")
public class ShopController {
    @Autowired
    ShopServ shopServ;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    Mapper mapper;

    @PostMapping("/create")
    public ResponseEntity createShop(@RequestHeader("Authorization") String token, @RequestBody ShopRB shopRB){
        token=token.replace("Bearer ","");
        authenticationService.verifyToken(token);

        Shop shop=shopServ.createShop(shopRB);
        return new ResponseEntity<>(shop, HttpStatus.CREATED);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity getShopById(/*@RequestHeader("Authorization") String token,*/ @PathVariable UUID shopId){
        //authenticationService.verifyToken(token);
        Shop shop=shopServ.getShopById(shopId);
        if(shop==null)throw new ShopNotFoundException("shop is not found this id is not valid");
        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping("/myShops/{ownerId}")
    public List<Shop> getShopsByOwner(@RequestHeader("Authorization") String token,@PathVariable UUID ownerId){
        token=token.replace("Bearer ","");
        authenticationService.verifyToken(token);
        return shopServ.getShopsByOwner(ownerId);
    }

    @GetMapping("/getShopsByPincode")
    public List<Shop> getShopsByPincode(@RequestParam int pincode){
        return shopServ.getShopsByPincode(pincode);
    }

    @GetMapping("/getShopsByLocation")
    public List<Shop> getShopsByLocation(@RequestParam String city){
        return shopServ.getShopsByLocation(city);
    }

    @GetMapping("/distinctCities")
    public List<String> getAllCities(){
        return shopServ.getAllCities();
    }

    @PutMapping("/update/{shopId}")
    public ResponseEntity updateShop(@RequestHeader("Authorization") String token,@PathVariable UUID shopId,@RequestBody ShopRB shopRB){
        token=token.replace("Bearer ","");
        authenticationService.verifyToken(token);
        Shop shop=shopServ.getShopById(shopId);

        //shop=mapper.mapShopRBToShop(shopRB);

        return new ResponseEntity(shopServ.updateShop(shopRB),HttpStatus.OK);
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity deleteShopById(@RequestHeader("Authorization") String token,@PathVariable UUID shopId){
        authenticationService.verifyToken(token);
        shopServ.deleteShopById(shopId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{shopId}/addService/{shopServiceId}")
    public ResponseEntity addServiceToShop(@RequestHeader("Authorization") String token,@PathVariable UUID shopServiceId,@PathVariable UUID shopId){
        authenticationService.verifyToken(token);
        Shop shop=shopServ.addServiceToShop(shopServiceId,shopId);
        return new ResponseEntity<>(shop,HttpStatus.OK);
    }

    @GetMapping("/getServicesByShop/{shopId}")
    public Set<UUID> getServicesByShop(@PathVariable UUID shopId){
        return shopServ.getServicesByShop(shopId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Shop>> getAllShops(){

        return new ResponseEntity<>(shopServ.getAllShops(),HttpStatus.OK);
    }

}
