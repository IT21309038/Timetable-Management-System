package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.StudentCollectionException;
import com.university.Timetable.Management.System.model.Student;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

public interface StudentService {

    public Student createStudent(Student student) throws ConstraintViolationException, StudentCollectionException;

    public List<Student> getAllStudent();

    public Student getSingleStudentByStudentId(String studentId) throws StudentCollectionException;

    public Student updateStudent(String id, Student student) throws StudentCollectionException;

    public Student deleteStudentById(String id) throws StudentCollectionException;
}
