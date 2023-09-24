package com.project.ebankingbackend.web;

import com.project.ebankingbackend.dtos.CustomerDto;
import com.project.ebankingbackend.entities.Customer;
import com.project.ebankingbackend.services.AuthService;
import com.project.ebankingbackend.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AuthController {

    private AuthService authService;
    private CustomerService customerService;

    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> jwtToken(String username, String password){
        Customer customer= customerService.getCustomerByUserName(username);
        if(customer.isActivated()) {
            Map<String, String> authenticate = authService.authenticate(username, password);
            return new ResponseEntity<>(authenticate, HttpStatus.OK);
        }
        else {
            Map<String, String> notActivated = new HashMap<>();
            notActivated.put("none-activated", "your account has not been activated yet!");
            return new ResponseEntity<>(notActivated,HttpStatus.OK);
        }
    }
}
