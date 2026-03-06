package com.centralApi.centralApi.ResponseBody;

import java.util.UUID;

public class TokenResponseBody {
    String token;
    UUID userId;


    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
