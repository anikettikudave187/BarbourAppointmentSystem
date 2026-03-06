package com.auth.authApi.Service;

import com.auth.authApi.Integrations.DbApi;
import com.auth.authApi.model.AppUser;
import com.auth.authApi.security.JwtFilter;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    @Autowired
    DbApi dbApi;

    @Value("${auth.secret.key}")
   String secretKey;

    long expirationTime=1000000L;

    public String generateToken(String email, String password){
        String credentials=email+":"+password;

        String token= Jwts.builder().setSubject(credentials)
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();

        return token;
    }

    public String decryptToken(String token){
        String credentials=Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        return credentials;
    }

    public boolean verifyToken(String token){
        String credentials=decryptToken(token);

        String email=credentials.split(":")[0];
        String password=credentials.split(":")[1];

        AppUser appUser=dbApi.findUserByEmail(email);
        if(password.equals(appUser.getPassword())){
            return true;
        }

        return false;
    }

    public AppUser verifyAndGetUser(String authHeader){
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            throw new RuntimeException("missing token");
        }

        String token = authHeader.substring(7);
        String credentials=decryptToken(token);

        String email=credentials.split(":")[0];
        String password=credentials.split(":")[1];

        AppUser user= dbApi.findUserByEmail(email);

        if(user==null){
            throw new RuntimeException("User Not Valid");
        }

        if(!user.getPassword().equals(password)){
            throw new RuntimeException("invalid token");
        }

        return user;
    }

}
