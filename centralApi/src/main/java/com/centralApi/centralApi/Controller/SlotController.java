package com.centralApi.centralApi.Controller;

import com.centralApi.centralApi.Exceptions.SlotNotFoundException;
import com.centralApi.centralApi.modelClasses.Slot;
import com.centralApi.centralApi.requestBodyClasses.SlotRB;
import com.centralApi.centralApi.serviceClasses.AuthenticationService;
import com.centralApi.centralApi.serviceClasses.SlotServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/central/slot")
public class SlotController {
    @Autowired
    SlotServiceClass slotService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity createSlot(@RequestBody SlotRB slotRB){
        return new ResponseEntity<>(slotService.createSlot(slotRB), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity getSlotById(@RequestHeader("Authorization") String token,@PathVariable UUID id){
        authenticationService.verifyToken(token);
        Slot slot= slotService.getSlotById(id);
        if(slot==null)throw new SlotNotFoundException("slot not found");
        return new ResponseEntity<>(slot, HttpStatus.OK);
    }

    @PutMapping("/book/{slotId}/user/{userId}")
    public ResponseEntity bookSlot(@RequestHeader("Authorization") String token,@PathVariable UUID slotId,@PathVariable UUID userId){
        authenticationService.verifyToken(token);
        return new ResponseEntity(slotService.bookSlot(slotId,userId),HttpStatus.OK);
    }

    @PutMapping("/cancel/{slotId}/user/{userId}")
    public ResponseEntity cancelSlot(@RequestHeader("Authorization") String token,@PathVariable UUID slotId,@PathVariable UUID userId){
        authenticationService.verifyToken(token);
        return new ResponseEntity(slotService.cancelSlot(slotId,userId),HttpStatus.OK);
    }

    @GetMapping("/getBookedSlotsInfo")
    public List<Slot> getBookedSlotsInfo(@RequestParam UUID shopId, @RequestParam UUID serviceId,@RequestParam String date){

        LocalDate localDate=LocalDate.parse(date);
        List<Slot> bookedSlots=slotService.getBookedSlotsInfo(shopId,serviceId,localDate);
        return bookedSlots;
    }

    @GetMapping("/getOrCreateSlots")
    public List<Slot> getOrCreateSlots(@RequestParam UUID shopId, @RequestParam UUID serviceId, @RequestParam String date){
        LocalDate localDate=LocalDate.parse(date);
        List<Slot> slots=new ArrayList<>();


        slots=slotService.getOrCreateSlot(shopId,serviceId,localDate);
        return slots;
    }
}
