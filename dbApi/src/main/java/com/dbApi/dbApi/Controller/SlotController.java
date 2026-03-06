package com.dbApi.dbApi.Controller;

import com.dbApi.dbApi.modelClasses.Slot;
import com.dbApi.dbApi.repository.SlotRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/db/slot")
public class SlotController {
    @Autowired
    SlotRepo slotRepo;

    @PostMapping("/create")
    public ResponseEntity createSlot(@RequestBody Slot slot){
        Slot saved=slotRepo.save(slot);
        return new ResponseEntity(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSlotById(@PathVariable UUID id){
        slotRepo.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity getSlotById(@PathVariable UUID id){
        Slot slot=slotRepo.findById(id).orElse(null);
        return new ResponseEntity(slot,HttpStatus.OK);
    }

    @GetMapping("/getBookedSlotsInfo")
    public List<Slot> getBookedSlotsInfo(@RequestParam UUID shopId,@RequestParam UUID serviceId , @RequestParam LocalDate date){
        List<Slot> bookedSlots=slotRepo.getBookedSlotsInfo(shopId,serviceId,date);
        return bookedSlots;
    }

    @GetMapping("/day")
    public List<Slot> getSlotsByDay(@RequestParam UUID shopId, @RequestParam UUID serviceId, @RequestParam LocalDate date){
        List<Slot> slots=slotRepo.findByShopIdAndServiceIdAndDate(shopId,serviceId,date);
        return slots;
    }

    @PutMapping("/book/{slotId}/user/{userId}")
    public ResponseEntity bookSlot(@PathVariable UUID slotId,@PathVariable UUID userId){
        Slot slot=slotRepo.findById(slotId).orElse(null);

        if(slot==null)return new ResponseEntity(HttpStatus.NOT_FOUND);

        if(slot.isBooked()){
            return new ResponseEntity<>("Already Booked",HttpStatus.BAD_REQUEST);
        }

        slot.setBooked(true);
        slot.setBookedByUser(userId);

        Slot saved=slotRepo.save(slot);
        return new ResponseEntity(saved,HttpStatus.OK);
    }

    @PutMapping("/cancel/{slotId}/user/{userId}")
    public ResponseEntity cancelSlot(@PathVariable UUID slotId,@PathVariable UUID userId){
        Slot slot=slotRepo.findById(slotId).orElse(null);



        if(slot==null)return new ResponseEntity(HttpStatus.NOT_FOUND);

        if(slot.isBooked() && !slot.getBookedByUser().equals(userId)){
            return new ResponseEntity<>("Already Booked",HttpStatus.BAD_REQUEST);
        }

        slot.setBooked(false);
        slot.setBookedByUser(null);



        Slot saved=slotRepo.save(slot);
        return new ResponseEntity(saved,HttpStatus.OK);
    }
}
