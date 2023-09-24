package com.project.ebankingbackend.services;


import com.project.ebankingbackend.dtos.AccountHistoryDto;
import com.project.ebankingbackend.dtos.AccountOperationDto;
import com.project.ebankingbackend.dtos.OperationHistoryDto;
import com.project.ebankingbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountOperationService {


    List<AccountOperationDto> findOperationsForAccount();

    List<AccountOperationDto> listOperationsForAccount(String accountID);

    OperationHistoryDto listOperationsForAccountPerPage(String accountId, int page, int size);


    // AccountHistoryDto listOperationsForAccountPerPage(String accountId, int page, int size);
}
