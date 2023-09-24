package com.project.ebankingbackend.dtos;

import lombok.Data;

@Data
public class NewCustomerDto {

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
    private String password;
    private String confirmPassword;
}
