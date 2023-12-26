package com.iffat.springboot.association.repositories;

import com.iffat.springboot.association.entities.Course;
import com.iffat.springboot.association.entities.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students WHERE c.id = ?1")
    Optional<Course> findOneWithStudents(long id);
}
