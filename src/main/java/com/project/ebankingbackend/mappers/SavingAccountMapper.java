package com.project.ebankingbackend.mappers;

import com.project.ebankingbackend.dtos.CurrentAccountDto;
import com.project.ebankingbackend.dtos.SavingAccountDto;
import com.project.ebankingbackend.entities.CurrentAccount;
import com.project.ebankingbackend.entities.SavingAccount;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SavingAccountMapper {

    private CustomerMapper customerMapper;
public SavingAccountDto fromSavingAccount(SavingAccount savingAccount){
    SavingAccountDto savingAccountDto = new SavingAccountDto();
    BeanUtils.copyProperties(savingAccount,savingAccountDto);
    savingAccountDto.setCustomerDto(customerMapper.fromCustomer(savingAccount.getCustomer()));
    savingAccountDto.setTypeAccount(savingAccount.getClass().getName());
    return savingAccountDto;
}
public SavingAccount fromSavingAccountDto(SavingAccountDto savingAccountDto){
    SavingAccount savingAccount = new SavingAccount();
    BeanUtils.copyProperties(savingAccountDto,savingAccount);
    savingAccount.setCustomer(customerMapper.fromCustomerDto(savingAccountDto.getCustomerDto()));
    return  savingAccount;
}


}
