package com.university.Timetable.Management.System.controller;

import com.university.Timetable.Management.System.exception.ClassSessionCollectionException;
import com.university.Timetable.Management.System.exception.CourseCollectionException;
import com.university.Timetable.Management.System.model.ClassSession;
import com.university.Timetable.Management.System.repo.ClassSessionRepo;
import com.university.Timetable.Management.System.service.ClassSessionService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;

@RestController
@RequestMapping("/api/v1/classSession")
public class ClassSessionController {

    @Autowired
    private ClassSessionService classSessionService;

    @Autowired
    private ClassSessionRepo classSessionRepo;

    @PostMapping("/addClassSession")
    public ResponseEntity<?> createClassSession(@RequestBody ClassSession classSession, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            classSessionService.createClassSession(classSession);
            return new ResponseEntity<ClassSession>(classSession, HttpStatus.CREATED);
        }catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }catch (ClassSessionCollectionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/getAllClassSessions")
    public ResponseEntity<?> getAllClassSessions(Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        List<ClassSession> classSessions = classSessionService.getAllClassSession();
        return new ResponseEntity<>(classSessions, !classSessions.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getClassSessionByDayOfWeek/{day}")
    public ResponseEntity<?> getClassSessionByDayOfWeek(@PathVariable("day") DayOfWeek day, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        List<ClassSession> classSessions = classSessionService.getClassSessionByDayOfWeek(day);
        return new ResponseEntity<>(classSessions, !classSessions.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getCourseSessionForDay/{courseCode}/{day}")
    public ResponseEntity<?> getCourseSessionForDay(@PathVariable("courseCode") String courseCode, @PathVariable("day") DayOfWeek day, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }

        List<ClassSession> classSessions = classSessionService.getClassSessionByCourseCodeAndDayOfWeek(courseCode, day);
        return new ResponseEntity<>(classSessions, !classSessions.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getCourseSession/{courseCode}")
    public ResponseEntity<?> getCourseSession(@PathVariable("courseCode") String courseCode, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        List<ClassSession> classSessions = classSessionService.getClassSessionByCourseCode(courseCode);
        return new ResponseEntity<>(classSessions, !classSessions.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getSingleClassSession/{id}")
    public ResponseEntity<?> getSingleClassSessionById(@PathVariable("id") String id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            return new ResponseEntity<>(classSessionService.getSingleClassSessionById(id), HttpStatus.OK);
        } catch (ClassSessionCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getClassSessionForStudent/{studentId}")
    public ResponseEntity<?> getClassSessionForStudent(@PathVariable("studentId") String studentId, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        // Check if the user has the required role
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_STUDENT"))) {
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_STUDENT role.");
        }

        // If the user has the required role, proceed with the method logic
        List<ClassSession> classSessions = classSessionService.getAllClassSessionForStudent(studentId);
        return new ResponseEntity<>(classSessions, !classSessions.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }


    @PutMapping("/updateClassSession/{id}")
    public ResponseEntity<?> updateClassSession(@PathVariable("id") String id, @RequestBody ClassSession classSession, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            classSessionService.updateClassSession(id, classSession);
            return new ResponseEntity<>("Class Session with ID: " + id + " has been updated.", HttpStatus.OK);
        }catch (ConstraintViolationException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ClassSessionCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteClassSession/{id}")
    public ResponseEntity<?> deleteClassSessionById(@PathVariable("id") String id, Authentication authentication) {
        if (authentication.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_FACULTY") )){
            // Return unauthorized response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access denied. You need to have ROLE_ADMIN Or ROLE_FACULTY role.");
        }
        try {
            classSessionService.deleteClassSessionById(id);
            return new ResponseEntity<>("Class Session with ID: " + id + " has been deleted.", HttpStatus.OK);
        } catch (ClassSessionCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
