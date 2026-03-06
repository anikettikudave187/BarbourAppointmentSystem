/*package com.centralApi.centralApi.Controller;

import com.centralApi.centralApi.modelClasses.Shop;
import com.centralApi.centralApi.serviceClasses.AiServiceClass;
import com.centralApi.centralApi.serviceClasses.ShopServ;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/central/ai")
public class AiControllerClass {
    private final AiServiceClass aiServiceClass;

    public AiControllerClass(AiServiceClass aiServiceClass, ShopServ shopServ) {
        this.aiServiceClass = aiServiceClass;
    }


    @GetMapping("/askAI")
    public String askAI(@RequestParam String question) {
        return aiServiceClass.askAI(question);
    }




}*/
