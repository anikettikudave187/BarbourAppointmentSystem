package com.dbApi.dbApi.repository;

import com.dbApi.dbApi.modelClasses.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppuserRepo extends JpaRepository<AppUser, UUID> {
    @Query(value = "select * from appUser where userType='ADMIN' ",nativeQuery = true)
    public List<AppUser> getAllAdmins();

    public AppUser findByEmail(String email);
}
