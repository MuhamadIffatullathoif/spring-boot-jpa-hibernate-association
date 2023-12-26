package com.iffat.springboot.association.repositories;

import com.iffat.springboot.association.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.id = ?1")
    Optional<Student> findOneWithCourse(Long id);
}
