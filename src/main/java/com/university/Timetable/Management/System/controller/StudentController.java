package com.university.Timetable.Management.System.controller;

import com.university.Timetable.Management.System.exception.StudentCollectionException;
import com.university.Timetable.Management.System.model.Student;
import com.university.Timetable.Management.System.repo.StudentRepo;
import com.university.Timetable.Management.System.service.StudentService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepo studentRepo;

    @PostMapping("/addStudent")
    public ResponseEntity<?> createStudent(@RequestBody Student student, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT"))) {
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_STUDENT role.");
        }
        try {
            studentService.createStudent(student);
            return new  ResponseEntity<Student>(student, HttpStatus.CREATED);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (StudentCollectionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<?> getAllStudents(Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT"))) {
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_STUDENT role.");
        }
        List<Student> students = studentService.getAllStudent();
        return new ResponseEntity<>(students, !students.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getSingleStudent/{studentId}")
    public ResponseEntity<?> getSingleStudentByStudentId(@PathVariable("studentId") String studentId, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT"))) {
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_STUDENT role.");
        }
        try {
            return new ResponseEntity<>(studentService.getSingleStudentByStudentId(studentId), HttpStatus.OK);
        } catch (StudentCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") String id, @RequestBody Student student, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT"))) {
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_STUDENT role.");
        }
        try {
            studentService.updateStudent(id, student);
            return new ResponseEntity<>("Student with ID: " + id + " has been updated.", HttpStatus.OK);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (StudentCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteStudent/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") String id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT"))) {
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_STUDENT role.");
        }
        try {
            studentService.deleteStudentById(id);
            return new ResponseEntity<>("Student with ID: " + id + " has been deleted.", HttpStatus.OK);
        } catch (StudentCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
