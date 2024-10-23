package com.university.Timetable.Management.System.controller;

import com.university.Timetable.Management.System.exception.BookResourceCollectionException;
import com.university.Timetable.Management.System.model.BookResource;
import com.university.Timetable.Management.System.repo.BookResourceRepo;
import com.university.Timetable.Management.System.service.BookResourceService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookResource")
public class BookResourceController {

    @Autowired
    private BookResourceService bookResourceService;

    @Autowired
    private BookResourceRepo bookResourceRepo;

    @PostMapping("/addBookResource")
    public ResponseEntity<?> createBookResource(@RequestBody BookResource bookResource, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            String resourceId = bookResource.getResource().getId();
            bookResourceService.createBookResource(resourceId,bookResource);
            return new ResponseEntity<BookResource>(bookResource, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (BookResourceCollectionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getAllBookResources")
    public ResponseEntity<?> getAllBookResources(Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        List<BookResource> bookResources = bookResourceService.getAllBookResource();
        return new ResponseEntity<>(bookResources, !bookResources.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getSingleBookResource/{id}")
    public ResponseEntity<?> getSingleBookResourceById(@PathVariable("id") String id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            return new ResponseEntity<>(bookResourceService.getSingleBookResourceById(id), HttpStatus.OK);
        } catch (BookResourceCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteBookResource/{id}")
    public ResponseEntity<?> deleteBookResourceById(@PathVariable("id") String id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            bookResourceService.deleteBookResourceById(id);
            return new ResponseEntity<>("BookResource with ID: " + id + " has been deleted.", HttpStatus.OK);
        } catch (BookResourceCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
