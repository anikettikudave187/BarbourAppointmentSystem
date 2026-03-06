package com.dbApi.dbApi.modelClasses;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "shop")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    String name;
    String address;
    String city;
    int pincode;

    UUID owner;

    String state;
    String status;

    @ElementCollection
    @CollectionTable(
            name = "shop_services",
            joinColumns = @JoinColumn(name = "shop_id")
    )
    @Column(name = "service_id")
    private Set<UUID> shopServiceSet = new HashSet<>();



    public Set<UUID> getShopServiceSet() {
        return shopServiceSet;
    }

    public void setShopServiceSet(Set<UUID> shopServiceSet) {
        this.shopServiceSet = shopServiceSet;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
