package com.university.Timetable.Management.System;

import com.university.Timetable.Management.System.exception.StudentCollectionException;
import com.university.Timetable.Management.System.model.Student;
import com.university.Timetable.Management.System.repo.StudentRepo;
import com.university.Timetable.Management.System.service.StudentServiceImpl;
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

class StudentServiceImplTest {

    @Mock
    private StudentRepo studentRepo;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateStudent_Success() {
        Student student = new Student();
        student.setStudentId("123");
        when(studentRepo.findByStudentId("123")).thenReturn(Optional.empty());

        try {
            studentService.createStudent(student);
            verify(studentRepo, times(1)).save(student);
        } catch (StudentCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testGetAllStudent_NotEmpty() {
        List<Student> students = new ArrayList<>();
        students.add(new Student());
        when(studentRepo.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudent();

        assertFalse(result.isEmpty());
        assertEquals(students, result);
    }

    @Test
    void testGetSingleStudentByStudentId_Success() {
        Student student = new Student();
        student.setStudentId("123");
        when(studentRepo.findByStudentId("123")).thenReturn(Optional.of(student));

        try {
            Student result = studentService.getSingleStudentByStudentId("123");
            assertEquals(student, result);
        } catch (StudentCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testUpdateStudent_Success() {
        Student student = new Student();
        student.setId("1");
        student.setStudentId("123");
        when(studentRepo.findById("1")).thenReturn(Optional.of(student));
        when(studentRepo.findByStudentId("123")).thenReturn(Optional.empty());

        try {
            studentService.updateStudent("1", student);
            verify(studentRepo, times(1)).save(student);
        } catch (StudentCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testDeleteStudentById_Success() {
        Student student = new Student();
        student.setId("1");
        when(studentRepo.findById("1")).thenReturn(Optional.of(student));

        try {
            Student result = studentService.deleteStudentById("1");
            assertEquals(student, result);
            verify(studentRepo, times(1)).deleteById("1");
        } catch (StudentCollectionException e) {
            fail("Exception not expected here");
        }
    }
}

