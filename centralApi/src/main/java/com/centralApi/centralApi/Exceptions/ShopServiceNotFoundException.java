package com.centralApi.centralApi.Exceptions;

public class ShopServiceNotFoundException extends RuntimeException{
    public ShopServiceNotFoundException(String message){
        super(message);
    }
}
