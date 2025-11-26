package com.example.courseprifs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Driver extends BasicUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String licence;
    private LocalDate bDate;
    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Chat> chats;
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodOrder> myOrders;
    private String vehicleInfo;


    public Driver(String login, String password, String name, String surname, String phoneNumber, String address, String licence, LocalDate bDate, VehicleType vehicleType, String vehicleInfo) {
        super(login, password, name, surname, phoneNumber, address);
        this.licence = licence;
        this.bDate = bDate;
        this.vehicleType = vehicleType;
        this.vehicleInfo = vehicleInfo;

    }

    public Driver(String address, String licence, LocalDate bDate, VehicleType vehicleType, String vehicleInfo){
        super(address);
        this.licence = licence;
        this.bDate = bDate;
        this.vehicleType = vehicleType;
        this.vehicleInfo = vehicleInfo;

    }

}
