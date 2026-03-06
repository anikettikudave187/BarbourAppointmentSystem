package com.centralApi.centralApi.serviceClasses;

import com.centralApi.centralApi.Exceptions.UserNotFoundException;
import com.centralApi.centralApi.enumClasses.UserType;
import com.centralApi.centralApi.integrations.DbApi;
import com.centralApi.centralApi.modelClasses.AppUser;
import com.centralApi.centralApi.requestBodyClasses.CreateUserRB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
    DbApi dbApi;

    public AppUser createUser(CreateUserRB userRB){
        return dbApi.callCreateUserEndpoint(userRB);
    }

    public AppUser getUserById(UUID userId){
        AppUser appUser=dbApi.callGetUserByIdEndpoint(userId);
        if(appUser==null)throw new UserNotFoundException("user not found");
        return appUser;
    }
    public AppUser updateUser(CreateUserRB userRB){
        return dbApi.callUpdateUserEndpoint(userRB);
    }
    public AppUser deleteUserById(UUID id){
        return dbApi.callDeleteUserByIdEndpoint(id);
    }

    public boolean isShopOwner(UUID ownerId){
        AppUser shopOwner=getUserById(ownerId);
        if(shopOwner==null){
            throw new UserNotFoundException("This User Not Valid");
        }
        if(shopOwner.getUserType().equalsIgnoreCase(UserType.OWNER.toString())){
            return true;
        }else{
            return false;
        }
    }


}
