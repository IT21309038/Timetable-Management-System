package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.RoomCollectionException;
import com.university.Timetable.Management.System.model.Room;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public interface RoomService {

    public void createRoom(Room room) throws ConstraintViolationException, RoomCollectionException;

    public List<Room> getAllRoom();

    public Room getSingleRoomByRoomCode(String roomCode) throws RoomCollectionException;

    public void updateRoom(String id, Room room) throws RoomCollectionException;

    public void deleteRoomById(String id) throws RoomCollectionException;
}
