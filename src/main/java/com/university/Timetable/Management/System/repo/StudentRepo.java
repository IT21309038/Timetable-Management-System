package com.university.Timetable.Management.System.repo;

import com.university.Timetable.Management.System.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepo extends MongoRepository<Student, String> {

    Optional<Student> findByStudentId(String studentId);

    Optional<Student> findById(String id);

    void deleteById(String id);
}
