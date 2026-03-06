package com.centralApi.centralApi.UtilClasses;

import com.centralApi.centralApi.modelClasses.AppUser;
import com.centralApi.centralApi.modelClasses.Shop;
import com.centralApi.centralApi.modelClasses.ShopService;
import com.centralApi.centralApi.requestBodyClasses.CreateUserRB;
import com.centralApi.centralApi.requestBodyClasses.ShopRB;
import com.centralApi.centralApi.requestBodyClasses.ShopServiceRB;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public AppUser mapCreateUserRBToAppUser(CreateUserRB createUserRB){
        AppUser appUser=new AppUser();

        appUser.setUserId(createUserRB.getUserId());
        appUser.setAddress(createUserRB.getAddress());
        appUser.setUserType(createUserRB.getUserType());
        appUser.setEmail(createUserRB.getEmail());
        appUser.setName(createUserRB.getName());
        appUser.setPassword(createUserRB.getPassword());
        appUser.setPincode(createUserRB.getPincode());
        appUser.setPhoneNo(createUserRB.getPhoneNo());
        appUser.setState(createUserRB.getState());

        return appUser;
    }

    public Shop mapShopRBToShop(ShopRB shopRB){
        Shop shop=new Shop();

        shop.setAddress(shopRB.getAddress());
        shop.setCity(shopRB.getCity());
        shop.setShopServiceSet(shopRB.getShopServiceSet());
        shop.setId(shopRB.getId());
        shop.setName(shopRB.getName());
        shop.setPincode(shopRB.getPincode());
        shop.setState(shopRB.getState());
        shop.setStatus(shopRB.getStatus());
        shop.setOwner(shopRB.getOwner());

        return shop;
    }

    public ShopService mapShopServiceRBToShopService(ShopServiceRB shopServiceRB){
        ShopService shopService=new ShopService();

        shopService.setServiceName(shopServiceRB.getServiceName());
        shopService.setDuration(shopServiceRB.getDuration());
        shopService.setId(shopServiceRB.getId());
        shopService.setPrice(shopServiceRB.getPrice());
        shopService.setRatings(shopServiceRB.getRatings());

        return shopService;
    }
}
