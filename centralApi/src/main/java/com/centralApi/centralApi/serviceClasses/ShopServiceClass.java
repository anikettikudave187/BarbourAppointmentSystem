package com.centralApi.centralApi.serviceClasses;

import com.centralApi.centralApi.Exceptions.ShopServiceNotFoundException;
import com.centralApi.centralApi.integrations.DbApi;
import com.centralApi.centralApi.modelClasses.Shop;
import com.centralApi.centralApi.modelClasses.ShopService;
import com.centralApi.centralApi.requestBodyClasses.ShopRB;
import com.centralApi.centralApi.requestBodyClasses.ShopServiceRB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class ShopServiceClass {
    @Autowired
    DbApi dbApi;

    @Autowired
    ShopServ shopServ;

    public ShopService createShopService(ShopServiceRB shopServiceRB){
        return dbApi.callCreateShopServiceEndpoint(shopServiceRB);
    }

    public ShopService getShopServiceById(UUID id){
        return dbApi.callGetShopServiceByIdEndpoint(id);
    }

    public ShopService updateShopService(ShopServiceRB shopServiceRB){
        return dbApi.callUpdateShopServiceEndpoint(shopServiceRB);
    }

    public ResponseEntity<Void> deleteShopServiceById(UUID id){
        ShopService shopService=getShopServiceById(id);

        if(shopService==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return dbApi.callDeleteShopServiceByIdEndpoint(id);
    }

    public ShopService addService(UUID shopId, ShopServiceRB shopServiceRB){
        Shop shop=shopServ.getShopById(shopId);
        ShopService saved=createShopService(shopServiceRB);

        shop.getShopServiceSet().add(saved.getId());
        ShopRB shopRB=new ShopRB();
        shopRB.setShopServiceSet(shop.getShopServiceSet());
        shopRB.setAddress(shop.getAddress());
        shopRB.setCity(shop.getCity());
        shopRB.setId(shop.getId());
        shopRB.setName(shop.getName());
        shopRB.setOwner(shop.getOwner());
        shopRB.setState(shop.getState());
        shopRB.setStatus(shop.getStatus());
        shopRB.setPincode(shop.getPincode());

        shopServ.updateShop(shopRB);

        return saved;
    }

    public void removeService(UUID shopId, UUID serviceId){
        Shop shop=shopServ.getShopById(shopId);
        if(!shop.getShopServiceSet().contains(serviceId)){
            throw new ShopServiceNotFoundException("service not exists");
        }

        shop.getShopServiceSet().remove(serviceId);

        ShopRB shopRB=new ShopRB();

        shopRB.setName(shop.getName());
        shopRB.setStatus(shop.getStatus());
        shopRB.setId(shop.getId());
        shopRB.setState(shop.getState());
        shopRB.setPincode(shop.getPincode());
        shopRB.setOwner(shop.getOwner());
        shopRB.setAddress(shop.getAddress());
        shopRB.setCity(shop.getCity());
        shopRB.setShopServiceSet(shop.getShopServiceSet());

        shopServ.updateShop(shopRB);
    }
}
