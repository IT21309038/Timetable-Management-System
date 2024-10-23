package com.university.Timetable.Management.System.repo;

import com.university.Timetable.Management.System.model.Course;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CourseRepo extends MongoRepository<Course, String> {

    @Query("{'courseCode': ?0}")
    Optional<Course> findByCourseCode(String courseCode);

    Optional<Course> findById(String id);

    void deleteById(String id);
}
