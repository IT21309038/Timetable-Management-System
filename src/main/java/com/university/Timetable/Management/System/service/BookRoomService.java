package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.BookRoomCollectionException;
import com.university.Timetable.Management.System.model.BookRoom;
import jakarta.validation.ConstraintViolationException;

import java.time.DayOfWeek;
import java.util.List;

public interface BookRoomService {

    public void createBookRoom(String roomId, String classSessionId) throws ConstraintViolationException, BookRoomCollectionException;

    public List<BookRoom> getAllBookRoom();

    public void updateBookRoom(String id, BookRoom bookRoom) throws BookRoomCollectionException;

    public void deleteBookRoomById(String id) throws BookRoomCollectionException;

}
