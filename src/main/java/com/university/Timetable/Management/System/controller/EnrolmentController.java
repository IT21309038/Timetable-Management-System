package com.university.Timetable.Management.System.controller;

import com.university.Timetable.Management.System.DTO.EnrolmentDTO;
import com.university.Timetable.Management.System.exception.EnrolmentCollectionException;
import com.university.Timetable.Management.System.model.Student;
import com.university.Timetable.Management.System.service.EnrolmentService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/enrolment")
public class EnrolmentController {

    @Autowired
    private EnrolmentService enrolmentService;

    @PostMapping("/enrollStudentToCourse")
    public ResponseEntity<?> enrollStudentToCourse(@RequestBody EnrolmentDTO enrolmentDTO, Authentication authentication) {
        // Check if the user has the required role
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT"))) {
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_STUDENT role.");
        }

        try {
            enrolmentService.enrollStudentToCourse(enrolmentDTO.getStudentId(), enrolmentDTO.getCourseId());
            return new ResponseEntity<EnrolmentDTO>(enrolmentDTO, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (EnrolmentCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @PostMapping("/addFacultyToCourse")
    public ResponseEntity<?> addFacultyToCourse(@RequestBody EnrolmentDTO enrolmentDTO, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN role.");
        }
        try {
            enrolmentService.addFacultyToCourse(enrolmentDTO.getFacultyId(), enrolmentDTO.getCourseId());
            return new ResponseEntity<EnrolmentDTO>(enrolmentDTO, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (EnrolmentCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/getStudentsByCourseCode/{courseCode}")
    public ResponseEntity<?> getStudentsByCourseCode(@PathVariable("courseCode") String courseCode, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        ArrayList<Student> students = enrolmentService.getStudentsByCourseCode(courseCode);
        return new ResponseEntity<>(students, !students.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteStudentFromCourse")
    public ResponseEntity<?> deleteStudentFromCourse(@RequestBody EnrolmentDTO enrolmentDTO, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }

        try {
            enrolmentService.deleteStudentFromCourse(enrolmentDTO.getStudentId(), enrolmentDTO.getCourseId());
            return new ResponseEntity<>("Student with ID: " + enrolmentDTO.getStudentId() + " has been deleted from course with ID: " + enrolmentDTO.getCourseId(), HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (EnrolmentCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
