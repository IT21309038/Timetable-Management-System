package com.university.Timetable.Management.System.repo;

import com.university.Timetable.Management.System.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface RoomRepo extends MongoRepository<Room,String> {

    @Query("{'roomCode': ?0}")
    Optional<Room> findByRoomCode(String roomCode);

    Optional<Room> findById(String id);

    void deleteById(String id);
}
