package com.centralApi.centralApi.serviceClasses;

import com.centralApi.centralApi.Exceptions.ShopNotFoundException;
import com.centralApi.centralApi.UtilClasses.Mapper;
import com.centralApi.centralApi.enumClasses.ShopStatus;
import com.centralApi.centralApi.integrations.DbApi;
import com.centralApi.centralApi.modelClasses.AppUser;
import com.centralApi.centralApi.modelClasses.Shop;
import com.centralApi.centralApi.modelClasses.ShopService;
import com.centralApi.centralApi.requestBodyClasses.ShopRB;
import com.centralApi.centralApi.requestBodyClasses.ShopServiceRB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ShopServ {
    @Autowired
    DbApi dbApi;

    @Autowired
    Mapper mapper;

    @Autowired
    UserService userService;

    public Shop createShop(ShopRB shopRB){
        Shop shop=mapper.mapShopRBToShop(shopRB);
        shop.setStatus(ShopStatus.REQUEST_RAISED.name());

        return dbApi.callCreateShopEndpoint(shopRB);
    }

    public List<Shop> getShopsByOwner(UUID ownerId){
        return dbApi.callGetShopsByOwner(ownerId);
    }

    public List<Shop> getShopsByPincode(int pincode){
        return dbApi.callGetShopsByPincode(pincode);

    }

    public List<String> getAllCities(){
        return dbApi.getAllCities();
    }

    public List<Shop> getShopsByLocation(String city){
        return dbApi.callGetShopsByLocation(city);

    }

    public Shop getShopById(UUID shopId){
        return dbApi.callGetShopByIdEndpoint(shopId);
    }

    public Shop updateShop(ShopRB shopRB){

        return dbApi.callUpdateShopEndpoint(shopRB);
    }

    public ResponseEntity<Void> deleteShopById(UUID shopId){
        if(this.getShopById(shopId)==null){
            throw new ShopNotFoundException("shop not exists");
        }
        return dbApi.callDeleteShopByIdEndpoint(shopId);
    }

    public Set<UUID> getServicesByShop(UUID shopId){
        return dbApi.callGetServicesByShopIdEndpoint(shopId);
    }

    public List<Shop> getAllShops(){
        return dbApi.callGetAllShops();
    }

    public Shop addServiceToShop(UUID shopServiceId,UUID shopId){
        Shop shop=getShopById(shopId);
        Set<UUID> services=shop.getShopServiceSet();
        services.add(shopServiceId);
        shop.setShopServiceSet(services);
        return dbApi.callAddServiceToShopEndpoint(shop,shopServiceId);
    }
}
