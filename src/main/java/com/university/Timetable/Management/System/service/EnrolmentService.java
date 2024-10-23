package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.EnrolmentCollectionException;
import com.university.Timetable.Management.System.model.Student;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;

public interface EnrolmentService {

    public void enrollStudentToCourse(String studentId, String courseId)
            throws ConstraintViolationException, EnrolmentCollectionException;

    public ArrayList<Student> getStudentsByCourseCode(String courseCode);

    public void deleteStudentFromCourse(String studentId, String courseId)
            throws ConstraintViolationException, EnrolmentCollectionException;

    public void addFacultyToCourse(String facultyId, String courseId)
            throws ConstraintViolationException, EnrolmentCollectionException;
}
