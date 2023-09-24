package com.project.ebankingbackend.services.impl;

import com.project.ebankingbackend.dtos.AccountOperationDto;
import com.project.ebankingbackend.dtos.OperationHistoryDto;
import com.project.ebankingbackend.entities.AccountOperation;
import com.project.ebankingbackend.entities.BankAccount;
import com.project.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.project.ebankingbackend.mappers.AccountOperationMapper;
import com.project.ebankingbackend.mappers.BanAccountMapper;
import com.project.ebankingbackend.repositories.BankAccRepository;
import com.project.ebankingbackend.repositories.OperationAccRepository;
import com.project.ebankingbackend.services.AccountOperationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor

public class AccountOperationServiceImpl implements AccountOperationService {
    private OperationAccRepository operationAccRepository;
    private AccountOperationMapper accountOperationMapper;
    private BanAccountMapper banAccountMapper;
    private BankAccRepository bankAccRepository;




    @Override
    public List<AccountOperationDto> findOperationsForAccount() {
        List<AccountOperation> accountOperations = operationAccRepository.findAll();
        return accountOperations.stream().map(accountOperation -> accountOperationMapper.fromAccountOperation(accountOperation)).collect(Collectors.toList());
    }

    @Override
    public List<AccountOperationDto> listOperationsForAccount(String accountId){
        List<AccountOperation> accountOperations = operationAccRepository.findByBankAccountId(accountId);
        return accountOperations.stream().map(op->accountOperationMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }
/*
    @Override
    public AccountHistoryDto listOperationsForAccountPerPage(String accountId, int page, int size){
        Optional<BankAccount> bankAccountExist = bankAccRepository.findById(accountId);
        if (bankAccountExist.isEmpty()) throw new BankAccountNotFoundException();
        Page<AccountOperation> accountOperations = operationAccRepository.findByBankAccountId(accountId,PageRequest.of(page, size));
        List<AccountOperationDto> accountOperationDTOS = accountOperations.getContent().stream().map(op -> accountOperationMapper.fromAccountOperation(op)).collect(Collectors.toList());
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
        accountHistoryDto.setAccountOperationDtos(accountOperationDTOS);
        accountHistoryDto.setAccountId(bankAccountExist.get().getId());
        accountHistoryDto.setBalance(bankAccountExist.get().getBalance());
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setPageSize(size);
        accountHistoryDto.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDto;
    }
*/

    @Override
    public OperationHistoryDto listOperationsForAccountPerPage(String accountId, int page, int size) {
        Optional<BankAccount> bankAccountExist = bankAccRepository.findById(accountId);
        if (bankAccountExist.isEmpty()) throw new BankAccountNotFoundException();
        Page<AccountOperation> accountOperations = operationAccRepository.findByBankAccountId(accountId, PageRequest.of(page,size));
        List<AccountOperationDto> accountOperationDTOS = accountOperations.getContent().stream().map(op -> accountOperationMapper.fromAccountOperation(op)).collect(Collectors.toList());
        OperationHistoryDto operationHistoryDto = new OperationHistoryDto();
        operationHistoryDto.setAccountOperationDtos(accountOperationDTOS);
        operationHistoryDto.setBankAccountDto(banAccountMapper.fromBankAccount(bankAccountExist.get()));
        operationHistoryDto.setCurrentPage(page);
        operationHistoryDto.setPageSize(size);
        operationHistoryDto.setTotalPages(accountOperations.getTotalPages());
        return operationHistoryDto;
    }


}
