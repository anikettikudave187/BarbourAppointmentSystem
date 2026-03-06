package com.centralApi.centralApi.requestBodyClasses;

import com.centralApi.centralApi.modelClasses.Shop;
import com.centralApi.centralApi.modelClasses.ShopService;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SlotRB {
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    UUID shopId;
    UUID serviceId;
    boolean booked;
    UUID bookedByUser;

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public UUID getBookedByUser() {
        return bookedByUser;
    }

    public void setBookedByUser(UUID bookedByUser) {
        this.bookedByUser = bookedByUser;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public UUID getShopId() {
        return shopId;
    }

    public void setShopId(UUID shopId) {
        this.shopId = shopId;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }
}
