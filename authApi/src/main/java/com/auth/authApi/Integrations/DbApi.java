package com.auth.authApi.Integrations;

import com.auth.authApi.model.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Slf4j
public class DbApi extends RestApi{

    @Value("${db.api.base}")
    String dbApiBaseUrl;

    @Autowired
    ModelMapper modelMapper;

    public AppUser findUserByEmail(String email){
        String endpoint="/user/email/"+email;
        ResponseEntity<Object> response=this.makeGetCall(dbApiBaseUrl,endpoint,new HashMap<>());

        if(response==null || response.getBody()==null){
            return null;
        }

        AppUser appUser=modelMapper.map(response.getBody(),AppUser.class);
        return appUser;
    }
}
