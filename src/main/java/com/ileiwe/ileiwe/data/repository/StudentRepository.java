package com.ileiwe.ileiwe.data.repository;

import com.ileiwe.ileiwe.data.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
