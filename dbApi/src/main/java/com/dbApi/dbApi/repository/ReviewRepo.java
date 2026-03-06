package com.dbApi.dbApi.repository;

import com.dbApi.dbApi.modelClasses.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewRepo extends JpaRepository<Reviews, UUID> {
}
