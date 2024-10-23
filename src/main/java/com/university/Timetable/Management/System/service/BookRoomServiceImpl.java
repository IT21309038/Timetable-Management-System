package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.BookRoomCollectionException;
import com.university.Timetable.Management.System.model.BookRoom;
import com.university.Timetable.Management.System.model.ClassSession;
import com.university.Timetable.Management.System.model.Room;
import com.university.Timetable.Management.System.repo.BookRoomRepo;
import com.university.Timetable.Management.System.repo.ClassSessionRepo;
import com.university.Timetable.Management.System.repo.RoomRepo;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookRoomServiceImpl implements BookRoomService {

    @Autowired
    private BookRoomRepo bookRoomRepo;

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private ClassSessionRepo classSessionRepo;


    @Override
    public void createBookRoom(String roomId, String classSessionId) throws ConstraintViolationException, BookRoomCollectionException {
        // Check if the room exists and is available
        Optional<Room> roomOptional = roomRepo.findById(roomId);
        if (roomOptional.isEmpty()) {
            throw new BookRoomCollectionException("Room is not available or does not exist");
        }

        // Check if the class session exists and is available
        Optional<ClassSession> classSessionOptional = classSessionRepo.findById(classSessionId);
        if (classSessionOptional.isEmpty()) {
            throw new BookRoomCollectionException("Class session is not available or does not exist");
        }

        // Check if the room is already booked for the given class session
        Optional<BookRoom> existingBookRoom = bookRoomRepo.findByRoomAndClassSession(roomOptional.get(), classSessionOptional.get());
        if (existingBookRoom.isPresent()) {
            throw new BookRoomCollectionException("Room is already booked for the given class session");
        }

        // Check if the room is already booked for another session during the specified time slot
        List<BookRoom> bookedRooms = bookRoomRepo.findByRoomAndIsInUse(roomOptional.get(), true);
        for (BookRoom bookedRoom : bookedRooms) {
            if (isOverlapping(bookedRoom.getClassSession(), classSessionOptional.get())) {
                throw new BookRoomCollectionException("Room is already booked for another session during the specified time slot");
            }
        }

        // Create and save the bookRoom
        BookRoom bookRoom = new BookRoom();
        bookRoom.setRoom(roomOptional.get());
        bookRoom.setClassSession(classSessionOptional.get());
        bookRoom.setIsInUse(true);
        bookRoom.setCreatedAt(new Date(System.currentTimeMillis()));
        bookRoomRepo.save(bookRoom);
    }

    // Helper method to check if two class sessions overlap
    private boolean isOverlapping(ClassSession session1, ClassSession session2) {
        // Get the start and end times of both sessions
        LocalTime session1StartTime = session1.getStartTime();
        LocalTime session1EndTime = session1.getEndTime();
        LocalTime session2StartTime = session2.getStartTime();
        LocalTime session2EndTime = session2.getEndTime();

        // Check if session1 overlaps with session2
        boolean session1OverlapsWithSession2 =
                session1StartTime.isBefore(session2EndTime) && session1EndTime.isAfter(session2StartTime);

        // Check if session2 overlaps with session1
        boolean session2OverlapsWithSession1 =
                session2StartTime.isBefore(session1EndTime) && session2EndTime.isAfter(session1StartTime);

        // Return true if there is an overlap, false otherwise
        return session1OverlapsWithSession2 || session2OverlapsWithSession1;
    }



    @Override
    public List<BookRoom> getAllBookRoom() {
        List<BookRoom> bookRooms = bookRoomRepo.findAll();
        if (!bookRooms.isEmpty()){
            return bookRooms;
        }else {
            return new ArrayList<BookRoom>();
        }
    }

    @Override
    public void updateBookRoom(String id, BookRoom bookRoom) throws BookRoomCollectionException {

    }

    @Override
    public void deleteBookRoomById(String id) throws BookRoomCollectionException {
        Optional<BookRoom> bookRoom = bookRoomRepo.findById(id);
        if(!bookRoom.isPresent()){
            throw new BookRoomCollectionException(BookRoomCollectionException.NotFoundException(id));
        }else {
            bookRoomRepo.deleteById(id);
        }
    }
}
