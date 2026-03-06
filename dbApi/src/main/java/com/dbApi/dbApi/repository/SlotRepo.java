package com.dbApi.dbApi.repository;

import com.dbApi.dbApi.modelClasses.Slot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SlotRepo extends JpaRepository<Slot, UUID> {
    @Query(
            value = "select * from slots where shop_id= :shopId and service_id= :serviceId and date= :date and booked=true",
            nativeQuery = true
    )
    public List<Slot> getBookedSlotsInfo(UUID shopId,UUID serviceId, LocalDate date);

    public List<Slot> findByShopIdAndServiceIdAndDate(UUID shopId, UUID serviceId, LocalDate date);
}
