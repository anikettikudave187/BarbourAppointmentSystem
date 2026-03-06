package com.dbApi.dbApi.modelClasses;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(
        name = "slots",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"shop_id", "service_id", "date", "start_time"}
                )
        }
)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID slotId;

    @Column(name = "date",nullable = false)
    private LocalDate date;

    @Column(name = "start_time",nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time",nullable = false)
    private LocalTime endTime;

    private UUID shopId;
    private UUID serviceId;

    private  boolean booked=false;

    @Column(name = "bookedByUser")
    private UUID bookedByUser;


    public UUID getSlotId() {
        return slotId;
    }

    public void setSlotId(UUID slotId) {
        this.slotId = slotId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public UUID getBookedByUser() {
        return bookedByUser;
    }

    public void setBookedByUser(UUID bookedByUserId) {
        this.bookedByUser = bookedByUserId;
    }
}
