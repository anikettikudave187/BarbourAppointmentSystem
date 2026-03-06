package com.centralApi.centralApi.serviceClasses;

import com.centralApi.centralApi.integrations.DbApi;
import com.centralApi.centralApi.modelClasses.Slot;
import com.centralApi.centralApi.requestBodyClasses.SlotRB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SlotServiceClass {
    @Autowired
    DbApi dbApi;

    public Slot createSlot(SlotRB slotRB){

        return dbApi.callCreateSlotEndpoint(slotRB);
    }
    public Slot getSlotById(UUID id){
        return dbApi.callGetSlotByIdEndpoint(id);
    }

    public List<Slot> getBookedSlotsInfo(UUID shopId,UUID serviceId,LocalDate date){
        return dbApi.callGetBookedSlotsInfo(shopId,serviceId,date);
    }

    public Slot bookSlot(UUID slotId, UUID userId){
        Slot slot=dbApi.callGetSlotByIdEndpoint(slotId);

        if(slot.isBooked()){
            throw new RuntimeException("Slot is already Booked");
        }

        /*slot.setBooked(true);
        slot.setBookedByUser(userId);*/
        dbApi.callBookSlot(slotId,userId);

        return dbApi.callGetSlotByIdEndpoint(slotId);
    }

    public Slot cancelSlot(UUID slotId, UUID userId){
        Slot slot=dbApi.callGetSlotByIdEndpoint(slotId);



        if(!slot.getBookedByUser().equals(userId)){
            throw new RuntimeException("UnAuthorized User");
        }

        /*slot.setBooked(false);
        slot.setBookedByUser(null);*/
        dbApi.callCancelSlot(slotId,userId);

        return dbApi.callGetSlotByIdEndpoint(slotId);
    }

    public List<Slot> getOrCreateSlot(UUID shopId, UUID serviceId, LocalDate date){
        List<Slot> existingSlots= dbApi.getSlotsByDay(shopId,serviceId,date);

        if(!existingSlots.isEmpty()){
            return existingSlots;
        }

        List<Slot> newSlots=generateDailySlots(shopId,serviceId,date);

        for(Slot slot:newSlots){
            SlotRB slotRB=new SlotRB();

            slotRB.setDate(slot.getDate());
            slotRB.setServiceId(slot.getServiceId());
            slotRB.setEndTime(slot.getEndTime());
            slotRB.setShopId(slot.getShopId());
            slotRB.setStartTime(slot.getStartTime());
            slotRB.setBookedByUser(slot.getBookedByUser());
            slotRB.setBooked(slot.isBooked());

            dbApi.callCreateSlotEndpoint(slotRB);
        }
        return dbApi.getSlotsByDay(shopId,serviceId,date);
    }

    public List<Slot> generateDailySlots(UUID shopId, UUID serviceId, LocalDate date){
        List<Slot> dailySlots=new ArrayList<>();

        LocalTime start=LocalTime.of(10,0);
        LocalTime end=LocalTime.of(22,0);

        while(start.isBefore(end)){
            if(start.equals(LocalTime.of(13,0))){
                start=start.plusHours(1);
                continue;
            }

            Slot slot=new Slot();

            //slot.setSlotId(UUID.randomUUID());
            slot.setDate(date);
            slot.setServiceId(serviceId);
            slot.setShopId(shopId);
            slot.setBooked(false);
            slot.setStartTime(start);
            slot.setEndTime(start.plusHours(1));

            dailySlots.add(slot);
            start=start.plusHours(1);

        }

        return dailySlots;
    }
}
