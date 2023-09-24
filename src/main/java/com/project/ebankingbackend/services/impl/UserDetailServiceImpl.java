package com.project.ebankingbackend.services.impl;

import com.project.ebankingbackend.entities.Customer;
import com.project.ebankingbackend.exceptions.CustomerNotFoundException;
import com.project.ebankingbackend.services.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private CustomerService customerService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer =customerService.getCustomerByUserName(username);
        if (customer == null) throw new CustomerNotFoundException();
        String[] roles = customer.getAppRoles().stream().map(u -> u.getRoleName()).toArray(String[]::new);
        UserDetails userDetails = User
                .withUsername(customer.getUserName())
                .password(customer.getPassword())
                .roles(roles).build();
        return userDetails;
    }
}
