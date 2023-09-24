package com.project.ebankingbackend.dtos;

import com.project.ebankingbackend.enums.AccountStatus;
import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDto {

  // private List<AccountOperationDto> accountOperationDtos;
   private List<BankAccountDto> bankAccountDtos;
   private int currentPage;
   private int totalPages;
   private int pageSize;
}
