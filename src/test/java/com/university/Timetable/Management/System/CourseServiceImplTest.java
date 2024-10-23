package com.university.Timetable.Management.System;

import com.university.Timetable.Management.System.exception.CourseCollectionException;
import com.university.Timetable.Management.System.model.Course;
import com.university.Timetable.Management.System.repo.CourseRepo;
import com.university.Timetable.Management.System.service.CourseServiceImpl;
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

class CourseServiceImplTest {

    @Mock
    private CourseRepo courseRepo;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCourse_Success() {
        Course course = new Course();
        course.setCourseCode("CSE101");
        when(courseRepo.findByCourseCode("CSE101")).thenReturn(Optional.empty());

        try {
            Course result = courseService.createCourse(course);
            assertNotNull(result);
            verify(courseRepo, times(1)).save(course);
        } catch (CourseCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testGetAllCourse_NotEmpty() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course());
        when(courseRepo.findAll()).thenReturn(courses);

        List<Course> result = courseService.getAllCourse();

        assertFalse(result.isEmpty());
        assertEquals(courses, result);
    }

    @Test
    void testGetSingleCourseByCourseCode_Success() {
        Course course = new Course();
        course.setCourseCode("CSE101");
        when(courseRepo.findByCourseCode("CSE101")).thenReturn(Optional.of(course));

        try {
            Course result = courseService.getSingleCourseByCourseCode("CSE101");
            assertEquals(course, result);
        } catch (CourseCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testUpdateCourse_Success() {
        Course course = new Course();
        course.setId("1");
        course.setCourseCode("CSE101");
        when(courseRepo.findById("1")).thenReturn(Optional.of(course));
        when(courseRepo.findByCourseCode("CSE101")).thenReturn(Optional.empty());

        try {
            courseService.updateCourse("1", course);
            verify(courseRepo, times(1)).save(course);
        } catch (CourseCollectionException e) {
            fail("Exception not expected here");
        }
    }

    @Test
    void testDeleteCourseById_Success() {
        Course course = new Course();
        course.setId("1");
        when(courseRepo.findById("1")).thenReturn(Optional.of(course));

        try {
            courseService.deleteCourseById("1");
            verify(courseRepo, times(1)).deleteById("1");
        } catch (CourseCollectionException e) {
            fail("Exception not expected here");
        }
    }
}

