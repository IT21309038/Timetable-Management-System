package com.university.Timetable.Management.System.repo;

import com.university.Timetable.Management.System.model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface ResourceRepo extends MongoRepository<Resource, String> {

    @Query("{'resourceCode': ?0}")
    Optional<Resource> findByResourceCode(String resourceCode);

    Optional<Resource> findById(String id);

    void deleteById(String id);
}
