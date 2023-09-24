package com.project.ebankingbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.ebankingbackend.enums.CustomerSexe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String phoneNumber;
    private int age;
    @Enumerated(EnumType.STRING)
    private CustomerSexe sexe;
    private String localisation;
    private String instagram;
    private String facebook;
    private String tiktok;
    @Column(unique = true)
    private String userName;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean isActivated;


    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL)
  //  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<BankAccount> bankAccounts;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> appRoles;

}
