package com.university.Timetable.Management.System;

import com.university.Timetable.Management.System.exception.BookRoomCollectionException;
import com.university.Timetable.Management.System.model.BookRoom;
import com.university.Timetable.Management.System.model.ClassSession;
import com.university.Timetable.Management.System.model.Room;
import com.university.Timetable.Management.System.repo.BookRoomRepo;
import com.university.Timetable.Management.System.repo.ClassSessionRepo;
import com.university.Timetable.Management.System.repo.RoomRepo;
import com.university.Timetable.Management.System.service.BookRoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookRoomServiceImplTest {

    @Mock
    private BookRoomRepo bookRoomRepo;

    @Mock
    private RoomRepo roomRepo;

    @Mock
    private ClassSessionRepo classSessionRepo;

    @InjectMocks
    private BookRoomServiceImpl bookRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBookRoom_Success() {
        Room room = new Room();
        room.setId("roomId");

        ClassSession classSession = new ClassSession();
        classSession.setId("classSessionId");

        when(roomRepo.findById("roomId")).thenReturn(Optional.of(room));
        when(classSessionRepo.findById("classSessionId")).thenReturn(Optional.of(classSession));
        when(bookRoomRepo.findByRoomAndClassSession(room, classSession)).thenReturn(Optional.empty());
        when(bookRoomRepo.findByRoomAndIsInUse(room, true)).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> bookRoomService.createBookRoom("roomId", "classSessionId"));
        verify(bookRoomRepo, times(1)).save(any(BookRoom.class));
    }

    @Test
    void testCreateBookRoom_RoomNotAvailable() {
        when(roomRepo.findById("roomId")).thenReturn(Optional.empty());

        assertThrows(BookRoomCollectionException.class,
                () -> bookRoomService.createBookRoom("roomId", "classSessionId"));
        verify(bookRoomRepo, never()).save(any());
    }

}

