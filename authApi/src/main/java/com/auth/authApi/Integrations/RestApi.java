package com.auth.authApi.Integrations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;

public abstract class RestApi {
    @Autowired
    RestTemplate restTemplate;

    public String addQueryParams(String url, HashMap<String,String> queryParams){
        if(queryParams.isEmpty()){
            return url;
        }

        url+="?";
        int count=1;
        for(String key:queryParams.keySet()){
            url+=(key+"="+queryParams.get(key));
            if(count< queryParams.size()){
                url+="&";
            }
            count++;
        }

        return url;
    }

    public ResponseEntity<Object> makePostCall(String baseUrl, String endpoint, Object requestBody, HashMap<String, String> queryParams){
        String url=baseUrl+endpoint;
        url=this.addQueryParams(url,queryParams);
        URI finalUrl=URI.create(url);
        RequestEntity requestEntity=RequestEntity.post(finalUrl).body(requestBody);
        ResponseEntity<Object> response=restTemplate.exchange(finalUrl, HttpMethod.POST,requestEntity,Object.class);
        return response;
    }

    public ResponseEntity<Object> makeGetCall(String baseUrl, String endpoint, HashMap<String, String> queryParams){
        String url=baseUrl+endpoint;
        url=this.addQueryParams(url,queryParams);
        URI finalUrl=URI.create(url);
        RequestEntity requestEntity=RequestEntity.get(finalUrl).build();
        ResponseEntity<Object> response=restTemplate.exchange(finalUrl, HttpMethod.GET,requestEntity,Object.class);
        return response;
    }

    public ResponseEntity<Object> makeGetCall(String baseUrl, String endpoint, String token, HashMap<String, String> queryParams){
        String url=baseUrl+endpoint;
        url=this.addQueryParams(url,queryParams);
        URI finalUrl=URI.create(url);
        HttpHeaders headers=new HttpHeaders();
        headers.set("Authorization",token);
        RequestEntity requestEntity=RequestEntity.get(finalUrl).headers(headers).build();
        ResponseEntity<Object> response=restTemplate.exchange(finalUrl, HttpMethod.GET,requestEntity,Object.class);
        return response;
    }

    public ResponseEntity<Object> makeDeleteCall(String baseUrl, String endpoint, HashMap<String, String> queryParams){
        String url=baseUrl+endpoint;
        url=this.addQueryParams(url,queryParams);
        URI finalUrl=URI.create(url);
        RequestEntity requestEntity=RequestEntity.delete(finalUrl).build();
        ResponseEntity<Object> response=restTemplate.exchange(finalUrl, HttpMethod.DELETE,requestEntity,Object.class);
        return response;
    }

    public ResponseEntity<Object> makePutCall(String baseUrl, String endpoint, Object requestBody, HashMap<String, String> queryParams){
        String url=baseUrl+endpoint;
        url=this.addQueryParams(url,queryParams);
        URI finalUrl=URI.create(url);
        RequestEntity requestEntity=RequestEntity.put(finalUrl).body(requestBody);
        ResponseEntity<Object> response=restTemplate.exchange(finalUrl, HttpMethod.PUT,requestEntity,Object.class);
        return response;
    }

}
