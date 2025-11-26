package com.example.courseprifs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // userio lentele tures id sequence ir bus unikalus siai lentelei
    protected int id;
    @Column(unique = true)
    protected String login;
    protected String password;
    protected String name;
    protected String surname;
    protected String phoneNumber;
    protected LocalDateTime dateCreated;
    protected LocalDateTime dateUpdated;
    protected boolean isAdmin;

    public User(String login, String password, String name, String surname, String phoneNumber, boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String password, String name, String surname, String phoneNumber) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;

    }

    public User(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return  "Name: " + name + " " + "Surname:  " + surname;
    }
}
