package com.university.Timetable.Management.System.repo;

import com.university.Timetable.Management.System.model.BookResource;
import com.university.Timetable.Management.System.model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BookResourceRepo extends MongoRepository<BookResource, String> {

    Optional<BookResource> findById(String id);

    void deleteById(String id);

    List<BookResource> findByResource(Resource resource);
}
