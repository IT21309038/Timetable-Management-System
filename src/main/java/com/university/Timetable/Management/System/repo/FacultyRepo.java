package com.university.Timetable.Management.System.repo;

import com.university.Timetable.Management.System.model.Faculty;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FacultyRepo extends MongoRepository<Faculty, String> {

    Optional<Faculty> findById(String id);
}
