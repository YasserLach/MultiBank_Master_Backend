package com.project.ebankingbackend.services;

import com.project.ebankingbackend.dtos.AccountHistoryDto;
import com.project.ebankingbackend.dtos.BankAccountDto;
import com.project.ebankingbackend.dtos.CurrentAccountDto;
import com.project.ebankingbackend.dtos.SavingAccountDto;
import com.project.ebankingbackend.entities.BankAccount;
import com.project.ebankingbackend.entities.CurrentAccount;
import com.project.ebankingbackend.entities.SavingAccount;

import java.util.List;

public interface BankAccountService {

     void debiter(String bankAccountId,double solde);
     void crediter(String bankAccountId,double solde);

     void transferer(String bankAccountSourceId,String bankAccountDestinationId,double solde);


    AccountHistoryDto getListAccountsByPage(String keyword, int page, int size);

    AccountHistoryDto getAccountsByUserName( String userName,String keyword, int page, int size);

    BankAccountDto getAccountById(String accountId);

    List<BankAccountDto> getAccountsByName(String userName);

    CurrentAccountDto addNewCurrentAccount(double balance, double overDraft, Long customerId);

    SavingAccountDto addNewSavingAccount(double balance, double rate, Long customerId);

    void deleteBankAccount(String accountId);

    BankAccountDto getbankAccount(String bankAccountId);

    List<BankAccountDto> getListBankAccounts();
}
