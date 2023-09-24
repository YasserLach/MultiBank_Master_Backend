package com.project.ebankingbackend.dtos;

import com.project.ebankingbackend.entities.BankAccount;
import com.project.ebankingbackend.enums.AccountStatus;
import com.project.ebankingbackend.enums.OperationType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class AccountOperationDto {
    private Long id;
    private Date dateOperation;
    private double amount;
    private String description;
    private OperationType type;
}
