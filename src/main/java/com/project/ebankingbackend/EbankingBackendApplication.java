package com.project.ebankingbackend;

import com.project.ebankingbackend.entities.*;
import com.project.ebankingbackend.enums.AccountStatus;
import com.project.ebankingbackend.enums.OperationType;
import com.project.ebankingbackend.repositories.BankAccRepository;
import com.project.ebankingbackend.repositories.CustomerRepository;
import com.project.ebankingbackend.repositories.OperationAccRepository;
import com.project.ebankingbackend.repositories.StudentRepository;
import com.project.ebankingbackend.security.RsaKeysConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;



@SpringBootApplication
@Slf4j
@EnableConfigurationProperties(RsaKeysConfig.class)
public class EbankingBackendApplication {

    public static void main(String[] args) {SpringApplication.run(EbankingBackendApplication.class, args);}

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


   // @Bean
    CommandLineRunner start(CustomerRepository customerRepo, BankAccRepository bankAccRepo, OperationAccRepository operationRepo){
        return args -> {
            Stream.of("Nadia","Omar","Sofiane","Yasser").forEach(cust -> {
                Customer customer = new Customer();
                customer.setName(cust);
                customer.setEmail(cust+"1@gmail.com");
                customerRepo.save(customer);
            });

            customerRepo.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCreatedAt(new Date());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCurrency("DH");
                currentAccount.setOverDraft(9000);
                currentAccount.setCustomer(customer);
                bankAccRepo.save(currentAccount);


                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setCreatedAt(new Date());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCurrency("DH");
                savingAccount.setInterestRate(5.5);
                savingAccount.setCustomer(customer);
                bankAccRepo.save(savingAccount);

            });

            bankAccRepo.findAll().forEach(bankAccount -> {
                for (int i=0; i<10;i++){
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setDateOperation(new Date());
                    accountOperation.setAmount(Math.random()*3000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT: OperationType.CREDIT);
                    accountOperation.setDescription("add description later...");
                    accountOperation.setBankAccount(bankAccount);
                    operationRepo.save(accountOperation);
                }
            });


        };
    }

    // to get all of the customers
   // @Bean
     CommandLineRunner commandLineRunner(CustomerRepository customerRepository,BankAccRepository bankAccRepository,OperationAccRepository operationAccRepository){
        return args -> {
            bankAccRepository.findAll().forEach(bankAccount -> {
                System.out.println(bankAccount.getCreatedAt());
                System.out.println(bankAccount.getCustomer().getEmail());
                bankAccount.getAccountOperations().forEach(operation -> {
                    System.out.println(operation.getAmount());
                });
            });
        };
    }

    // to create new Student
    //@Bean
    CommandLineRunner createStudent(StudentRepository studentRepository){
        return args -> {
            Stream.of("Hamza","Simo","Anas","Meskini").forEach(s -> {
                Student student = new Student();
                student.setFirstName(s);
                student.setLastName(s+" Ben");
                student.setEmail(s+"-google@gmail.com");
                studentRepository.save(student);
            });
        };
    }


}
