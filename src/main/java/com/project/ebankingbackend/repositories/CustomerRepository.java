package com.project.ebankingbackend.repositories;

import com.project.ebankingbackend.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.name LIKE concat('%', :keywords, '%') OR c.email LIKE concat('%', :keywords, '%')")
    List<Customer> searchCustomer(@Param("keywords") String keywords);

    //Page<Customer> listCustomersPerPage(Pageable pageable);
    @Query("SELECT c FROM Customer c WHERE c.name LIKE concat('%', :keywords, '%') OR c.email LIKE concat('%', :keywords, '%')")
    Page<Customer> getAllCustomerBySearch(@Param("keywords") String keywords,Pageable pageable);

    @Query("SELECT c FROM Customer c WHERE c.isActivated = false AND (c.name LIKE concat('%', :keywords, '%') OR c.email LIKE concat('%', :keywords, '%')) ")
    Page<Customer> getInactivatedUsers(@Param("keywords") String keywords,Pageable pageable);

    Customer findByName(String userName);
    Customer findByEmail(String email);
    Customer findByUserName(String userName);

}
