package com.project.ebankingbackend.services.impl;

import com.project.ebankingbackend.dtos.CustomerDto;
import com.project.ebankingbackend.dtos.CustomerHistoryDro;
import com.project.ebankingbackend.dtos.NewCustomerDto;
import com.project.ebankingbackend.entities.AppRole;
import com.project.ebankingbackend.entities.Customer;
import com.project.ebankingbackend.exceptions.CustomerAlreadyExistException;
import com.project.ebankingbackend.exceptions.CustomerNotFoundException;
import com.project.ebankingbackend.exceptions.EmailAlreadyExistException;
import com.project.ebankingbackend.mappers.CustomerMapper;
import com.project.ebankingbackend.repositories.AppRoleRepository;
import com.project.ebankingbackend.repositories.BankAccRepository;
import com.project.ebankingbackend.repositories.CustomerRepository;
import com.project.ebankingbackend.repositories.OperationAccRepository;
import com.project.ebankingbackend.services.CustomerService;
import com.project.ebankingbackend.services.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private AppRoleRepository appRoleRepository;
    private BankAccRepository bankAccRepository;
    private OperationAccRepository operationAccRepository;
    private CustomerMapper customerMapper;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;





  /*  @Override
    public CustomerHistoryDro getCustomersPerPage(int page, int size){
        Page<Customer> customers = customerRepository.findAll(PageRequest.of(page, size));
        List<CustomerDto> customerDtos = customers.getContent().stream().map(cust -> customerMapper.fromCustomer(cust)).collect(Collectors.toList());
        CustomerHistoryDro customerHistoryDro = new CustomerHistoryDro();
        customerHistoryDro.setCustomerDtos(customerDtos);
        customerHistoryDro.setCurrentPage(page);
        customerHistoryDro.setPageSize(size);
        customerHistoryDro.setTotalPages(customers.getTotalPages());
        return customerHistoryDro;
    }*/

    @Override
    public CustomerHistoryDro getCustomersPerPage(String keyword, int page, int size) {
        Page<Customer> customers = customerRepository.getAllCustomerBySearch(keyword, PageRequest.of(page, size));
        List<CustomerDto> customerDtos = customers.getContent().stream().map(cust -> customerMapper.fromCustomer(cust)).collect(Collectors.toList());
        CustomerHistoryDro customerHistoryDro = new CustomerHistoryDro();
        customerHistoryDro.setCustomerDtos(customerDtos);
        customerHistoryDro.setCurrentPage(page);
        customerHistoryDro.setPageSize(size);
        customerHistoryDro.setTotalPages(customers.getTotalPages());
        return customerHistoryDro;
    }



    @Override
    public CustomerHistoryDro getInactiveCustomersPerPage(String keyword, int page, int size) {
        Page<Customer> customers = customerRepository.getInactivatedUsers(keyword, PageRequest.of(page, size));
        List<CustomerDto> customerDtos = customers.getContent().stream().map(cust -> customerMapper.fromCustomer(cust)).collect(Collectors.toList());
        CustomerHistoryDro customerHistoryDro = new CustomerHistoryDro();
        customerHistoryDro.setCustomerDtos(customerDtos);
        customerHistoryDro.setCurrentPage(page);
        customerHistoryDro.setPageSize(size);
        customerHistoryDro.setTotalPages(customers.getTotalPages());
        return customerHistoryDro;
    }

    @Override
    public void activateAccount(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isEmpty()) throw new CustomerNotFoundException();
        customer.get().setActivated(true);
    }




    @Override
    public List<CustomerDto> getListCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer -> customerMapper.fromCustomer(customer)).collect(Collectors.toList());
    }


    @Override
    public CustomerDto getCustomer(Long idCustomer) {
        Optional<Customer> customerExist = customerRepository.findById(idCustomer);
        if(customerExist.isEmpty()) throw new CustomerNotFoundException();
        return customerMapper.fromCustomer(customerExist.get());
    }

    @Override
    public CustomerDto getCustomerByName(String userName){
       Optional<Customer> customer = Optional.ofNullable(customerRepository.findByName(userName));
       if(customer.isEmpty()) throw new CustomerNotFoundException();
        return customerMapper.fromCustomer(customer.get());
    }

    @Override
    public Customer getCustomerByUserName(String userName) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findByUserName(userName));
        if(customer.isEmpty()) throw new CustomerNotFoundException();
        return customer.get();
    }



/*
    @Override
    public CustomerDto addCustomer(CustomerDto customerDto){
        Customer customer = customerMapper.fromCustomerDto(customerDto);
        Customer customerSaved = customerRepository.save(customer);
        return customerMapper.fromCustomer(customerSaved);
    }*/

    @Override
    public void addNewCustomer(NewCustomerDto customerDto) throws MessagingException, IOException {
        Optional<Customer> customerByName = Optional.ofNullable(customerRepository.findByName(customerDto.getUserName()));
        Optional<Customer> customerByEmail = Optional.ofNullable(customerRepository.findByEmail(customerDto.getEmail()));
        if (customerByName.isPresent()) throw new CustomerAlreadyExistException();
        if (customerByEmail.isPresent()) throw new CustomerAlreadyExistException();
        customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
        emailService.sendMail(customerDto.getEmail(),customerDto.getName(),"Account Created","Your account has been successfully created.");
        AppRole appRole = new AppRole();
        appRole.setRoleName("USER");
        appRoleRepository.save(appRole);
        List<AppRole> appRoles = new ArrayList<>();
        appRoles.add(appRole);
        Customer customer1 = customerMapper.fromNewCustomerDto(customerDto);
        customer1.setAppRoles(appRoles);
        customer1.setActivated(false);
        customerRepository.save(customer1);
    }


    @Override
    public void deleteCustomer(Long customerId){
        Optional<Customer> customerExist = customerRepository.findById(customerId);
        if(customerExist.isEmpty()) throw new CustomerNotFoundException();
        customerRepository.deleteById(customerId);
        log.info("Customer "+customerExist.get().getName()+" is deleted successfully");
    }


    @Override
    public CustomerDto updateCustomer(CustomerDto customerDto, Long customerId) {
        CustomerDto customerDto1 = getCustomer(customerId);
        if(customerDto1.getEmail().equals(customerDto.getEmail())) throw new EmailAlreadyExistException();
        Customer customer = customerMapper.fromCustomerDto(customerDto);
        return customerMapper.fromCustomer(customerRepository.save(customer));
    }


    public List<CustomerDto> searchForCustomer(String keywords){
        List<Customer> customers = customerRepository.searchCustomer(keywords);
       customers.forEach(customer -> {
            if (customer == null) throw new CustomerNotFoundException();
        });
        return customers.stream().map(customer -> customerMapper.fromCustomer(customer)).collect(Collectors.toList());
    }



}
