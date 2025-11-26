package com.example.courseprifs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class FoodOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @ManyToOne()
    private BasicUser customer;
    @ManyToOne
    private Driver driver;
    @OneToOne(mappedBy = "FoodOrder", cascade = CascadeType.ALL)
    private Chat chat;
    @ManyToOne
    private Restaurant restaurant;
    private String title;
    @ManyToMany
    @JoinTable(
            name = "order_cuisine",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "cuisine_id")
    )
    protected List<Cuisine> cuisineList;
    protected double price;
    @Transient
    protected List<Chat> myChat;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;

    public FoodOrder(String title, BasicUser customer, double price, Restaurant restaurant) {
        this.title = title;
        this.customer = customer;
        this.price = price;
        this.restaurant = restaurant;
    }

    public FoodOrder(BasicUser customer, String title, double price, List<Cuisine> cuisineList, Restaurant restaurant) {
        this.customer = customer;
        this.title = title;
        this.price = price;
        this.cuisineList = cuisineList;
        this.restaurant = restaurant;
        this.orderStatus = OrderStatus.PENDING;
    }

    @Override
    public String toString() {
        return title + " " + price;
    }
}
