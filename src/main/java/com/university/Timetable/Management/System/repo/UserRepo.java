package com.university.Timetable.Management.System.repo;

import com.university.Timetable.Management.System.model.UserEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<UserEntity, ObjectId> {

    Optional<UserEntity> findByUsername(String username);
    Boolean existsByUsername(String username);
}
