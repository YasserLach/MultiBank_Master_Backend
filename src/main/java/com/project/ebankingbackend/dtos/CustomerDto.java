package com.project.ebankingbackend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ebankingbackend.entities.BankAccount;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class CustomerDto {

    private Long id;
    private String name;
    private String lastName;
    private String userName;
    private int age;
    private String sexe;
    private String localisation;
    private String instagram;
    private String facebook;
    private String tiktok;
    private String phoneNumber;
    private String email;
    private boolean isActivated;


}
