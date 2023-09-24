package com.project.ebankingbackend.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CustomerHistoryDro {
    private List<CustomerDto> customerDtos;
    private int currentPage;
    private int totalPages;
    private int pageSize;
}
