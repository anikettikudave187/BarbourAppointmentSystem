package com.dbApi.dbApi.repository;

import com.dbApi.dbApi.modelClasses.Shop;
import com.dbApi.dbApi.modelClasses.ShopService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ShopRepo extends JpaRepository<Shop, UUID> {
    @Query(
            value = "SELECT BIN_TO_UUID(service_id) FROM shop_services WHERE shop_id = :shopId",
            nativeQuery = true
    )
    Set<String> getServicesByShopId(@Param("shopId") UUID shopId);

    @Query(value = "select * from shop where owner= :ownerId",nativeQuery = true)
    List<Shop> getShopsByOwner(UUID ownerId);

    Optional<Shop> findByOwnerAndNameAndAddress(UUID owner, String name, String address);

    @Query(value = "select * from shop where pincode= :pincode",nativeQuery = true)
    List<Shop> getShopsByCity(int pincode);


    List<Shop> findByCityIgnoreCase(String city);


    @Query(
            value = "select distinct s.city from shop s where s.city is not null",nativeQuery = true
    )
    List<String> findDistinctCities();


}
