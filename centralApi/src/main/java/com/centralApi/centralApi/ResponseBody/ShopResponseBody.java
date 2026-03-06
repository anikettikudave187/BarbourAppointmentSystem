package com.centralApi.centralApi.ResponseBody;

import com.centralApi.centralApi.modelClasses.ShopService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopResponseBody {
    Set<UUID> servicesByShop;

    public Set<UUID> getServicesByShop() {
        return servicesByShop;
    }

    public void setServicesByShop(Set<UUID> servicesByShop) {
        this.servicesByShop = servicesByShop;
    }
}
