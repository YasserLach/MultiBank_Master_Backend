package com.project.ebankingbackend.services;

import com.project.ebankingbackend.dtos.CustomerDto;
import com.project.ebankingbackend.dtos.CustomerHistoryDro;
import com.project.ebankingbackend.dtos.NewCustomerDto;
import com.project.ebankingbackend.entities.Customer;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface CustomerService {

    //CustomerHistoryDro getCustomersPerPage(int page, int size);

    CustomerHistoryDro getCustomersPerPage(String keyword, int page, int size);

    CustomerHistoryDro getInactiveCustomersPerPage(String keyword, int page, int size);

    void activateAccount(Long id);

    List<CustomerDto> getListCustomers();

    CustomerDto getCustomer(Long idCustomer);


    CustomerDto getCustomerByName(String userName);

   // CustomerDto addCustomer(CustomerDto customerDto);

    //void addNewCustomer(Customer customer) throws MessagingException, IOException;

    Customer getCustomerByUserName(String userName);

    void addNewCustomer(NewCustomerDto customerDto) throws MessagingException, IOException;

    void deleteCustomer(Long customerId);

    CustomerDto updateCustomer(CustomerDto customerDto, Long customerId);
}
