package com.centralApi.centralApi.integrations;

import com.centralApi.centralApi.ResponseBody.SuccessResponseBody;
import com.centralApi.centralApi.modelClasses.AppUser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AuthApi extends RestApi{

    @Value("${auth.api.base}")
    String authBaseUrl;

    @Autowired
    ModelMapper modelMapper;

    public SuccessResponseBody callVerifyToken(String token){
        String endpoint="/verify_token";
        Object response=this.makeGetCall(authBaseUrl,endpoint,token,new HashMap<>());

        if(response==null){
            return null;
        }
        SuccessResponseBody successResponseBody=modelMapper.map(response,SuccessResponseBody.class);
        return successResponseBody;
    }

    public Object callGenerateToken(String email,String password){
        String endpoint="/token";
        HashMap<String,String> queryParams=new HashMap<>();

        queryParams.put("email",email);
        queryParams.put("password",password);

        Object response=this.makeGetCall(authBaseUrl,endpoint,queryParams);
        return response;
    }

    public AppUser callVerifyAndGetUser(String authHeader){
        String endpoint="/getUser";
        Object resp=this.makeGetCall(authBaseUrl,endpoint,authHeader,new HashMap<>());

        if(resp==null)return null;

        AppUser user=modelMapper.map(resp, AppUser.class);

        return user;
    }
}
