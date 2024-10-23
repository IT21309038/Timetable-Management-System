package com.university.Timetable.Management.System.repo;

import com.university.Timetable.Management.System.model.BookRoom;
import com.university.Timetable.Management.System.model.ClassSession;
import com.university.Timetable.Management.System.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface BookRoomRepo extends MongoRepository<BookRoom, String> {

    Optional<BookRoom> findById(BookRoom id);

    Optional<BookRoom> findByRoomAndClassSession(Room room, ClassSession classSession);

    List<BookRoom> findByRoomAndIsInUse(Room room, boolean b);
}
