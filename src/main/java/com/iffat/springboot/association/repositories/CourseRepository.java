package com.iffat.springboot.association.repositories;

import com.iffat.springboot.association.entities.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
