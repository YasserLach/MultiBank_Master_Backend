package com.project.ebankingbackend.repositories;

import com.project.ebankingbackend.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankAccRepository extends JpaRepository<BankAccount,String > {


        List<BankAccount> findByCustomerName(String userName);

        @Query("SELECT b FROM BankAccount b WHERE b.id LIKE concat('%', :keywords, '%') OR b.currency LIKE concat('%', :keywords, '%') OR CAST(b.balance AS string) LIKE concat('%', :keywords, '%')")
        Page<BankAccount> findAllAccountsBySearch(@Param("keywords") String keywords, Pageable pageable);
        @Query("SELECT b FROM BankAccount b WHERE b.customer.userName = :userName AND (b.id LIKE concat('%', :keywords, '%') OR b.currency LIKE concat('%', :keywords, '%') OR CAST(b.balance AS string) LIKE concat('%', :keywords, '%'))")
        Page<BankAccount> findByUserNameSearch(@Param("userName") String userName,@Param("keywords") String keywords,Pageable pageable);


}
