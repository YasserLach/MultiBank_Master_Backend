package com.project.ebankingbackend.web;

import com.project.ebankingbackend.dtos.AccountHistoryDto;
import com.project.ebankingbackend.dtos.BankAccountDto;
import com.project.ebankingbackend.dtos.CurrentAccountDto;
import com.project.ebankingbackend.dtos.SavingAccountDto;
import com.project.ebankingbackend.entities.BankAccount;
import com.project.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.project.ebankingbackend.exceptions.CustomerNotFoundException;
import com.project.ebankingbackend.exceptions.OverBalanceException;
import com.project.ebankingbackend.services.impl.BankAccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankAccount")
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountController {

    private BankAccountServiceImpl bankAccountService;


    @GetMapping("/test")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<AccountHistoryDto> getBanksByUsername(
            @RequestParam(name = "userName")String userName,
            @RequestParam(name = "keyword",defaultValue = "")String keyword,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "3")int size){
        return new ResponseEntity<>(bankAccountService.getAccountsByUserName(userName,keyword,page, size),HttpStatus.OK);
    }

    @GetMapping("/v2")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<AccountHistoryDto> getListAccountsPerPage(
            @RequestParam(name = "keyword",defaultValue = "") String keyword,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "3")int size){
        return new ResponseEntity<>(bankAccountService.getListAccountsByPage(keyword,page,size),HttpStatus.OK);
    }

    @GetMapping("/{accountId}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<BankAccountDto> bankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return new ResponseEntity<>(bankAccountService.getAccountById(accountId),HttpStatus.OK);
    }


    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<List<BankAccountDto>> listBankAccounts(){
        return new ResponseEntity<>(bankAccountService.getListBankAccounts(), HttpStatus.OK) ;
    }

    @DeleteMapping("/{accountId}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> removeBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException{
        bankAccountService.deleteBankAccount(accountId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

/*
    @GetMapping("/{bankAccountId}")
    public ResponseEntity<BankAccountDto> getBankAcc(@PathVariable String bankAccountId)throws BankAccountNotFoundException {
        return new ResponseEntity<>(bankAccountService.getbankAccount(bankAccountId),HttpStatus.OK) ;
    }*/

    @PostMapping("/addCurrentAcount")
    public ResponseEntity<CurrentAccountDto> saveCurrentAccount(@RequestParam Long customerId,@RequestParam double balance,@RequestParam double overDraft) throws CustomerNotFoundException {
        return new ResponseEntity<>(bankAccountService.addNewCurrentAccount(balance,overDraft,customerId),HttpStatus.CREATED) ;
    }

    @PostMapping("/addSavingAccount")
    public ResponseEntity<SavingAccountDto>  saveSavingAccount(@RequestParam Long customerId, @RequestParam double balance, @RequestParam double rate)throws CustomerNotFoundException{
        return new ResponseEntity<>( bankAccountService.addNewSavingAccount(balance,rate,customerId),HttpStatus.CREATED);
    }

    @GetMapping("/debit")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<?> debiter(@RequestParam String bankAccountId,@RequestParam double solde) throws BankAccountNotFoundException, OverBalanceException {
        bankAccountService.debiter(bankAccountId,solde);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/credit")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<?>  crediter(@RequestParam String bankAccountId,@RequestParam double solde) throws BankAccountNotFoundException{
        bankAccountService.crediter(bankAccountId,solde);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/transfert")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<?>  transferer(@RequestParam String bankAccountSourceId,@RequestParam String bankAccountDestinationId,@RequestParam double solde){
        bankAccountService.transferer(bankAccountSourceId,bankAccountDestinationId,solde);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
