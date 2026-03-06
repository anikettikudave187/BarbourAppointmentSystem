package com.centralApi.centralApi.integrations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RestApi {
    @Autowired
    RestTemplate restTemplate;

    public String addQueryParams(String url, Map<String,String> queryParams){
        if(queryParams.size()==0){
            return url;
        }

        url+="?";
        int count=1;

        for(String key:queryParams.keySet()){
            url+=(key+"="+queryParams.get(key));

            if(count<queryParams.size()){
                url+="&";
            }
            count++;
        }

        return url;
    }

    public Object makePostCall(String apiBaseUrl, String endpoint, Object requestBody, Map<String,String> queryParams) {
        String url = apiBaseUrl + endpoint;
        url = this.addQueryParams(url, queryParams);

        URI finalUrl = URI.create(url);
        RequestEntity<Object> requestEntity = RequestEntity
                .post(finalUrl)
                .header("Content-Type", "application/json")
                .body(requestBody);

        ResponseEntity<Object> response = restTemplate.exchange(requestEntity, Object.class);

        return response.getBody();

    }


    public Object makeGetCall(String apiBaseUrl,String endpoint,Map<String,String>queryParams){
        String url=apiBaseUrl+endpoint;
        url=this.addQueryParams(url,queryParams);

        RequestEntity<Void> requestEntity=RequestEntity.get(url).build();
        ResponseEntity<Object> response=restTemplate.exchange(requestEntity,Object.class);
        return response.getBody();
    }

    public Object makeGetCall(String baseUrl, String endpoint, String token, HashMap<String, String> queryParams){
        String url=baseUrl+endpoint;
        url=this.addQueryParams(url,queryParams);
        URI finalUrl=URI.create(url);
        HttpHeaders headers=new HttpHeaders();
        headers.set("Authorization","Bearer "+token);
        RequestEntity requestEntity=RequestEntity.get(finalUrl).headers(headers).build();
        ResponseEntity<Object> response=restTemplate.exchange(requestEntity,Object.class);
        return response.getBody();
    }

    public Object makePutCall(String apiBaseUrl,String endpoint,Object requestBody,Map<String,String>queryParams){
        String url=apiBaseUrl+endpoint;
        url=this.addQueryParams(url,queryParams);

        URI finalUrl=URI.create(url);
        RequestEntity<Object> requestEntity=RequestEntity.put(finalUrl).body(requestBody);
        ResponseEntity<Object> response=restTemplate.exchange(finalUrl,HttpMethod.PUT,requestEntity,Object.class);

        return response.getBody();
    }

    public Object makeDeleteCall(String apiBaseUrl,String endpoint,Map<String,String>queryParams){
        String url=apiBaseUrl+endpoint;
        url=this.addQueryParams(url,queryParams);

        RequestEntity<Void> requestEntity=RequestEntity.delete(url).build();
        ResponseEntity<Object> response=restTemplate.exchange(url,HttpMethod.DELETE,requestEntity, Object.class);

        return response.getBody();
    }
}
