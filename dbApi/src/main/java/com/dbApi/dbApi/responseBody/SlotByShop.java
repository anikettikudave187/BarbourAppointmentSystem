package com.dbApi.dbApi.responseBody;

import com.dbApi.dbApi.modelClasses.Shop;
import com.dbApi.dbApi.modelClasses.Slot;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SlotByShop {
    List<Slot> slotsOfShop;

    public List<Slot> getSlotsOfShop() {
        return slotsOfShop;
    }

    public void setSlotsOfShop(List<Slot> slotsOfShop) {
        this.slotsOfShop = slotsOfShop;
    }
}
