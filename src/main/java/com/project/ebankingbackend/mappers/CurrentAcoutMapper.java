package com.project.ebankingbackend.mappers;

import com.project.ebankingbackend.dtos.CurrentAccountDto;
import com.project.ebankingbackend.entities.CurrentAccount;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CurrentAcoutMapper {

    private CustomerMapper customerMapper;
    public CurrentAccountDto fromCurrentAccount(CurrentAccount currentAccount){
        CurrentAccountDto currentAccountDto = new CurrentAccountDto();
        BeanUtils.copyProperties(currentAccount,currentAccountDto);
        currentAccountDto.setCustomerDto(customerMapper.fromCustomer(currentAccount.getCustomer()));
        currentAccountDto.setTypeAccount(currentAccount.getClass().getName());
        return currentAccountDto;
    }

    public CurrentAccount fromCurrentAccountDto(CurrentAccountDto currentAccountDto){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDto,currentAccount);
        currentAccount.setCustomer(customerMapper.fromCustomerDto(currentAccountDto.getCustomerDto()));
        return currentAccount;
    }

}
