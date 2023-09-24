package com.project.ebankingbackend.mappers;

import com.project.ebankingbackend.dtos.BankAccountDto;
import com.project.ebankingbackend.entities.BankAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BanAccountMapper {

    public BankAccountDto fromBankAccount(BankAccount bankAccount){
        BankAccountDto bankAccountDto = new BankAccountDto();
        BeanUtils.copyProperties(bankAccount,bankAccountDto);
        return bankAccountDto;
    }

    public BankAccount fromBankAccountDto(BankAccountDto bankAccountDto){
        BankAccount bankAccount = new BankAccount();
        BeanUtils.copyProperties(bankAccountDto,bankAccount);
        return bankAccount;
    }
}
