package com.project.ebankingbackend.web;

import com.project.ebankingbackend.entities.Customer;
import com.project.ebankingbackend.entities.Student;
import com.project.ebankingbackend.services.impl.StudentServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {
    private StudentServiceImpl studentService;

    @GetMapping
    public List<Student> getStudents(){
        return studentService.getListStudent();
    }


}
