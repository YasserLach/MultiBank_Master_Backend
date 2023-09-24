package com.project.ebankingbackend.web;

import com.project.ebankingbackend.dtos.AccountHistoryDto;
import com.project.ebankingbackend.dtos.AccountOperationDto;
import com.project.ebankingbackend.dtos.OperationHistoryDto;
import com.project.ebankingbackend.entities.AccountOperation;
import com.project.ebankingbackend.exceptions.BankAccountNotFoundException;
import com.project.ebankingbackend.services.impl.AccountOperationServiceImpl;
import com.project.ebankingbackend.services.impl.BankAccountServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operations")
@AllArgsConstructor
@CrossOrigin("*")
public class AccountOperationController {

    private AccountOperationServiceImpl accountOperationService ;


    @GetMapping
    public ResponseEntity<OperationHistoryDto> listOperationsForAccountPerPage(
            @RequestParam(name = "accountID")String accountID,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "3")int size
    ){
        return new ResponseEntity<>(accountOperationService.listOperationsForAccountPerPage(accountID,page,size),HttpStatus.OK);
    }

    @GetMapping("/{accountID}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<List<AccountOperationDto>> listOperationsForAccount(@PathVariable String accountID){
        return new ResponseEntity<>(accountOperationService.listOperationsForAccount(accountID), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<List<AccountOperationDto>> allOperations(){
        return new ResponseEntity<>(accountOperationService.findOperationsForAccount(), HttpStatus.OK);
    }


  /*  @GetMapping("/{accountId}/operation")
    public AccountHistoryDto getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return accountOperationService.listOperationsForAccountPerPage(accountId,page,size);
    }*/



}
