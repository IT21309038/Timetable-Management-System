package com.university.Timetable.Management.System.repo;

import com.university.Timetable.Management.System.model.ClassSession;
import com.university.Timetable.Management.System.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ClassSessionRepo extends MongoRepository<ClassSession, String> {

    Optional<ClassSession> findById(String id);
    void deleteById(String id);

    List<ClassSession> findByDayOfWeekAndStartTimeAndEndTimeAndCourseCode(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, String courseCode);

    List<ClassSession> findByDayOfWeekAndStartTimeAndEndTimeAndLocation(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, String location);

    List<ClassSession> findByCourseCodeAndDayOfWeek(String courseCode, DayOfWeek day);

    List<ClassSession> findByCourseCode(String courseCode);

    List<ClassSession> findByDayOfWeek(DayOfWeek day);
}
