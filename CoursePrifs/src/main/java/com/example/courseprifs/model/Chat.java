package com.example.courseprifs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String chatText;
    @ManyToOne
    private BasicUser customer;
    @ManyToOne
    private Driver driver;
    @OneToOne
    private FoodOrder FoodOrder;
    private LocalDateTime createdAt;
    private LocalDateTime lastMessageAt;


}
