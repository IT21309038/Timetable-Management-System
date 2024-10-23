package com.university.Timetable.Management.System;

import com.university.Timetable.Management.System.exception.RoomCollectionException;
import com.university.Timetable.Management.System.model.Room;
import com.university.Timetable.Management.System.repo.RoomRepo;
import com.university.Timetable.Management.System.service.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    @Mock
    private RoomRepo roomRepo;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRoom_Success() {
        Room room = new Room();
        room.setRoomCode("101");
        when(roomRepo.findByRoomCode("101")).thenReturn(Optional.empty());

        try {
            roomService.createRoom(room);
            verify(roomRepo, times(1)).save(room);
        } catch (RoomCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testGetAllRoom_NotEmpty() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room());
        when(roomRepo.findAll()).thenReturn(rooms);

        List<Room> result = roomService.getAllRoom();

        assertFalse(result.isEmpty());
        assertEquals(rooms, result);
    }

    @Test
    void testGetSingleRoomByRoomCode_Success() {
        Room room = new Room();
        room.setRoomCode("101");
        when(roomRepo.findByRoomCode("101")).thenReturn(Optional.of(room));

        try {
            Room result = roomService.getSingleRoomByRoomCode("101");
            assertEquals(room, result);
        } catch (RoomCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testUpdateRoom_Success() {
        Room room = new Room();
        room.setId("1");
        room.setRoomCode("101");
        when(roomRepo.findById("1")).thenReturn(Optional.of(room));
        when(roomRepo.findByRoomCode("101")).thenReturn(Optional.empty());

        try {
            roomService.updateRoom("1", room);
            verify(roomRepo, times(1)).save(room);
        } catch (RoomCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testDeleteRoomById_Success() {
        Room room = new Room();
        room.setId("1");
        when(roomRepo.findById("1")).thenReturn(Optional.of(room));

        try {
            roomService.deleteRoomById("1");
            verify(roomRepo, times(1)).deleteById("1");
        } catch (RoomCollectionException e) {
            fail("Exception not expected here");
        }
    }
}

