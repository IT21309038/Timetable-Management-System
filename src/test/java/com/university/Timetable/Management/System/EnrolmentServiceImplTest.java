package com.university.Timetable.Management.System;

import com.university.Timetable.Management.System.exception.EnrolmentCollectionException;
import com.university.Timetable.Management.System.model.Course;
import com.university.Timetable.Management.System.model.Student;
import com.university.Timetable.Management.System.repo.CourseRepo;
import com.university.Timetable.Management.System.repo.StudentRepo;
import com.university.Timetable.Management.System.service.EnrolmentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnrolmentServiceImplTest {

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private EnrolmentServiceImpl enrolmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEnrollStudentToCourse_Success() {
        Student student = new Student();
        student.setId("1");

        Course course = new Course();
        course.setId("2");

        when(studentRepo.findById("1")).thenReturn(Optional.of(student));
        when(courseRepo.findById("2")).thenReturn(Optional.of(course));

        assertDoesNotThrow(() -> enrolmentService.enrollStudentToCourse("1", "2"));

        assertTrue(course.getStudents().contains(student));
        assertTrue(student.getCourses().contains(course));

        verify(courseRepo, times(1)).save(course);
        verify(studentRepo, times(1)).save(student);
    }

    @Test
    void testGetStudentsByCourseCode_Success() {
        Course course = new Course();
        course.setCourseCode("CSE101");

        Student student1 = new Student();
        Student student2 = new Student();

        course.addStudent(student1);
        course.addStudent(student2);

        when(courseRepo.findAll()).thenReturn(new ArrayList<Course>() {{ add(course); }});

        ArrayList<Student> students = enrolmentService.getStudentsByCourseCode("CSE101");

        assertEquals(2, students.size());
        assertTrue(students.contains(student1));
        assertTrue(students.contains(student2));
    }

    @Test
    void testDeleteStudentFromCourse_Success() {
        Student student = new Student();
        student.setId("1");

        Course course = new Course();
        course.setId("2");
        course.addStudent(student);

        when(courseRepo.findById("2")).thenReturn(Optional.of(course));
        when(studentRepo.findById("1")).thenReturn(Optional.of(student));

        assertDoesNotThrow(() -> enrolmentService.deleteStudentFromCourse("1", "2"));

        assertFalse(course.getStudents().contains(student));
        assertFalse(student.getCourses().contains(course));

        verify(courseRepo, times(1)).save(course);
        verify(studentRepo, times(1)).save(student);
    }

}

