package com.university.Timetable.Management.System.controller;

import com.university.Timetable.Management.System.exception.BookRoomCollectionException;
import com.university.Timetable.Management.System.model.BookRoom;
import com.university.Timetable.Management.System.repo.BookRoomRepo;
import com.university.Timetable.Management.System.service.BookRoomService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookRoom")
public class BookRoomController {

    @Autowired
    private BookRoomService bookRoomService;

    @Autowired
    private BookRoomRepo bookRoomRepo;

    @PostMapping("/addBookRoom")
    public ResponseEntity<?> createBookRoom(@RequestBody BookRoom bookRoom, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            // Extract room and class session IDs from the BookRoom object
            String roomId = bookRoom.getRoom().getId();
            String classSessionId = bookRoom.getClassSession().getId();

            // Call the service method with the extracted IDs
            bookRoomService.createBookRoom(roomId, classSessionId);

            // Return a response indicating success
            return new ResponseEntity<>("Booking Created Success", HttpStatus.CREATED);
        } catch (ConstraintViolationException | BookRoomCollectionException e) {
            // Return appropriate error response in case of exceptions
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getAllBookRooms")
    public ResponseEntity<?> getAllBookRooms(Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        List<BookRoom> bookRooms = bookRoomService.getAllBookRoom();
        return new ResponseEntity<>(bookRooms, !bookRooms.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteBookRoom/{id}")
    public ResponseEntity<?> deleteBookRoomById(@PathVariable("id") String id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            bookRoomService.deleteBookRoomById(id);
            return new ResponseEntity<>("Booking Deleted Success", HttpStatus.OK);
        } catch (BookRoomCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
