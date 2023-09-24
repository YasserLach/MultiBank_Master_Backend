package com.project.ebankingbackend.dtos;

import com.project.ebankingbackend.entities.Customer;
import com.project.ebankingbackend.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class CurrentAccountDto extends BankAccountDto {

    private double overDraft;
}
