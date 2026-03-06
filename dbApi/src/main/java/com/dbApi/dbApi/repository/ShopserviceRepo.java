package com.dbApi.dbApi.repository;

import com.dbApi.dbApi.modelClasses.ShopService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShopserviceRepo extends JpaRepository<ShopService, UUID> {
}
