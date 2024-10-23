package com.university.Timetable.Management.System.service;

import com.university.Timetable.Management.System.exception.CourseCollectionException;
import com.university.Timetable.Management.System.model.Course;
import jakarta.validation.ConstraintViolationException;
import org.bson.types.ObjectId;

import java.util.List;

public interface CourseService {

    public Course createCourse(Course course) throws ConstraintViolationException, CourseCollectionException;

    public List<Course> getAllCourse();

    public Course getSingleCourseByCourseCode(String courseCode) throws CourseCollectionException;

    public void updateCourse(String id, Course course) throws CourseCollectionException;

    public void deleteCourseById(String id) throws CourseCollectionException;
}
