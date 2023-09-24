package com.project.ebankingbackend.repositories;

import com.project.ebankingbackend.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
