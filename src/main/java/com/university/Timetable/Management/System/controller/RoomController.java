package com.university.Timetable.Management.System.controller;

import com.university.Timetable.Management.System.exception.RoomCollectionException;
import com.university.Timetable.Management.System.model.Room;
import com.university.Timetable.Management.System.repo.RoomRepo;
import com.university.Timetable.Management.System.service.RoomService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomRepo roomRepo;

    @PostMapping("/addRoom")
    public ResponseEntity<?> createRoom(@RequestBody Room room, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            roomService.createRoom(room);
            return new  ResponseEntity<Room>(room, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (RoomCollectionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getAllRooms")
    public ResponseEntity<?> getAllRooms(Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        List<Room> rooms = roomService.getAllRoom();
        return new ResponseEntity<>(rooms, !rooms.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getSingleRoom/{roomCode}")
    public ResponseEntity<?> getSingleRoomByRoomCode(@PathVariable("roomCode") String roomCode, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            return new ResponseEntity<>(roomService.getSingleRoomByRoomCode(roomCode), HttpStatus.OK);
        } catch (RoomCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateRoom/{id}")
    public ResponseEntity<?> updateRoom(@PathVariable("id") String id, @RequestBody Room room, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            roomService.updateRoom(id, room);
            return new ResponseEntity<>("Room with ID: " + id + " has been updated.", HttpStatus.OK);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (RoomCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteRoom/{id}")
    public ResponseEntity<?> deleteRoomById(@PathVariable("id") String id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            roomService.deleteRoomById(id);
            return new ResponseEntity<>("Room with ID: " + id + " has been deleted.", HttpStatus.OK);
        } catch (RoomCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
