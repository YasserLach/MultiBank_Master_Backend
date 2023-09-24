package com.project.ebankingbackend.dtos;


import com.project.ebankingbackend.enums.AccountStatus;
import lombok.Data;

import java.util.Date;


@Data
public class BankAccountDto {
    private String id;
    private Date createdAt;
    private double balance;
    private String currency;
    private AccountStatus status;
    private CustomerDto customerDto;
    private String typeAccount;
}
