package com.centralApi.centralApi.serviceClasses;

import com.centralApi.centralApi.Exceptions.UnAuthorizedException;
import com.centralApi.centralApi.ResponseBody.SuccessResponseBody;
import com.centralApi.centralApi.ResponseBody.TokenResponseBody;
import com.centralApi.centralApi.integrations.AuthApi;
import com.centralApi.centralApi.modelClasses.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    AuthApi authApi;

    @Autowired
    ModelMapper modelMapper;

    public void verifyToken(String token){
        SuccessResponseBody responseBody= authApi.callVerifyToken(token);

        if(responseBody==null || responseBody.getStatus()==null || !responseBody.getStatus().equals("success")){
            throw new UnAuthorizedException("un-Authorized");
        }
    }

    public Object getTokenRB(String email,String password){
        Object token=authApi.callGenerateToken(email,password);
        TokenResponseBody tokenResponseBody=modelMapper.map(token, TokenResponseBody.class);
        return tokenResponseBody;
    }

    public AppUser verifyAndGetUser(String authHeader){
        AppUser user=authApi.callVerifyAndGetUser(authHeader);
        if(user==null)throw new RuntimeException("not found");
        return user;
    }
}
