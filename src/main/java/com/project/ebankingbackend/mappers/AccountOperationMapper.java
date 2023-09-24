package com.project.ebankingbackend.mappers;

import com.project.ebankingbackend.dtos.AccountHistoryDto;
import com.project.ebankingbackend.dtos.AccountOperationDto;
import com.project.ebankingbackend.entities.AccountOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountOperationMapper {

    private BanAccountMapper banAccountMapper;
    private CustomerMapper customerMapper;
    public AccountOperationDto fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDto accountOperationDto = new AccountOperationDto();
        BeanUtils.copyProperties(accountOperation,accountOperationDto);
        //  accountOperationDto.setBankAccountDto(banAccountMapper.fromBankAccount(accountOperation.getBankAccount()));
        return accountOperationDto;
    }

    public AccountOperation fromAccountOperationDto(AccountOperationDto accountOperationDto){
        AccountOperation accountOperation = new AccountOperation();
        BeanUtils.copyProperties(accountOperationDto,accountOperation);
       // accountOperation.setBankAccount(banAccountMapper.fromBankAccountDto(accountOperationDto.getBankAccountDto()));
        return accountOperation;
    }





}
