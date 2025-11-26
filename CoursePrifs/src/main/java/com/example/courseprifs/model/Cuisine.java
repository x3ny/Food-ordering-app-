package com.example.courseprifs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cuisine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private boolean spicy = false;
    private boolean vegan = false;
    private String ingredients;
    @ManyToMany(mappedBy = "cuisineList", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<FoodOrder> orderList;
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public Cuisine(String name, double price, boolean spicy, boolean vegan, String ingredients, Restaurant restaurant) {
        this.name = name;
        this.price = price;
        this.spicy = spicy;
        this.vegan = vegan;
        this.ingredients = ingredients;
        this.restaurant = restaurant;
    }
}
