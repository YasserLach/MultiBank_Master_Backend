package com.project.ebankingbackend.mappers;

import com.project.ebankingbackend.dtos.CustomerDto;
import com.project.ebankingbackend.dtos.NewCustomerDto;
import com.project.ebankingbackend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer,customerDto);
        return customerDto;
    }

    public Customer fromCustomerDto(CustomerDto customerDto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto,customer);
        return customer;
    }

    public Customer fromNewCustomerDto(NewCustomerDto newCustomerDto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(newCustomerDto,customer);
        return customer;
    }

}
