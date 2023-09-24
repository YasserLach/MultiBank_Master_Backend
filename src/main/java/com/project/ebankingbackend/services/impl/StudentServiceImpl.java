package com.project.ebankingbackend.services.impl;

import com.project.ebankingbackend.entities.Customer;
import com.project.ebankingbackend.entities.Student;
import com.project.ebankingbackend.repositories.CustomerRepository;
import com.project.ebankingbackend.repositories.StudentRepository;
import com.project.ebankingbackend.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    @Override
    public List<Student> getListStudent(){
        return studentRepository.findAll();
    }



}
