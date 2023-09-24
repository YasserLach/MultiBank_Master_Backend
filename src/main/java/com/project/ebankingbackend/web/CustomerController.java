package com.project.ebankingbackend.web;

import com.project.ebankingbackend.dtos.CustomerDto;
import com.project.ebankingbackend.dtos.CustomerHistoryDro;
import com.project.ebankingbackend.dtos.NewCustomerDto;
import com.project.ebankingbackend.entities.Customer;
import com.project.ebankingbackend.exceptions.CustomerAlreadyExistException;
import com.project.ebankingbackend.exceptions.CustomerNotFoundException;
import com.project.ebankingbackend.exceptions.EmailAlreadyExistException;
import com.project.ebankingbackend.services.impl.CustomerServiceImpl;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private CustomerServiceImpl customerService;

    @GetMapping("/v2")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<CustomerHistoryDro> getCustomersByPage(
            @RequestParam(name = "keyword",defaultValue = "") String keyword,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "3")int size) {
        return new ResponseEntity<>(customerService.getCustomersPerPage(keyword,page,size),HttpStatus.OK);
    }

    @GetMapping("/inactive")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<CustomerHistoryDro> getInactiveCustomersByPage(
            @RequestParam(name = "keyword",defaultValue = "") String keyword,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "3")int size) {
        return new ResponseEntity<>(customerService.getInactiveCustomersPerPage(keyword,page,size),HttpStatus.OK);
    }

    @GetMapping("/activate/{userID}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<CustomerHistoryDro> activateAccount(@PathVariable Long userID){
        customerService.activateAccount(userID);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<List<CustomerDto>> getListCustomers(){
        return new  ResponseEntity<>(customerService.getListCustomers(), HttpStatus.OK);
    }

    /*@GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable Long customerId) throws CustomerNotFoundException
    {return new ResponseEntity<>(customerService.getCustomer(customerId),HttpStatus.OK) ;}*/

  /*  @GetMapping("/v2/{userName}")
    public ResponseEntity<Customer> findCustomerByUserName(@PathVariable String userName) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.getCustomerByUserName(userName),HttpStatus.OK);
    }*/

    @GetMapping("/{userName}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public ResponseEntity<CustomerDto> getCustomerByName(@PathVariable String userName) throws CustomerNotFoundException
    {return new ResponseEntity<>(customerService.getCustomerByName(userName),HttpStatus.OK) ;}


    @PostMapping("/addNewOne")
    public ResponseEntity<?> addCustomer(@RequestBody NewCustomerDto customer) throws CustomerAlreadyExistException, MessagingException, IOException {
        customerService.addNewCustomer(customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{customerId}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> removeCustomer(@PathVariable Long customerId) throws CustomerNotFoundException{
        customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto,@PathVariable Long customerId) throws EmailAlreadyExistException {
        customerDto.setId(customerId);
        return new ResponseEntity<>(customerService.updateCustomer(customerDto,customerId),HttpStatus.ACCEPTED) ;
    }


    @GetMapping("/search")
    public ResponseEntity<List<CustomerDto>> searchCustomer(@RequestParam String keywords) throws CustomerNotFoundException {
        return new ResponseEntity<>(customerService.searchForCustomer(keywords),HttpStatus.OK);
    }



}
