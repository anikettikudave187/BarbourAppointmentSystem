package com.dbApi.dbApi.Controller;

import com.dbApi.dbApi.modelClasses.Shop;
import com.dbApi.dbApi.modelClasses.ShopService;
import com.dbApi.dbApi.repository.ShopRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/db/shop")
public class ShopController {
    @Autowired
    ShopRepo shopRepo;

    @PostMapping("/create")
    public ResponseEntity createShop(@RequestBody Shop shop){

        if(shopRepo.findByOwnerAndNameAndAddress(shop.getOwner(),shop.getName(),shop.getAddress()).isPresent()){
            throw new RuntimeException("Already Existing Shop");
        }

        Shop saved=shopRepo.save(shop);
        return new ResponseEntity(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity getShopById(@PathVariable UUID shopId){
        Shop shop=shopRepo.findById((shopId)).orElse(null);
        return new ResponseEntity(shop,HttpStatus.OK);
    }

    @GetMapping("/myShops/{ownerId}")
    public List<Shop> getShopsByOwner(@PathVariable UUID ownerId){
        return shopRepo.getShopsByOwner(ownerId);
    }

    @PutMapping("/update")
    public ResponseEntity updateShop(@RequestBody Shop shop){
        Shop saved=shopRepo.save(shop);
        return new ResponseEntity(saved,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShopById(@PathVariable UUID id){
        shopRepo.deleteById(id);
        return new ResponseEntity<>("deleted successfully",HttpStatus.NO_CONTENT);

    }

    @GetMapping("/getServicesByShop/{shopId}")
    public Set<UUID> getServicesByShopId(@PathVariable UUID shopId){
        Set<String> servicesInString=shopRepo.getServicesByShopId(shopId);

        Set<UUID> servicesByShop=new HashSet<>();

        for(String service:servicesInString){
            servicesByShop.add(UUID.fromString(service));
        }

        return servicesByShop;
    }

    @GetMapping("/all")
    public List<Shop> getAllShops(){
        return shopRepo.findAll();
    }

    @PutMapping("/{shopId}/addService/{shopServiceId}")
    public ResponseEntity addServiceToShop(@PathVariable UUID shopServiceId,@PathVariable UUID shopId){
        Shop shop=shopRepo.findById(shopId).orElse(null);
        if(shop==null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        Set<UUID> services=shop.getShopServiceSet();
        services.add(shopServiceId);
        shop.setShopServiceSet(services);
        Shop saved=shopRepo.save(shop);
        return new ResponseEntity<>(saved,HttpStatus.OK);
    }

    @GetMapping("/searchByPincode")
    public List<Shop> getShopsByCity (@RequestParam int pincode){
        List<Shop> allShops=shopRepo.getShopsByCity(pincode);
        return allShops;
    }

    @GetMapping("/searchByLocation")
    public List<Shop> getShopsByLocation (@RequestParam String city){
        System.out.println("Searching shops with address: " + city);
        List<Shop> allShops=shopRepo.findByCityIgnoreCase(city);


        return allShops;
    }

    @GetMapping("/distinctCities")
    public List<String> getDistinctCities(){
        return shopRepo.findDistinctCities();
    }


}
