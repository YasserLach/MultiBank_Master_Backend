package com.project.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class OperationHistoryDto {
    private List<AccountOperationDto> accountOperationDtos;
    private BankAccountDto bankAccountDto;
    private int currentPage;
    private int totalPages;
    private int pageSize;
}
