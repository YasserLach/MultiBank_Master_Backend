package com.project.ebankingbackend.services.impl;

import com.project.ebankingbackend.dtos.*;
import com.project.ebankingbackend.entities.*;
import com.project.ebankingbackend.enums.AccountStatus;
import com.project.ebankingbackend.enums.OperationType;
import com.project.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.project.ebankingbackend.exceptions.CustomerNotFoundException;
import com.project.ebankingbackend.exceptions.OverBalanceException;
import com.project.ebankingbackend.mappers.BanAccountMapper;
import com.project.ebankingbackend.mappers.CurrentAcoutMapper;
import com.project.ebankingbackend.mappers.CustomerMapper;
import com.project.ebankingbackend.mappers.SavingAccountMapper;
import com.project.ebankingbackend.repositories.BankAccRepository;
import com.project.ebankingbackend.repositories.CustomerRepository;
import com.project.ebankingbackend.repositories.OperationAccRepository;
import com.project.ebankingbackend.services.BankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private CustomerRepository customerRepository;
    private BankAccRepository bankAccRepository;
    private OperationAccRepository operationAccRepository;
    private CurrentAcoutMapper currentAcoutMapper;
    private SavingAccountMapper savingAccountMapper;
    private BanAccountMapper banAccountMapper;
    private CustomerMapper customerMapper;




    @Override
    public AccountHistoryDto getListAccountsByPage(String keyword,int page,int size){
        Page<BankAccount> bankAccounts =  bankAccRepository.findAllAccountsBySearch(keyword, PageRequest.of(page, size));
        List<BankAccountDto> bankAccountDtos = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return currentAcoutMapper.fromCurrentAccount(currentAccount);
            } else {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return savingAccountMapper.fromSavingAccount(savingAccount);
            }
        }).collect(Collectors.toList());
        AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
        accountHistoryDto.setBankAccountDtos(bankAccountDtos);
        accountHistoryDto.setCurrentPage(page);
        accountHistoryDto.setPageSize(size);
        accountHistoryDto.setTotalPages(bankAccounts.getTotalPages());
        return accountHistoryDto;
    }

    @Override
    public AccountHistoryDto getAccountsByUserName( String userName,String keyword, int page, int size) {
        Page<BankAccount> bankAccounts = bankAccRepository.findByUserNameSearch(userName,keyword,PageRequest.of(page, size));
       List<BankAccountDto> bankAccountDtos =  bankAccounts.stream().map(bankAccount -> {
            if(bankAccount instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return currentAcoutMapper.fromCurrentAccount(currentAccount);
            } else {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return savingAccountMapper.fromSavingAccount(savingAccount);
            }
        }).collect(Collectors.toList());
       AccountHistoryDto accountHistoryDto = new AccountHistoryDto();
       accountHistoryDto.setBankAccountDtos(bankAccountDtos);
       accountHistoryDto.setCurrentPage(page);
       accountHistoryDto.setPageSize(size);
       accountHistoryDto.setTotalPages(bankAccounts.getTotalPages());
        return accountHistoryDto;
    }

    @Override
    public BankAccountDto getAccountById(String accountId) {
        Optional<BankAccount> account = bankAccRepository.findById(accountId);
        if(account.isEmpty()) throw new BankAccountNotFoundException();
        if(account.get() instanceof CurrentAccount) {
          CurrentAccount currentAccount = (CurrentAccount) account.get();
          return currentAcoutMapper.fromCurrentAccount(currentAccount);
        } else {
            SavingAccount savingAccount = (SavingAccount) account.get();
            return savingAccountMapper.fromSavingAccount(savingAccount);
        }
    }


    @Override
    public List<BankAccountDto> getAccountsByName(String userName) {
        List<BankAccount> bankAccounts= bankAccRepository.findByCustomerName(userName);
        return bankAccounts.stream().map(bankAccount -> banAccountMapper.fromBankAccount(bankAccount)).collect(Collectors.toList());
    }




    @Override
    public CurrentAccountDto addNewCurrentAccount(double balance, double overDraft, Long customerId){
        Optional<Customer> customerExist = customerRepository.findById(customerId);
        if(customerExist.isEmpty()) throw new  CustomerNotFoundException();
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCurrency("DH");
        currentAccount.setBalance(balance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customerExist.get());
        CurrentAccount bankAccount = bankAccRepository.save(currentAccount);
        return currentAcoutMapper.fromCurrentAccount(bankAccount);
    }
    @Override
    public SavingAccountDto addNewSavingAccount(double balance, double rate, Long customerId){
        Optional<Customer> customerExist = customerRepository.findById(customerId);
        if(customerExist.isEmpty()) throw new  CustomerNotFoundException();
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCurrency("DH");
        savingAccount.setBalance(balance);
        savingAccount.setInterestRate(rate);
        savingAccount.setCustomer(customerExist.get());
        SavingAccount account = bankAccRepository.save(savingAccount);
        return savingAccountMapper.fromSavingAccount(account);
    }

    @Override
    public void deleteBankAccount(String accountId) {
        Optional<BankAccount> bankAccount = bankAccRepository.findById(accountId);
        if(bankAccount.isEmpty()) throw new BankAccountNotFoundException();
        bankAccRepository.deleteById(bankAccount.get().getId());
    }


    @Override
    public BankAccountDto getbankAccount(String bankAccountId){
        Optional<BankAccount> bankAccountExist = bankAccRepository.findById(bankAccountId);
        if (bankAccountExist.isEmpty())  throw new BankAccountNotFoundException();

        if(bankAccountExist.get() instanceof CurrentAccount){
            CurrentAccount currentAccount = (CurrentAccount) bankAccountExist.get();
            return  currentAcoutMapper.fromCurrentAccount(currentAccount);
        } else {
            SavingAccount savingAccount = (SavingAccount) bankAccountExist.get();
            return savingAccountMapper.fromSavingAccount(savingAccount);
        }
    }

    @Override
    public List<BankAccountDto> getListBankAccounts(){
        List<BankAccount> bankAccounts = bankAccRepository.findAll();
        List<BankAccountDto> bankAccountDtos = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return currentAcoutMapper.fromCurrentAccount(currentAccount);
            } else {
                SavingAccount savingAccount = (SavingAccount) bankAccount;
                return savingAccountMapper.fromSavingAccount(savingAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDtos;
    }



    @Override
    public void  debiter(String bankAccountId, double solde) {
        Optional<BankAccount> bankAccount = bankAccRepository.findById(bankAccountId);
        if(bankAccount.isEmpty()){
            throw new BankAccountNotFoundException();
        }
        if(bankAccount.get().getBalance()<solde){
            throw new OverBalanceException();
        }
        AccountOperation accountOperation1 = new AccountOperation();
        accountOperation1.setType(OperationType.DEBIT);
        accountOperation1.setDateOperation(new Date());
        accountOperation1.setAmount(solde);
        accountOperation1.setDescription("add description later...");
        accountOperation1.setBankAccount(bankAccount.get());
        operationAccRepository.save(accountOperation1);
        bankAccount.get().setBalance(bankAccount.get().getBalance()-solde);
        bankAccRepository.save(bankAccount.get());
    }

    @Override
    public void crediter(String bankAccountId, double solde) {
        Optional<BankAccount> bankAccount = bankAccRepository.findById(bankAccountId);
        if(bankAccount.isEmpty()){
            throw new BankAccountNotFoundException();
        }
        AccountOperation accountOperation1 = new AccountOperation();
        accountOperation1.setType(OperationType.CREDIT);
        accountOperation1.setDateOperation(new Date());
        accountOperation1.setAmount(solde);
        accountOperation1.setDescription("add description later...");
        accountOperation1.setBankAccount(bankAccount.get());
        operationAccRepository.save(accountOperation1);
        bankAccount.get().setBalance(bankAccount.get().getBalance()+solde);
        bankAccRepository.save(bankAccount.get());
    }

    @Override
    public void transferer(String bankAccountSourceId, String bankAccountDestinationId, double solde) {
        debiter(bankAccountSourceId,solde);
        crediter(bankAccountDestinationId,solde);
    }


}
