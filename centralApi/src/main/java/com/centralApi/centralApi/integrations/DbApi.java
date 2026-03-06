package com.centralApi.centralApi.integrations;

import com.centralApi.centralApi.ResponseBody.ShopResponseBody;
import com.centralApi.centralApi.UtilClasses.Mapper;
import com.centralApi.centralApi.modelClasses.*;
import com.centralApi.centralApi.requestBodyClasses.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.DocFlavor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
public class DbApi extends RestApi{

    private static final Logger log=LoggerFactory.getLogger(DbApi.class);
    @Value("${db.api.base}")
     String baseUrl;

    @Autowired
    Mapper mapper;

    @Autowired
    ModelMapper modelMapper;

    public AppUser callCreateUserEndpoint(CreateUserRB createUserRB){
        AppUser appUser=mapper.mapCreateUserRBToAppUser(createUserRB);
        String endpoint="/user/create";
        log.info("calling create user endpoint of db class");
        Object response=this.makePostCall(baseUrl,endpoint,appUser,new HashMap<>());
        AppUser userResponse=modelMapper.map(response,AppUser.class);
        return userResponse;
    }

    public AppUser callGetUserByIdEndpoint(UUID userId){
        String endpoint="/user/"+userId.toString();

        Object response=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        if(response==null){
            return null;
        }
        return modelMapper.map(response,AppUser.class);
    }

    public AppUser callUpdateUserEndpoint(CreateUserRB createUserRB){
        AppUser appUser=callGetUserByIdEndpoint(createUserRB.getUserId());
        appUser.setAddress(createUserRB.getAddress());
        appUser.setUserType(createUserRB.getUserType());
        appUser.setEmail(createUserRB.getEmail());
        appUser.setPincode(createUserRB.getPincode());
        appUser.setName(createUserRB.getName());
        appUser.setState(createUserRB.getState());
        appUser.setPassword(createUserRB.getPassword());
        appUser.setPhoneNo(createUserRB.getPhoneNo());

        String endpoint="/user/update";
        Object response=this.makePutCall(baseUrl,endpoint,appUser,new HashMap<>());

        return modelMapper.map(response,AppUser.class);
    }

    public AppUser callDeleteUserByIdEndpoint(UUID userId){
        String endpoint="/user/"+userId;
        Object response=this.makeDeleteCall(baseUrl,endpoint,new HashMap<>());

        return null;
    }

    /*--------------------------------------Appuser's crud operations done here--------------------------*/

    public Shop callCreateShopEndpoint(ShopRB shopRB){
        Shop shop= mapper.mapShopRBToShop(shopRB);
        String endpoint="/shop/create";

        Object response=this.makePostCall(baseUrl,endpoint,shop,new HashMap<>());
        return modelMapper.map(response,Shop.class);
    }

    public Shop callGetShopByIdEndpoint(UUID shopId){
        String endpoint="/shop/"+shopId.toString();

        Object response=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        return modelMapper.map(response,Shop.class);
    }

    public Shop callUpdateShopEndpoint(ShopRB shopRB){
        Shop shop= mapper.mapShopRBToShop(shopRB);
        String endpoint="/shop/update";

        Object response=this.makePutCall(baseUrl,endpoint,shop,new HashMap<>());
        return modelMapper.map(response,Shop.class);
    }
    public List<Shop> callGetShopsByOwner(UUID ownerId){
        String endpoint=baseUrl+"/shop/myShops/"+ownerId.toString();

        ResponseEntity<List<Shop>> myShops=restTemplate.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<Shop>>() {});
        return myShops.getBody();
    }

    public List<Shop> callGetShopsByPincode(int pincode){
        String endpoint=baseUrl+"/shop/searchByPincode?pincode="+pincode;
        ResponseEntity<List<Shop>> resp=restTemplate.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<Shop>>(){});
        return resp.getBody();
    }

    public List<Shop> callGetShopsByLocation(String city){
        String endpoint=baseUrl+"/shop/searchByLocation?city="+city;
        ResponseEntity<List<Shop>> resp=restTemplate.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<Shop>>(){});
        return resp.getBody();
    }

    public List<Shop> callGetAllShops(){
        String endpoint="/shop/all";
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        Type listType = new TypeToken<List<Shop>>() {}.getType();
        return modelMapper.map(resp, listType);
    }

    public List<String> getAllCities(){
        String endpoint=baseUrl+"/shop/distinctCities";
        ResponseEntity<List<String>> response=restTemplate.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>(){});
        return response.getBody();
    }

    public ResponseEntity<Void> callDeleteShopByIdEndpoint(UUID shopId){
        String endpoint=baseUrl+"/shop/"+shopId.toString();

        ResponseEntity<Void> response=restTemplate.exchange(endpoint,HttpMethod.DELETE,null,Void.class);
        return response;
    }

    public Set<UUID> callGetServicesByShopIdEndpoint(UUID shopId){
        String endpoint="/shop/getServicesByShop/"+shopId.toString();

        Object response=this.makeGetCall(baseUrl,endpoint,new HashMap<>());

        Set<UUID> services=new HashSet<>();

        if(response instanceof Iterable<?>){
            for(Object item : (Iterable<?>) response){
                services.add(UUID.fromString(item.toString()));
            }
        }
        return services;
    }


    public Shop callAddServiceToShopEndpoint(Shop shop, UUID shopServiceId){
        String endpoint="/shop/"+shop.getId()+"/addService/"+shopServiceId;
        Object response=this.makePutCall(baseUrl,endpoint,shop,new HashMap<>());
        return modelMapper.map(response,Shop.class);
    }
    /*---------------reviews--------------------------------------*/

    public Reviews callAddReview(ReviewsRb reviewsRb){
        String endpoint="/review/addReview";
        Object resp=this.makePostCall(baseUrl,endpoint,reviewsRb,new HashMap<>());
        return modelMapper.map(resp,Reviews.class);

    }

    public List<Reviews> callGetAll(){
        String endpoint=baseUrl+"/review/getAll";
        ResponseEntity<List<Reviews>>allReviews=restTemplate.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        return allReviews.getBody();

    }

    /*--------------------shop crud operation completed----------------------------------------*/

    public ShopService callCreateShopServiceEndpoint(ShopServiceRB shopServiceRB){
        ShopService shopService=mapper.mapShopServiceRBToShopService(shopServiceRB);
        String endpoint="/shopService/create";
        Object resp=this.makePostCall(baseUrl,endpoint,shopService,new HashMap<>());
        return modelMapper.map(resp,ShopService.class);
    }

    public ShopService callGetShopServiceByIdEndpoint(UUID id){
        String endpoint="/shopService/"+id.toString();
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        return modelMapper.map(resp,ShopService.class);
    }

    public ShopService callUpdateShopServiceEndpoint(ShopServiceRB shopServiceRB){
        ShopService shopService=mapper.mapShopServiceRBToShopService(shopServiceRB);
        String endpoint="/shopService/update";
        Object resp=this.makePutCall(baseUrl,endpoint,shopService,new HashMap<>());
        return modelMapper.map(resp,ShopService.class);
    }

    public ResponseEntity<Void> callDeleteShopServiceByIdEndpoint(UUID id){
        String endpoint=baseUrl+"/shopService/"+id.toString();

        ResponseEntity<Void> resp=this.restTemplate.exchange(endpoint,HttpMethod.DELETE,null, Void.class);
        return resp;
    }

    /*----------------------------------Shopservice CRUD complete---------------------*/

    public Slot callCreateSlotEndpoint(SlotRB slotRB){
        String endpoint="/slot/create";
        Object resp=this.makePostCall(baseUrl,endpoint,slotRB,new HashMap<>());
        return modelMapper.map(resp, Slot.class);
    }



    public Slot callGetSlotByIdEndpoint(UUID id){
        String endpoint="/slot/"+id.toString();
        Object resp=this.makeGetCall(baseUrl,endpoint,new HashMap<>());
        return modelMapper.map(resp, Slot.class);
    }

    public List<Slot> callGetBookedSlotsInfo(UUID shopId,UUID serviceId,LocalDate date){
        String endpoint=baseUrl+"/slot/getBookedSlotsInfo?shopId="+shopId+"&serviceId="+serviceId+"&date="+date;
        ResponseEntity<List<Slot>> resp=restTemplate.exchange(endpoint, HttpMethod.GET, null, new ParameterizedTypeReference<List<Slot>>() {});



        return resp.getBody();
    }

    public List<Slot> getSlotsByDay(UUID shopId, UUID serviceId, LocalDate date){
        String endpoint=baseUrl+"/slot/day?shopId=" + shopId +
                "&serviceId=" + serviceId +
                "&date=" + date;

        ResponseEntity<List<Slot>> resp=restTemplate.exchange(endpoint, HttpMethod.GET,null,new ParameterizedTypeReference<List<Slot>>(){});

        return resp.getBody();
    }

    public Slot callBookSlot(UUID slotId, UUID userId){
        String endpoint="/slot/book/"+slotId.toString()+"/user/"+userId.toString();
        Object resp=this.makePutCall(baseUrl,endpoint,null,new HashMap<>());

        return modelMapper.map(resp,Slot.class);
    }
    public Slot callCancelSlot(UUID slotId, UUID userId){
        String endpoint="/slot/cancel/"+slotId.toString()+"/user/"+userId.toString();
        Object resp=this.makePutCall(baseUrl,endpoint,null,new HashMap<>());

        return modelMapper.map(resp,Slot.class);
    }

    /*---------------------------------------slot done-----------------------------------------------*/


}
