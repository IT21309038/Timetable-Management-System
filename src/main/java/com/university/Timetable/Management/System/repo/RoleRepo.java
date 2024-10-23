package com.university.Timetable.Management.System.repo;

import com.university.Timetable.Management.System.model.Role;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepo extends MongoRepository<Role, ObjectId> {

    Optional<Role> findByName(String name);
}
