package com.example.courseprifs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Restaurant extends User{
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<Cuisine> menuItems;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected List<FoodOrder> orders;
    protected String workHours;
    protected double rating;
    protected String restaurantName;
    protected String restaurantInfo;
    protected String address;
    protected String email;

    public Restaurant(String restaurantName, String restaurantInfo, String email, String phoneNumber, String address) {
        super(phoneNumber);
        this.restaurantName = restaurantName;
        this.restaurantInfo = restaurantInfo;
        this.email = email;
        this.address = address;
    }


    public Restaurant(String login, String password, String surname, String name, String restaurantName, String restaurantInfo, String email, String phoneNumber, String address) {
        super(login,password,name,surname,phoneNumber);
        this.restaurantName = restaurantName;
        this.restaurantInfo = restaurantInfo;
        this.email = email;
        this.address = address;
    }
}
