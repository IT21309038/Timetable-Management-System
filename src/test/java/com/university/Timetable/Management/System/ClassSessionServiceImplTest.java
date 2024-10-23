package com.university.Timetable.Management.System;

import com.university.Timetable.Management.System.exception.ClassSessionCollectionException;
import com.university.Timetable.Management.System.model.ClassSession;
import com.university.Timetable.Management.System.model.Course;
import com.university.Timetable.Management.System.model.Room;
import com.university.Timetable.Management.System.model.Student;
import com.university.Timetable.Management.System.repo.ClassSessionRepo;
import com.university.Timetable.Management.System.repo.CourseRepo;
import com.university.Timetable.Management.System.repo.StudentRepo;
import com.university.Timetable.Management.System.service.ClassSessionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClassSessionServiceImplTest {

    @Mock
    private ClassSessionRepo classSessionRepo;

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private StudentRepo studentRepo;

    @InjectMocks
    private ClassSessionServiceImpl classSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateClassSession_Success() {
        ClassSession classSession = new ClassSession();
        classSession.setDayOfWeek(DayOfWeek.MONDAY);
        classSession.setStartTime(LocalTime.of(9, 0));
        classSession.setEndTime(LocalTime.of(11, 0));
        classSession.setCourseCode("CSE101");

        Course course = new Course();
        course.setCourseCode("CSE101");

        when(courseRepo.findByCourseCode("CSE101")).thenReturn(Optional.of(course));
        when(classSessionRepo.findByDayOfWeekAndStartTimeAndEndTimeAndLocation(
                DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0), null))
                .thenReturn(new ArrayList<>());
        when(classSessionRepo.findByDayOfWeekAndStartTimeAndEndTimeAndCourseCode(
                DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0), "CSE101"))
                .thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> classSessionService.createClassSession(classSession));
        verify(classSessionRepo, times(1)).save(classSession);
    }

    @Test
    void testGetAllClassSession_NotEmpty() {
        List<ClassSession> classSessions = new ArrayList<>();
        classSessions.add(new ClassSession());
        when(classSessionRepo.findAll()).thenReturn(classSessions);

        List<ClassSession> result = classSessionService.getAllClassSession();

        assertFalse(result.isEmpty());
        assertEquals(classSessions, result);
    }

    @Test
    void testGetClassSessionByDayOfWeek_NotEmpty() {
        List<ClassSession> classSessions = new ArrayList<>();
        classSessions.add(new ClassSession());
        when(classSessionRepo.findByDayOfWeek(DayOfWeek.MONDAY)).thenReturn(classSessions);

        List<ClassSession> result = classSessionService.getClassSessionByDayOfWeek(DayOfWeek.MONDAY);

        assertFalse(result.isEmpty());
        assertEquals(classSessions, result);
    }
}

